package com.apm23.happyghastoverhaul.client.render;

import com.apm23.happyghastoverhaul.HappyGhastOverhaul;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityRenderLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.animal.ghast.HappyGhastModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.HappyGhastRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.HappyGhastRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityTypes;

public final class MilitaryHarnessClientVisuals {
    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(
            Identifier.fromNamespaceAndPath(HappyGhastOverhaul.MOD_ID, "military_harness_visual"),
            "main"
    );

    private MilitaryHarnessClientVisuals() {
    }

    public static void register() {
        ModelLayerRegistry.registerModelLayer(MODEL_LAYER, MilitaryHarnessVisualModel::createLayer);

        LivingEntityRenderLayerRegistrationCallback.EVENT.register((entityType, renderer, helper, context) -> {
            if (entityType != EntityTypes.HAPPY_GHAST || !(renderer instanceof HappyGhastRenderer happyRenderer)) {
                return;
            }

            MilitaryHarnessVisualModel model = new MilitaryHarnessVisualModel(context.bakeLayer(MODEL_LAYER));
            @SuppressWarnings("unchecked")
            RenderLayerParent<HappyGhastRenderState, HappyGhastModel> parent =
                    (RenderLayerParent<HappyGhastRenderState, HappyGhastModel>) happyRenderer;
            helper.register(new MilitaryHarnessRenderLayer(parent, model));
        });
    }
}
