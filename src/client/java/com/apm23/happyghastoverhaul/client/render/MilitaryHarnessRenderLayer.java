package com.apm23.happyghastoverhaul.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.FabricRenderState;
import net.minecraft.client.model.animal.ghast.HappyGhastModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HappyGhastRenderState;
import net.minecraft.resources.Identifier;

/** Shared 3D geometry, faction-selected palette. */
public final class MilitaryHarnessRenderLayer extends RenderLayer<HappyGhastRenderState, HappyGhastModel> {
    private static final Identifier VALIDATION_TEXTURE = Identifier.withDefaultNamespace("textures/entity/happy_ghast/happy_ghast.png");
    private static final int SENTINEL_BLUE = 0xFF182B43;
    private static final int REAPER_RED = 0xFF42191D;
    private final MilitaryHarnessVisualModel model;

    public MilitaryHarnessRenderLayer(RenderLayerParent<HappyGhastRenderState, HappyGhastModel> parent, MilitaryHarnessVisualModel model) {
        super(parent);
        this.model = model;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int packedLight, HappyGhastRenderState state, float yRot, float xRot) {
        FabricRenderState fabricState = (FabricRenderState) state;
        if (!fabricState.getDataOrDefault(MilitaryHarnessRenderData.EQUIPPED, false)) return;
        boolean reaper = fabricState.getDataOrDefault(MilitaryHarnessRenderData.REAPER, false);
        coloredCutoutModelCopyLayerRender(this.model, VALIDATION_TEXTURE, poseStack, collector, packedLight, state,
                reaper ? REAPER_RED : SENTINEL_BLUE, 0);
    }
}
