package com.apm23.happyghastoverhaul.mixin;

import com.apm23.happyghastoverhaul.gameplay.FlightSpeedState;
import com.apm23.happyghastoverhaul.gameplay.MilitaryHarnessEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(HappyGhast.class)
public abstract class HappyGhastSpeedMixin extends Animal {
    protected HappyGhastSpeedMixin(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    /**
     * Vanilla Happy Ghast travel derives ridden flight acceleration from a 5.0F constant.
     * Scaling only that value preserves the vanilla movement algorithm exactly at Lv1 and
     * proportionally raises its settled speed at Lv2-Lv4.
     */
    @ModifyConstant(method = "travel", constant = @Constant(floatValue = 5.0F))
    private float happyGhastOverhaul$scaleRiddenSpeed(float vanillaConstant) {
        if (!this.level().isClientSide()) {
            return vanillaConstant;
        }

        HappyGhast ghast = (HappyGhast) (Object) this;
        if (!(ghast.getControllingPassenger() instanceof Player)
                || !MilitaryHarnessEffects.isMilitaryHarnessEquipped(ghast)) {
            return vanillaConstant;
        }

        return vanillaConstant * (float) FlightSpeedState.multiplier();
    }
}
