#!/usr/bin/env python3
import argparse, base64, gzip, hashlib, math, struct
from pathlib import Path

import numpy as np
import trimesh

MAGIC = b'HGV2'
VERSION = 1
FLOATS_PER_VERTEX = 8


def transformed_mesh(scene, geom_name):
    geom = scene.geometry[geom_name]
    node = next(n for n in scene.graph.nodes_geometry if scene.graph[n][1] == geom_name)
    transform, _ = scene.graph[node]
    verts = (np.c_[geom.vertices, np.ones(len(geom.vertices))] @ transform.T)[:, :3]
    uvs = np.asarray(geom.visual.uv)
    return geom, verts, uvs


def expand(scene, geom_name):
    geom, verts, uvs = transformed_mesh(scene, geom_name)
    out = []
    for face in geom.faces:
        p = verts[face]
        normal = np.cross(p[1] - p[0], p[2] - p[0])
        length = np.linalg.norm(normal)
        normal = np.array([0.0, 1.0, 0.0]) if length < 1e-12 or not np.isfinite(length) else normal / length
        for idx in face:
            x, y, z = verts[idx]
            u, v = uvs[idx]
            nx, ny, nz = normal
            out.extend((x, y, z, u, v, nx, ny, nz))
    return out


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument('glb', type=Path)
    ap.add_argument('--body', default='sentinel_body_uv')
    ap.add_argument('--banner', default='banner_cloth')
    ap.add_argument('--out', type=Path, required=True)
    ap.add_argument('--part-size', type=int, default=100000)
    args = ap.parse_args()

    scene = trimesh.load(args.glb, force='scene')
    body = expand(scene, args.body)
    banner = expand(scene, args.banner)

    packed = bytearray(struct.pack('<4sIII', MAGIC, VERSION, len(body)//FLOATS_PER_VERTEX, len(banner)//FLOATS_PER_VERTEX))
    packed += struct.pack('<' + 'f' * (len(body) + len(banner)), *(body + banner))

    args.out.parent.mkdir(parents=True, exist_ok=True)
    args.out.write_bytes(packed)
    gz = gzip.compress(bytes(packed), compresslevel=9, mtime=0)
    encoded = base64.b64encode(gz).decode('ascii')

    for i in range(math.ceil(len(encoded) / args.part_size)):
        part = encoded[i * args.part_size:(i + 1) * args.part_size]
        args.out.with_name(args.out.name + f'.gz.b64.part{i:02d}').write_text(part, encoding='ascii')

    print(f'body_vertices={len(body)//FLOATS_PER_VERTEX}')
    print(f'banner_vertices={len(banner)//FLOATS_PER_VERTEX}')
    print(f'raw_bytes={len(packed)}')
    print(f'gzip_bytes={len(gz)}')
    print(f'sha256={hashlib.sha256(packed).hexdigest()}')


if __name__ == '__main__':
    main()
