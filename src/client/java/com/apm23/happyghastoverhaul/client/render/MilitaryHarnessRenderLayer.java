package com.apm23.happyghastoverhaul.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.FabricRenderState;
import net.minecraft.client.model.animal.ghast.HappyGhastModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HappyGhastRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

/** Shared 3D geometry with dedicated faction base + emissive textures. */
public final class MilitaryHarnessRenderLayer extends RenderLayer<HappyGhastRenderState, HappyGhastModel> {
    private static final int WHITE = 0xFFFFFFFF;
    private static final int FULL_BRIGHT = 0x00F000F0;

    /*
     * FINAL v2 integration rule:
     * Sentinel and Reaper keep identical geometry/UV. Faction differences are texture-only.
     * The existing ModelPart renderer remains the compile-safe fallback until the UV-mesh
     * submitter is wired in; do not mutate geometry here to imitate the FINAL v2 GLB.
     */
    private final MilitaryHarnessVisualModel model;

    public MilitaryHarnessRenderLayer(RenderLayerParent<HappyGhastRenderState, HappyGhastModel> parent, MilitaryHarnessVisualModel model) {
        super(parent);
        this.model = model;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int packedLight, HappyGhastRenderState state, float yRot, float xRot) {
        FabricRenderState fabricState = (FabricRenderState) state;
        if (!fabricState.getDataOrDefault(MilitaryHarnessRenderData.EQUIPPED, false)) {
            return;
        }

        boolean reaper = fabricState.getDataOrDefault(MilitaryHarnessRenderData.REAPER, false);
        Identifier baseTexture = FinalV2ModelResources.baseTexture(reaper);
        Identifier emissiveTexture = FinalV2ModelResources.emissiveTexture(reaper);

        poseStack.pushPose();

        collector.submitModel(
                this.model,
                state,
                poseStack,
                this.model.renderType(baseTexture),
                packedLight,
                OverlayTexture.NO_OVERLAY,
                WHITE,
                null
        );

        collector.submitModel(
                this.model,
                state,
                poseStack,
                this.model.renderType(emissiveTexture),
                FULL_BRIGHT,
                OverlayTexture.NO_OVERLAY,
                WHITE,
                null
        );
        poseStack.popPose();
    }
}
