package com.apm23.happyghastoverhaul.client.mixin;

import com.apm23.happyghastoverhaul.client.render.MilitaryHarnessRenderData;
import com.apm23.happyghastoverhaul.gameplay.MilitaryHarnessEffects;
import net.fabricmc.fabric.api.client.rendering.v1.FabricRenderState;
import net.minecraft.client.renderer.entity.HappyGhastRenderer;
import net.minecraft.client.renderer.entity.state.HappyGhastRenderState;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HappyGhastRenderer.class)
public abstract class HappyGhastRendererMixin {
    @Inject(method = "extractRenderState", at = @At("TAIL"))
    private void happyGhastOverhaul$markMilitaryHarness(
            HappyGhast entity,
            HappyGhastRenderState state,
            float partialTicks,
            CallbackInfo ci
    ) {
        ((FabricRenderState) state).setData(
                MilitaryHarnessRenderData.EQUIPPED,
                MilitaryHarnessEffects.isMilitaryHarnessEquipped(entity)
        );
    }
}
