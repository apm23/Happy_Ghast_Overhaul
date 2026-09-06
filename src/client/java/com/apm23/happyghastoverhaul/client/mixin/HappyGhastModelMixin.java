package com.apm23.happyghastoverhaul.client.mixin;

import com.apm23.happyghastoverhaul.client.render.MilitaryHarnessRenderData;
import net.fabricmc.fabric.api.client.rendering.v1.FabricRenderState;
import net.minecraft.client.model.animal.ghast.HappyGhastModel;
import net.minecraft.client.renderer.entity.state.HappyGhastRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * The military harness is a complete visual replacement, not a shell drawn over vanilla.
 * Entity dimensions, passenger placement and every server-side property remain untouched;
 * only the vanilla client model root is hidden while a military harness is equipped.
 */
@Mixin(HappyGhastModel.class)
public abstract class HappyGhastModelMixin {
    @Inject(method = "setupAnim", at = @At("TAIL"))
    private void happyGhastOverhaul$hideVanillaForMilitaryModel(HappyGhastRenderState state, CallbackInfo ci) {
        boolean military = ((FabricRenderState) state)
                .getDataOrDefault(MilitaryHarnessRenderData.EQUIPPED, false);
        ((HappyGhastModel) (Object) this).root().visible = !military;
    }
}
