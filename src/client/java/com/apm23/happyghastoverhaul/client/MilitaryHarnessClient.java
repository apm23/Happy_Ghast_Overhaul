package com.apm23.happyghastoverhaul.client;

import com.apm23.happyghastoverhaul.HappyGhastOverhaul;
import com.apm23.happyghastoverhaul.client.render.MilitaryHarnessClientVisuals;
import com.apm23.happyghastoverhaul.gameplay.FlightInputState;
import com.apm23.happyghastoverhaul.gameplay.FlightSpeedState;
import com.apm23.happyghastoverhaul.gameplay.MilitaryHarnessEffects;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.happyghast.HappyGhast;
import org.lwjgl.glfw.GLFW;

public final class MilitaryHarnessClient implements ClientModInitializer {
    private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(
            Identifier.fromNamespaceAndPath(HappyGhastOverhaul.MOD_ID, "controls")
    );
    private static final String VISUAL_SMOKE_SERVER = System.getProperty("happyGhastVisualSmokeServer", "").trim();

    private static KeyMapping ascend;
    private static KeyMapping descend;
    private static KeyMapping cycleSpeed;
    private static boolean visualSmokeConnectAttempted;

    @Override
    public void onInitializeClient() {
        MilitaryHarnessClientVisuals.register();

        ascend = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.happy_ghast_overhaul.ascend",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_SPACE,
                CATEGORY
        ));

        descend = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.happy_ghast_overhaul.descend",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_CAPS_LOCK,
                CATEGORY
        ));

        cycleSpeed = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.happy_ghast_overhaul.cycle_speed",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                CATEGORY
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!visualSmokeConnectAttempted
                    && !VISUAL_SMOKE_SERVER.isEmpty()
                    && client.level == null
                    && client.screen != null) {
                visualSmokeConnectAttempted = true;
                HappyGhastOverhaul.LOGGER.info("Visual smoke auto-connect: {}", VISUAL_SMOKE_SERVER);
                ServerAddress address = ServerAddress.parseString(VISUAL_SMOKE_SERVER);
                ServerData data = new ServerData("Happy Ghast Visual Smoke", VISUAL_SMOKE_SERVER, ServerData.Type.OTHER);
                ConnectScreen.startConnecting(client.screen, client, address, data, false, null);
                return;
            }

            boolean validPilot = client.player != null
                    && client.player.getVehicle() instanceof HappyGhast ghast
                    && ghast.getControllingPassenger() == client.player
                    && MilitaryHarnessEffects.isMilitaryHarnessEquipped(ghast);

            FlightInputState.setAscendPressed(validPilot && ascend.isDown());
            FlightInputState.setDescendPressed(validPilot && descend.isDown());

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
                FlightInputState.reset();
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
