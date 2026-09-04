package com.apm23.happyghastoverhaul.mixin;

import com.apm23.happyghastoverhaul.gameplay.MilitaryHarnessEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HappyGhast.class)
public abstract class HappyGhastHealthMixin extends Animal {
    protected HappyGhastHealthMixin(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void happyGhastOverhaul$syncHarnessHealth(CallbackInfo ci) {
        MilitaryHarnessEffects.syncMaxHealthModifier((HappyGhast) (Object) this);
    }
}
