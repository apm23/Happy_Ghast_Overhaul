package com.apm23.happyghastoverhaul.gameplay;

public final class FlightSpeedState {
    private static final double[] MULTIPLIERS = {1.0D, 1.5D, 2.0D, 3.0D};
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
