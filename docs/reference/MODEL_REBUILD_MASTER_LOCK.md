# Happy Ghast Overhaul — Model Rebuild Master Lock

Authoritative visual source: uploaded 1536x1024 master sheet containing Sentinel (blue) and Reaper (red), with front/left/back/right reference views and banner references.

## Geometry lock
- Build #109 cuboid fortress geometry is visually rejected and must not be used as the shape source.
- Sentinel is the canonical geometry source for the Hunyuan3D-2mv rebuild.
- Reaper must reuse the exact same geometry/mesh and differ only by faction treatment: material/texture, emissive accents, banner treatment, and color language.
- Do not change gameplay mechanics, Happy Ghast body scale, harness/world scale, or rider alignment while replacing the visual mesh.

## Reference interpretation
- Use the four Sentinel strip views as the primary multi-view geometry inputs: FRONT, LEFT, BACK, RIGHT.
- Preserve the fortress-like armored shell, corner vertical braces, roof battlements/posts, hanging lower armor plates, lantern towers, frontal eye/visor area, and rear banner mount as seen in the master.
- The white Happy Ghast body/tentacles are context only and are not part of the harness mesh reconstruction.
- Banner cloth/emblem may remain a separately rendered attachment; its support mast/frame must align with the shared geometry.

## Pipeline target
1. Crop and normalize the four Sentinel views from the master reference.
2. Generate a single base mesh with Hunyuan3D-2mv.
3. Clean/retopologize only as needed for Minecraft rendering without changing silhouette.
4. Fit the mesh to the already-locked in-game scale/origin and rider alignment.
5. Bind both Sentinel and Reaper render paths to the same geometry.
6. Apply faction-specific textures/emissive/banner only after geometry validation.
7. Run build plus client visual smoke before accepting the replacement.

This file intentionally locks the visual source before any new generated geometry is integrated.
