package com.apm23.happyghastoverhaul.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.FabricRenderState;
import net.minecraft.client.model.animal.ghast.HappyGhastModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HappyGhastRenderState;
import net.minecraft.resources.Identifier;

/** Client render layer for the protruding Military Harness geometry. */
public final class MilitaryHarnessRenderLayer
        extends RenderLayer<HappyGhastRenderState, HappyGhastModel> {

    // Geometry is now validated on 26.2. Keep the vanilla-backed atlas while the dedicated
    // 128x128 Military Harness texture is authored; tint establishes the locked black-metal base.
    private static final Identifier VALIDATION_TEXTURE = Identifier.withDefaultNamespace(
            "textures/entity/happy_ghast/happy_ghast.png"
    );

    private static final int BLACK_METAL = 0xFF17191F;

    private final MilitaryHarnessVisualModel model;

    public MilitaryHarnessRenderLayer(
            RenderLayerParent<HappyGhastRenderState, HappyGhastModel> parent,
            MilitaryHarnessVisualModel model
    ) {
        super(parent);
        this.model = model;
    }

    @Override
    public void submit(
            PoseStack poseStack,
            SubmitNodeCollector collector,
            int packedLight,
            HappyGhastRenderState state,
            float yRot,
            float xRot
    ) {
        if (!((FabricRenderState) state).getDataOrDefault(MilitaryHarnessRenderData.EQUIPPED, false)) {
            return;
        }

        coloredCutoutModelCopyLayerRender(
                this.model,
                VALIDATION_TEXTURE,
                poseStack,
                collector,
                packedLight,
                state,
                BLACK_METAL,
                0
        );
    }
}
