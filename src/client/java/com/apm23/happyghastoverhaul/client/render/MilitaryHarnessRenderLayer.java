package com.apm23.happyghastoverhaul.client.render;

import com.apm23.happyghastoverhaul.HappyGhastOverhaul;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.FabricRenderState;
import net.minecraft.client.model.animal.ghast.HappyGhastModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HappyGhastRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

/** Shared 3D geometry with dedicated faction textures and amethyst emissive pass. */
public final class MilitaryHarnessRenderLayer extends RenderLayer<HappyGhastRenderState, HappyGhastModel> {
    private static final Identifier SENTINEL_TEXTURE = Identifier.fromNamespaceAndPath(
            HappyGhastOverhaul.MOD_ID,
            "textures/entity/military_harness/military_harness_sentinel.png"
    );
    private static final Identifier REAPER_TEXTURE = Identifier.fromNamespaceAndPath(
            HappyGhastOverhaul.MOD_ID,
            "textures/entity/military_harness/military_harness_reaper.png"
    );
    private static final Identifier EMISSIVE_TEXTURE = Identifier.fromNamespaceAndPath(
            HappyGhastOverhaul.MOD_ID,
            "textures/entity/military_harness/military_harness_emissive.png"
    );
    private static final int WHITE = 0xFFFFFFFF;
    private static final int FULL_BRIGHT = 0x00F000F0;

    /*
     * The custom mesh was authored against a compact ~20px ghast shell while the adult Happy
     * Ghast body occupies a much larger cube.  Rendering it 1:1 therefore places most armour
     * inside the body; only the highest/front-most pieces poke through.  Fit the authored shell
     * around the adult body before submitting it.  The scale expands X/Z onto the outside faces
     * and also preserves the intended proportions of the command deck / spear.
     */
    private static final float HARNESS_SCALE = 1.65F;
    private static final float BODY_Y_OFFSET = -0.55F;

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
        Identifier baseTexture = reaper ? REAPER_TEXTURE : SENTINEL_TEXTURE;

        poseStack.pushPose();
        poseStack.translate(0.0F, BODY_Y_OFFSET, 0.0F);
        poseStack.scale(HARNESS_SCALE, HARNESS_SCALE, HARNESS_SCALE);

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
                this.model.renderType(EMISSIVE_TEXTURE),
                FULL_BRIGHT,
                OverlayTexture.NO_OVERLAY,
                WHITE,
                null
        );
        poseStack.popPose();
    }
}
