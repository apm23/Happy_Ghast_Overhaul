package com.apm23.happyghastoverhaul.mixin;

import com.apm23.happyghastoverhaul.gameplay.MilitaryHarnessEffects;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import net.minecraft.world.entity.projectile.throwableitemprojectile.Snowball;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Snowball.class)
public abstract class SnowballMixin {
    @Inject(method = "onHitEntity", at = @At("HEAD"), cancellable = true)
    private void happyGhastOverhaul$healHarnessedGhast(EntityHitResult hit, CallbackInfo ci) {
        Snowball self = (Snowball) (Object) this;
        if (hit.getEntity() instanceof HappyGhast ghast && MilitaryHarnessEffects.isMilitaryHarnessEquipped(ghast)) {
            MilitaryHarnessEffects.healFromSnowball(ghast);
            if (!self.level().isClientSide()) {
                self.discard();
            }
            ci.cancel();
        }
    }
}
