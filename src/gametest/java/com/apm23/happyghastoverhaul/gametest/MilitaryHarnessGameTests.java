package com.apm23.happyghastoverhaul.gametest;

import com.apm23.happyghastoverhaul.HappyGhastOverhaul;
import com.apm23.happyghastoverhaul.gameplay.FlightSpeedState;
import com.apm23.happyghastoverhaul.gameplay.MilitaryHarnessEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.FunctionGameTestInstance;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.throwableitemprojectile.Snowball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.List;
import java.util.function.Consumer;

@Mod(MilitaryHarnessGameTests.TEST_MOD_ID)
public final class MilitaryHarnessGameTests {
    public static final String TEST_MOD_ID = "happy_ghast_overhaul_gametest";
    private static final String TEST_NAMESPACE = HappyGhastOverhaul.MOD_ID;
    private static final double EPSILON = 0.01D;

    private record Smoke(String name, int maxTicks, Consumer<GameTestHelper> body) {
    }

    private static final List<Smoke> TESTS = List.of(
            new Smoke("harness_health_lifecycle", 100, MilitaryHarnessGameTests::harnessHealthLifecycle),
            new Smoke("damage_protection", 100, MilitaryHarnessGameTests::damageProtection),
            new Smoke("snowball_heals_half_max_health", 100, MilitaryHarnessGameTests::snowballHealsHalfMaxHealth),
            new Smoke("speed_level_cycle", 40, MilitaryHarnessGameTests::speedLevelCycle)
    );

    public MilitaryHarnessGameTests(IEventBus modBus) {
        modBus.addListener(RegisterEvent.class, MilitaryHarnessGameTests::registerFunctions);
        modBus.addListener(RegisterGameTestsEvent.class, MilitaryHarnessGameTests::registerTests);
    }

    private static void registerFunctions(RegisterEvent event) {
        for (Smoke test : TESTS) {
            event.register(
                    Registries.TEST_FUNCTION,
                    Identifier.fromNamespaceAndPath(TEST_NAMESPACE, test.name()),
                    test::body
            );
        }
    }

    private static void registerTests(RegisterGameTestsEvent event) {
        var environment = event.registerEnvironment(
                Identifier.fromNamespaceAndPath(TEST_NAMESPACE, "military_harness_functional")
        );

        for (Smoke test : TESTS) {
            ResourceKey<Consumer<GameTestHelper>> function = ResourceKey.create(
                    Registries.TEST_FUNCTION,
                    Identifier.fromNamespaceAndPath(TEST_NAMESPACE, test.name())
            );
            event.registerTest(
                    Identifier.fromNamespaceAndPath(TEST_NAMESPACE, test.name()),
                    data -> new FunctionGameTestInstance(function, data),
                    new TestData<>(environment, Identifier.withDefaultNamespace("empty"), test.maxTicks(), 1, true)
            );
        }
    }

    private static void harnessHealthLifecycle(GameTestHelper helper) {
        HappyGhast ghast = spawnGhast(helper, new BlockPos(2, 3, 2));
        float vanillaMax = ghast.getMaxHealth();
        float vanillaHealth = ghast.getHealth();

        helper.assertTrue(!MilitaryHarnessEffects.isMilitaryHarnessEquipped(ghast),
                "Fresh Happy Ghast must not be treated as Military Harness equipped");

        equipMilitaryHarness(ghast);
        helper.runAfterDelay(3, () -> {
            assertNear(helper, ghast.getMaxHealth(), vanillaMax + MilitaryHarnessEffects.BONUS_MAX_HEALTH,
                    "+250 max-health modifier was not applied");
            assertNear(helper, ghast.getHealth(), vanillaHealth + MilitaryHarnessEffects.BONUS_MAX_HEALTH,
                    "Equipping should add the same 250 HP to current health");

            ghast.setItemSlot(EquipmentSlot.BODY, ItemStack.EMPTY);
            helper.runAfterDelay(3, () -> {
                assertNear(helper, ghast.getMaxHealth(), vanillaMax,
                        "Removing Military Harness must restore vanilla max health");
                helper.assertTrue(ghast.getHealth() <= vanillaMax + EPSILON,
                        "Removing Military Harness must clamp current health to restored max health");
                helper.succeed();
            });
        });
    }

    private static void damageProtection(GameTestHelper helper) {
        HappyGhast melee = spawnHarnessedGhast(helper, new BlockPos(2, 3, 2));
        HappyGhast projectile = spawnHarnessedGhast(helper, new BlockPos(8, 3, 2));
        HappyGhast explosion = spawnHarnessedGhast(helper, new BlockPos(14, 3, 2));
        HappyGhast fire = spawnHarnessedGhast(helper, new BlockPos(20, 3, 2));
        ServerLevel level = helper.getLevel();

        helper.runAfterDelay(3, () -> {
            var player = helper.makeMockServerPlayerInLevel();
            float incoming = 20.0F;
            float expectedLoss = incoming * MilitaryHarnessEffects.PROTECTED_DAMAGE_MULTIPLIER;

            float beforeMelee = melee.getHealth();
            melee.hurtServer(level, level.damageSources().playerAttack(player), incoming);
            assertNear(helper, beforeMelee - melee.getHealth(), expectedLoss,
                    "Melee damage was not reduced by 90%");

            AbstractArrow arrow = EntityTypes.ARROW.create(level, EntitySpawnReason.COMMAND);
            helper.assertTrue(arrow != null, "Could not construct an arrow damage source");
            float beforeProjectile = projectile.getHealth();
            projectile.hurtServer(level, level.damageSources().arrow(arrow, player), incoming);
            assertNear(helper, beforeProjectile - projectile.getHealth(), expectedLoss,
                    "Projectile damage was not reduced by 90%");

            float beforeExplosion = explosion.getHealth();
            explosion.hurtServer(level, level.damageSources().explosion(null, null), incoming);
            assertNear(helper, beforeExplosion - explosion.getHealth(), expectedLoss,
                    "Explosion damage was not reduced by 90%");

            float beforeFire = fire.getHealth();
            boolean fireAccepted = fire.hurtServer(level, level.damageSources().lava(), incoming);
            assertNear(helper, fire.getHealth(), beforeFire,
                    "Lava/fire damage must be completely canceled");
            helper.assertTrue(!fireAccepted,
                    "Canceled lava damage should report that no damage was accepted");

            helper.succeed();
        });
    }

    private static void snowballHealsHalfMaxHealth(GameTestHelper helper) {
        HappyGhast ghast = spawnHarnessedGhast(helper, new BlockPos(6, 4, 6));
        ServerLevel level = helper.getLevel();

        helper.runAfterDelay(3, () -> {
            ghast.setHealth(1.0F);
            float before = ghast.getHealth();
            float expected = Math.min(
                    ghast.getMaxHealth(),
                    before + ghast.getMaxHealth() * MilitaryHarnessEffects.SNOWBALL_HEAL_FRACTION
            );

            Snowball snowball = new Snowball(
                    level,
                    ghast.getX(),
                    ghast.getY(),
                    ghast.getZ(),
                    Items.SNOWBALL.getDefaultInstance()
            );
            level.addFreshEntity(snowball);

            ProjectileImpactEvent impact = new ProjectileImpactEvent(snowball, new EntityHitResult(ghast));
            NeoForge.EVENT_BUS.post(impact);

            helper.assertTrue(impact.isCanceled(),
                    "Military Harness snowball impact must cancel vanilla impact handling");
            helper.assertTrue(!snowball.isAlive(),
                    "Military Harness snowball impact must consume the snowball");
            assertNear(helper, ghast.getHealth(), expected,
                    "One snowball must heal exactly 50% of actual max health");
            helper.succeed();
        });
    }

    private static void speedLevelCycle(GameTestHelper helper) {
        FlightSpeedState.reset();
        assertSpeed(helper, 1, 1.0D);
        FlightSpeedState.cycle();
        assertSpeed(helper, 2, 1.5D);
        FlightSpeedState.cycle();
        assertSpeed(helper, 3, 2.0D);
        FlightSpeedState.cycle();
        assertSpeed(helper, 4, 3.0D);
        FlightSpeedState.cycle();
        assertSpeed(helper, 1, 1.0D);
        helper.succeed();
    }

    private static HappyGhast spawnHarnessedGhast(GameTestHelper helper, BlockPos relativePos) {
        HappyGhast ghast = spawnGhast(helper, relativePos);
        equipMilitaryHarness(ghast);
        return ghast;
    }

    private static HappyGhast spawnGhast(GameTestHelper helper, BlockPos relativePos) {
        ServerLevel level = helper.getLevel();
        HappyGhast ghast = EntityTypes.HAPPY_GHAST.create(level, EntitySpawnReason.COMMAND);
        helper.assertTrue(ghast != null, "Could not create Happy Ghast");
        ghast.setBaby(false);
        ghast.setNoAi(true);
        BlockPos absolute = helper.absolutePos(relativePos);
        ghast.setPos(absolute.getX() + 0.5D, absolute.getY(), absolute.getZ() + 0.5D);
        helper.assertTrue(level.addFreshEntity(ghast), "Could not add Happy Ghast to GameTest level");
        return ghast;
    }

    private static void equipMilitaryHarness(HappyGhast ghast) {
        ghast.setItemSlot(
                EquipmentSlot.BODY,
                new ItemStack(HappyGhastOverhaul.MILITARY_HARNESS.get())
        );
    }

    private static void assertSpeed(GameTestHelper helper, int expectedLevel, double expectedMultiplier) {
        helper.assertTrue(FlightSpeedState.level() == expectedLevel,
                "Expected speed Lv" + expectedLevel + " but got Lv" + FlightSpeedState.level());
        assertNear(helper, FlightSpeedState.multiplier(), expectedMultiplier,
                "Wrong multiplier for speed Lv" + expectedLevel);
    }

    private static void assertNear(GameTestHelper helper, double actual, double expected, String message) {
        helper.assertTrue(Math.abs(actual - expected) <= EPSILON,
                message + ": expected=" + expected + ", actual=" + actual);
    }
}
