package com.apm23.happyghastoverhaul.client;

import com.apm23.happyghastoverhaul.HappyGhastOverhaul;
import com.apm23.happyghastoverhaul.client.render.MilitaryHarnessClientVisuals;
import com.apm23.happyghastoverhaul.gameplay.FlightInputState;
import com.apm23.happyghastoverhaul.gameplay.FlightSpeedState;
import com.apm23.happyghastoverhaul.gameplay.MilitaryHarnessEffects;
import com.mojang.blaze3d.platform.InputConstants;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Screenshot;
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
    private static final Set<String> VISUAL_SMOKE_CAPTURED = new HashSet<>();
    private static String visualSmokeCandidate = "";
    private static int visualSmokeStableTicks;

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
                    && client.gui.screen() != null) {
                visualSmokeConnectAttempted = true;
                System.out.println("[HappyGhastOverhaul] Visual smoke auto-connect: " + VISUAL_SMOKE_SERVER);
                ServerAddress address = ServerAddress.parseString(VISUAL_SMOKE_SERVER);
                ServerData data = new ServerData("Happy Ghast Visual Smoke", VISUAL_SMOKE_SERVER, ServerData.Type.OTHER);
                ConnectScreen.startConnecting(client.gui.screen(), client, address, data, false, null);
                return;
            }

            if (!VISUAL_SMOKE_SERVER.isEmpty() && client.player != null && client.level != null) {
                captureVisualSmokeFrame(client);
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

    private static void captureVisualSmokeFrame(net.minecraft.client.Minecraft client) {
        String angle = visualSmokeAngle(client.player.getX(), client.player.getY(), client.player.getZ());
        if (angle == null || VISUAL_SMOKE_CAPTURED.contains(angle)) {
            visualSmokeCandidate = "";
            visualSmokeStableTicks = 0;
            return;
        }

        if (!angle.equals(visualSmokeCandidate)) {
            visualSmokeCandidate = angle;
            visualSmokeStableTicks = 1;
            return;
        }

        if (++visualSmokeStableTicks < 20) {
            return;
        }

        VISUAL_SMOKE_CAPTURED.add(angle);
        visualSmokeCandidate = "";
        visualSmokeStableTicks = 0;
        File dir = new File(client.gameDirectory, "screenshots/visual-smoke");
        dir.mkdirs();
        String fileName = "visual-smoke/" + angle + ".png";
        System.out.println("[HappyGhastOverhaul] Capturing visual smoke framebuffer: " + angle);
        Screenshot.grab(client.gameDirectory, fileName, client.gameRenderer.mainRenderTarget(), 1,
                message -> System.out.println("[HappyGhastOverhaul] " + message.getString()));
    }

    private static String visualSmokeAngle(double x, double y, double z) {
        if (near(x, 0) && near(y, 101) && near(z, 12)) return "front";
        if (near(x, 0) && near(y, 101) && near(z, -12)) return "back";
        if (near(x, -12) && near(y, 101) && near(z, 0)) return "left";
        if (near(x, 12) && near(y, 101) && near(z, 0)) return "right";
        if (near(x, 0) && near(y, 112) && near(z, 0)) return "top";
        if (near(x, 0) && near(y, 88) && near(z, 0)) return "bottom";
        return null;
    }

    private static boolean near(double value, double target) {
        return Math.abs(value - target) < 0.6;
    }

    private static String formatMultiplier(double multiplier) {
        if (multiplier == Math.rint(multiplier)) {
            return Integer.toString((int) multiplier);
        }
        return Double.toString(multiplier);
    }
}
