# FINAL v2 packed mesh payload

Authoritative source: local accepted `Sentinel_FINAL_v2.glb`; Reaper shares identical geometry and UV.

Deterministic HGV2 packer: `tools/pack_final_v2_mesh.py`.

Expected payload fingerprint from the accepted FINAL v2 source:

- body expanded vertices: `108894`
- banner expanded vertices: `48`
- raw HGV2 bytes: `3486160`
- gzip bytes: `775132`
- SHA-256 (raw HGV2): `7f3e248009a674597431ef37c83a8bc08403911b295b596e5ce8fd71796864f2`

The runtime loader must only replace the procedural fallback once the complete payload is packaged and loads successfully. Partial `mesh_sources` payloads must never be treated as valid geometry.
