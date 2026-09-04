package com.apm23.happyghastoverhaul.client.render;

import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;

public final class MilitaryHarnessRenderData {
    public static final RenderStateDataKey<Boolean> EQUIPPED = RenderStateDataKey.create(
            () -> "happy_ghast_overhaul:military_harness_equipped"
    );
    public static final RenderStateDataKey<Boolean> REAPER = RenderStateDataKey.create(
            () -> "happy_ghast_overhaul:military_harness_reaper"
    );

    private MilitaryHarnessRenderData() {}
}
