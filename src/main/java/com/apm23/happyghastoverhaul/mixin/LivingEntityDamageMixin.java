package com.apm23.happyghastoverhaul.mixin;

import com.apm23.happyghastoverhaul.gameplay.MilitaryHarnessEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityDamageMixin {
    @Inject(method = "hurtServer", at = @At("HEAD"), cancellable = true)
    private void happyGhastOverhaul$cancelFireDamage(ServerLevel level, DamageSource source, float amount,
                                                     CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (self instanceof HappyGhast ghast && MilitaryHarnessEffects.shouldCancelDamage(ghast, source)) {
            cir.setReturnValue(false);
        }
    }

    @ModifyVariable(method = "hurtServer", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private float happyGhastOverhaul$reduceProtectedDamage(float amount, ServerLevel level, DamageSource source) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (self instanceof HappyGhast ghast) {
            return MilitaryHarnessEffects.modifyDamage(ghast, source, amount);
        }
        return amount;
    }
}
