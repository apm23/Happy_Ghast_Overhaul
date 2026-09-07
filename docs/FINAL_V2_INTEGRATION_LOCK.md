# FINAL V2 integration lock

Source-of-truth visual assets were finalized in `apm23/Model_Ghast` as FINAL v2.

## Locked decisions

- Sentinel and Reaper share exactly the same geometry and UV layout.
- Sentinel uses the longer horizontal eye-slot proportion from texture Pass 30.
- Reaper is faction treatment only: red base accents/emissive instead of Sentinel cyan/purple treatment.
- Do not restart the old cuboid/banner visual redesign loop.
- Preserve existing Happy Ghast gameplay dimensions, mechanics and rider alignment behavior while integrating the new visual assets.
- The upper central pillar remains part of the approved geometry. If first-person/rider visibility needs adjustment, solve it through rider/camera alignment rather than remodeling the approved geometry.

## Current renderer constraint

The Fabric 26.2 branch currently renders `MilitaryHarnessVisualModel`, a vanilla `ModelPart`/cuboid model authored against a 512x256 texture sheet. `MilitaryHarnessRenderLayer` selects Sentinel/Reaper PNG textures and then submits a shared emissive texture. The approved FINAL v2 source, however, is a UV-mapped GLB using 2048x2048 faction-specific base and emissive textures.

Therefore the next integration step must replace/adapt the client visual pipeline for the FINAL v2 mesh instead of pretending that copying the new 2048 atlas over the old cuboid texture paths would reproduce the approved model.

## Asset names

- `Sentinel_FINAL_v2.glb`
- `Sentinel_FINAL_v2.png`
- `Sentinel_FINAL_v2_emissive.png`
- `Reaper_FINAL_v2.glb`
- `Reaper_FINAL_v2.png`
- `Reaper_FINAL_v2_emissive.png`

Binary source assets remain in the model handoff; this commit intentionally records the integration contract before renderer changes.
