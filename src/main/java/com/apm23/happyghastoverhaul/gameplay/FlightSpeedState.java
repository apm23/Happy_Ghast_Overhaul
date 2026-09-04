package com.apm23.happyghastoverhaul.gameplay;

public final class FlightSpeedState {
    private static final double[] MULTIPLIERS = {4.0D, 8.0D, 12.0D, 20.0D};
    private static int level = 1;

    private FlightSpeedState() {
    }

    public static int level() {
        return level;
    }

    public static double multiplier() {
        return MULTIPLIERS[level - 1];
    }

    public static float scale(float vanillaTravelConstant) {
        return vanillaTravelConstant * (float) multiplier();
    }

    public static void cycle() {
        level = level == 4 ? 1 : level + 1;
    }

    public static void reset() {
        level = 1;
    }
}
