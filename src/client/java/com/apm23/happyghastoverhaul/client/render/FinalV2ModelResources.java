package com.apm23.happyghastoverhaul.client.render;

import com.apm23.happyghastoverhaul.HappyGhastOverhaul;
import net.minecraft.resources.Identifier;

/**
 * Central resource contract for the approved FINAL v2 Sentinel/Reaper visual set.
 *
 * Geometry is intentionally shared by both factions. Only faction base/emissive
 * textures differ. Keeping these identifiers in one place prevents the renderer
 * from silently drifting back to separate geometry paths.
 */
public final class FinalV2ModelResources {
    public static final Identifier SHARED_GEOMETRY = Identifier.fromNamespaceAndPath(
            HappyGhastOverhaul.MOD_ID,
            "models/entity/military_harness/final_v2/military_harness_final_v2.glb"
    );

    public static final Identifier SENTINEL_BASE = Identifier.fromNamespaceAndPath(
            HappyGhastOverhaul.MOD_ID,
            "textures/entity/military_harness/military_harness_sentinel.png"
    );

    public static final Identifier REAPER_BASE = Identifier.fromNamespaceAndPath(
            HappyGhastOverhaul.MOD_ID,
            "textures/entity/military_harness/military_harness_reaper.png"
    );

    public static final Identifier SENTINEL_EMISSIVE = Identifier.fromNamespaceAndPath(
            HappyGhastOverhaul.MOD_ID,
            "textures/entity/military_harness/military_harness_sentinel_emissive.png"
    );

    public static final Identifier REAPER_EMISSIVE = Identifier.fromNamespaceAndPath(
            HappyGhastOverhaul.MOD_ID,
            "textures/entity/military_harness/military_harness_reaper_emissive.png"
    );

    private FinalV2ModelResources() {
    }

    public static Identifier baseTexture(boolean reaper) {
        return reaper ? REAPER_BASE : SENTINEL_BASE;
    }

    public static Identifier emissiveTexture(boolean reaper) {
        return reaper ? REAPER_EMISSIVE : SENTINEL_EMISSIVE;
    }
}
