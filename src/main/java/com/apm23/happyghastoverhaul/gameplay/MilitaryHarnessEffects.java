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
import net.minecraft.world.entity.projectile.throwableitemprojectile.Snowball;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

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

    public static boolean isMilitaryHarnessEquipped(HappyGhast ghast) {
        return ghast.getItemBySlot(EquipmentSlot.BODY).getItem() == HappyGhastOverhaul.MILITARY_HARNESS.get();
    }

    @SubscribeEvent
    public void onEntityTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof HappyGhast ghast) || ghast.level().isClientSide()) {
            return;
        }

        AttributeInstance maxHealth = ghast.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealth == null) {
            return;
        }

        boolean equipped = isMilitaryHarnessEquipped(ghast);
        boolean modifierPresent = maxHealth.hasModifier(MAX_HEALTH_MODIFIER_ID);

        if (equipped && !modifierPresent) {
            maxHealth.addTransientModifier(MAX_HEALTH_MODIFIER);
            ghast.setHealth(Math.min(ghast.getMaxHealth(), ghast.getHealth() + (float) BONUS_MAX_HEALTH));
        } else if (!equipped && modifierPresent) {
            maxHealth.removeModifier(MAX_HEALTH_MODIFIER_ID);
            ghast.setHealth(Math.min(ghast.getHealth(), ghast.getMaxHealth()));
        }
    }

    @SubscribeEvent
    public void onIncomingDamage(LivingIncomingDamageEvent event) {
        if (!(event.getEntity() instanceof HappyGhast ghast) || !isMilitaryHarnessEquipped(ghast)) {
            return;
        }

        DamageSource source = event.getSource();

        // Preserve void, /kill and other administrative/absolute damage semantics.
        if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return;
        }

        if (source.is(DamageTypeTags.IS_FIRE)) {
            event.setCanceled(true);
            return;
        }

        boolean projectile = source.is(DamageTypeTags.IS_PROJECTILE);
        boolean explosion = source.is(DamageTypeTags.IS_EXPLOSION);
        boolean melee = source.getEntity() instanceof LivingEntity && source.getDirectEntity() == source.getEntity();

        if (projectile || explosion || melee) {
            event.setAmount(event.getAmount() * PROTECTED_DAMAGE_MULTIPLIER);
        }
    }

    @SubscribeEvent
    public void onProjectileImpact(ProjectileImpactEvent event) {
        if (!(event.getProjectile() instanceof Snowball snowball)
                || snowball.level().isClientSide()
                || !(event.getRayTraceResult() instanceof EntityHitResult hit)
                || !(hit.getEntity() instanceof HappyGhast ghast)
                || !isMilitaryHarnessEquipped(ghast)) {
            return;
        }

        ghast.heal(ghast.getMaxHealth() * SNOWBALL_HEAL_FRACTION);

        // Consume the snowball ourselves and skip vanilla impact handling so this
        // special heal cannot also damage or trigger a second Happy Ghast effect.
        snowball.discard();
        event.setCanceled(true);
    }
}
