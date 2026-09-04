package com.apm23.happyghastoverhaul.client;

import com.apm23.happyghastoverhaul.HappyGhastOverhaul;
import com.apm23.happyghastoverhaul.gameplay.FlightSpeedState;
import com.apm23.happyghastoverhaul.gameplay.MilitaryHarnessEffects;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import org.lwjgl.glfw.GLFW;

public final class MilitaryHarnessClient implements ClientModInitializer {
    private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(
            Identifier.fromNamespaceAndPath(HappyGhastOverhaul.MOD_ID, "controls")
    );

    private static KeyMapping cycleSpeed;

    @Override
    public void onInitializeClient() {
        cycleSpeed = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.happy_ghast_overhaul.cycle_speed",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_CONTROL,
                CATEGORY
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            boolean validPilot = client.player != null
                    && client.player.getVehicle() instanceof HappyGhast ghast
                    && ghast.getControllingPassenger() == client.player
                    && MilitaryHarnessEffects.isMilitaryHarnessEquipped(ghast);

            while (cycleSpeed.consumeClick()) {
                if (validPilot) {
                    FlightSpeedState.cycle();
                    client.gui.hud.setOverlayMessage(
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
        });
    }

    private static String formatMultiplier(double multiplier) {
        if (multiplier == Math.rint(multiplier)) {
            return Integer.toString((int) multiplier);
        }
        return Double.toString(multiplier);
    }
}
