package com.apm23.happyghastoverhaul.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.FabricRenderState;
import net.minecraft.client.model.animal.ghast.HappyGhastModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HappyGhastRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

/** Shared FINAL v2 geometry with dedicated faction base + emissive textures. */
public final class MilitaryHarnessRenderLayer extends RenderLayer<HappyGhastRenderState, HappyGhastModel> {
    private static final int WHITE = 0xFFFFFFFF;
    private static final int FULL_BRIGHT = 0x00F000F0;

    private final MilitaryHarnessVisualModel fallbackModel;

    public MilitaryHarnessRenderLayer(RenderLayerParent<HappyGhastRenderState, HappyGhastModel> parent, MilitaryHarnessVisualModel model) {
        super(parent);
        this.fallbackModel = model;
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

        var finalMesh = FinalV2PackedMesh.get();
        if (finalMesh.isPresent()) {
            FinalV2PackedMesh mesh = finalMesh.get();
            collector.submitCustomGeometry(
                    poseStack,
                    RenderType.entityCutoutNoCull(baseTexture),
                    (pose, consumer) -> mesh.render(pose, consumer, packedLight)
            );
            collector.submitCustomGeometry(
                    poseStack,
                    RenderType.entityCutoutNoCull(emissiveTexture),
                    (pose, consumer) -> mesh.render(pose, consumer, FULL_BRIGHT)
            );
        } else {
            // Compile-safe/runtime-safe fallback until the packed FINAL v2 mesh asset is present.
            collector.submitModel(
                    this.fallbackModel,
                    state,
                    poseStack,
                    this.fallbackModel.renderType(baseTexture),
                    packedLight,
                    OverlayTexture.NO_OVERLAY,
                    WHITE,
                    null
            );

            collector.submitModel(
                    this.fallbackModel,
                    state,
                    poseStack,
                    this.fallbackModel.renderType(emissiveTexture),
                    FULL_BRIGHT,
                    OverlayTexture.NO_OVERLAY,
                    WHITE,
                    null
            );
        }

        poseStack.popPose();
    }
}
