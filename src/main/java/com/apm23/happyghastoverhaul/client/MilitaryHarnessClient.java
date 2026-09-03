package com.apm23.happyghastoverhaul.client;

import com.apm23.happyghastoverhaul.HappyGhastOverhaul;
import com.apm23.happyghastoverhaul.gameplay.FlightSpeedState;
import com.apm23.happyghastoverhaul.gameplay.MilitaryHarnessEffects;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = HappyGhastOverhaul.MOD_ID, value = Dist.CLIENT)
public final class MilitaryHarnessClient {
    private static final KeyMapping.Category CATEGORY = new KeyMapping.Category(
            Identifier.fromNamespaceAndPath(HappyGhastOverhaul.MOD_ID, "controls")
    );

    private static final KeyMapping CYCLE_SPEED = new KeyMapping(
            "key.happy_ghast_overhaul.cycle_speed",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_LEFT_CONTROL,
            CATEGORY
    );

    private MilitaryHarnessClient() {
    }

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.registerCategory(CATEGORY);
        event.register(CYCLE_SPEED);
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        boolean validPilot = minecraft.player != null
                && minecraft.player.getVehicle() instanceof HappyGhast ghast
                && ghast.getControllingPassenger() == minecraft.player
                && MilitaryHarnessEffects.isMilitaryHarnessEquipped(ghast);

        while (CYCLE_SPEED.consumeClick()) {
            if (validPilot) {
                FlightSpeedState.cycle();
                minecraft.gui.hud.setOverlayMessage(
                        Component.translatable(
                                "message.happy_ghast_overhaul.speed_level",
                                FlightSpeedState.level(),
                                formatMultiplier(FlightSpeedState.multiplier())
                        ),
                        false
                );
            }
        }

        if (!validPilot) {
            FlightSpeedState.reset();
        }
    }

    private static String formatMultiplier(double multiplier) {
        if (multiplier == Math.rint(multiplier)) {
            return Integer.toString((int) multiplier);
        }
        return Double.toString(multiplier);
    }
}
