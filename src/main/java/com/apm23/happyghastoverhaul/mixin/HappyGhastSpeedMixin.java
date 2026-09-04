package com.apm23.happyghastoverhaul.mixin;

import com.apm23.happyghastoverhaul.gameplay.FlightInputState;
import com.apm23.happyghastoverhaul.gameplay.FlightSpeedState;
import com.apm23.happyghastoverhaul.gameplay.MilitaryHarnessEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HappyGhast.class)
public abstract class HappyGhastSpeedMixin extends Animal {
    private static final double RESPONSIVE_AIR_DRAG = 0.70D;
    private static final float RESPONSIVE_TURN_RATE = 0.35F;

    protected HappyGhastSpeedMixin(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    /**
     * Military Harness control model:
     * - Vanilla WASD is untouched for horizontal steering.
     * - Dedicated rebindable Ascend/Descend keys control altitude.
     * - Vanilla dismount binding is untouched.
     * - Looking up/down no longer changes altitude.
     * - With no vertical input, altitude is held instead of slowly sinking.
     */
    @Inject(method = "getRiddenInput", at = @At("RETURN"), cancellable = true)
    private void happyGhastOverhaul$playerLikeInput(
            Player controller,
            Vec3 selfInput,
            CallbackInfoReturnable<Vec3> cir
    ) {
        HappyGhast ghast = (HappyGhast) (Object) this;
        if (!this.level().isClientSide() || !MilitaryHarnessEffects.isMilitaryHarnessEquipped(ghast)) {
            return;
        }

        Vec3 vanilla = cir.getReturnValue();
        double vertical = FlightInputState.ascendPressed()
                ? 1.0D
                : FlightInputState.descendPressed() ? -1.0D : 0.0D;
        cir.setReturnValue(new Vec3(vanilla.x, vertical, vanilla.z));
    }

    @Inject(method = "travel", at = @At("HEAD"), cancellable = true)
    private void happyGhastOverhaul$responsiveTravel(Vec3 input, CallbackInfo ci) {
        if (!this.level().isClientSide()) {
            return;
        }

        HappyGhast ghast = (HappyGhast) (Object) this;
        if (!(ghast.getControllingPassenger() instanceof Player)
                || !MilitaryHarnessEffects.isMilitaryHarnessEquipped(ghast)) {
            return;
        }

        double multiplier = FlightSpeedState.multiplier();
        double vanillaDrag = 0.91D;
        double dragCompensation = ((1.0D - RESPONSIVE_AIR_DRAG) / RESPONSIVE_AIR_DRAG)
                / ((1.0D - vanillaDrag) / vanillaDrag);

        Vec3 direction = input;
        double lengthCompensation = 1.0D;
        double length = input.length();
        if (length > 1.0D) {
            direction = input.scale(1.0D / length);
            lengthCompensation = length;
        }

        float vanillaSpeed = (float) this.getAttributeValue(Attributes.FLYING_SPEED) * 5.0F / 3.0F;
        float speed = (float) (vanillaSpeed * multiplier * dragCompensation * lengthCompensation);
        double drag = this.isInWater() ? 0.8D : this.isInLava() ? 0.5D : RESPONSIVE_AIR_DRAG;

        this.moveRelative(speed, direction);
        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().scale(drag));
        ci.cancel();
    }

    @ModifyConstant(method = "tickRidden", constant = @Constant(floatValue = 0.08F))
    private float happyGhastOverhaul$snappierTurning(float vanilla) {
        HappyGhast ghast = (HappyGhast) (Object) this;
        if (!this.level().isClientSide() || !MilitaryHarnessEffects.isMilitaryHarnessEquipped(ghast)) {
            return vanilla;
        }
        return RESPONSIVE_TURN_RATE;
    }
}
