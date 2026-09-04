package com.apm23.happyghastoverhaul.gameplay;

import com.apm23.happyghastoverhaul.HappyGhastOverhaul;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;

public final class MilitaryHarnessEffects {
    public static final double BONUS_MAX_HEALTH = 250.0D;
    public static final float PROTECTED_DAMAGE_MULTIPLIER = 0.10F;
    public static final float SNOWBALL_HEAL_FRACTION = 0.50F;

    private static final Identifier MAX_HEALTH_MODIFIER_ID = Identifier.fromNamespaceAndPath(
            HappyGhastOverhaul.MOD_ID,
            "military_harness_max_health"
    );

    private static final AttributeModifier MAX_HEALTH_MODIFIER = new AttributeModifier(
            MAX_HEALTH_MODIFIER_ID,
            BONUS_MAX_HEALTH,
            AttributeModifier.Operation.ADD_VALUE
    );

    private MilitaryHarnessEffects() {
    }

    public static boolean isMilitaryHarnessEquipped(HappyGhast ghast) {
        return ghast.getItemBySlot(EquipmentSlot.BODY).getItem() == HappyGhastOverhaul.MILITARY_HARNESS;
    }

    public static void syncMaxHealthModifier(HappyGhast ghast) {
        if (ghast.level().isClientSide()) {
            return;
        }

        AttributeInstance maxHealth = ghast.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealth == null) {
            return;
        }

        boolean equipped = isMilitaryHarnessEquipped(ghast);
        boolean modifierPresent = maxHealth.hasModifier(MAX_HEALTH_MODIFIER_ID);
        if (equipped && !modifierPresent) {
            float oldHealth = ghast.getHealth();
            maxHealth.addTransientModifier(MAX_HEALTH_MODIFIER);
            ghast.setHealth(Math.min(ghast.getMaxHealth(), oldHealth + (float) BONUS_MAX_HEALTH));
        } else if (!equipped && modifierPresent) {
            maxHealth.removeModifier(MAX_HEALTH_MODIFIER_ID);
            ghast.setHealth(Math.min(ghast.getHealth(), ghast.getMaxHealth()));
        }
    }

    public static boolean shouldCancelDamage(HappyGhast ghast, DamageSource source) {
        return isMilitaryHarnessEquipped(ghast)
                && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)
                && source.is(DamageTypeTags.IS_FIRE);
    }

    public static float modifyDamage(HappyGhast ghast, DamageSource source, float amount) {
        if (!isMilitaryHarnessEquipped(ghast) || source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return amount;
        }

        boolean projectile = source.is(DamageTypeTags.IS_PROJECTILE);
        boolean explosion = source.is(DamageTypeTags.IS_EXPLOSION);
        boolean melee = source.getEntity() instanceof LivingEntity && source.getDirectEntity() == source.getEntity();
        return projectile || explosion || melee ? amount * PROTECTED_DAMAGE_MULTIPLIER : amount;
    }

    public static void healFromSnowball(HappyGhast ghast) {
        if (isMilitaryHarnessEquipped(ghast) && !ghast.level().isClientSide()) {
            ghast.heal(ghast.getMaxHealth() * SNOWBALL_HEAL_FRACTION);
        }
    }
}
