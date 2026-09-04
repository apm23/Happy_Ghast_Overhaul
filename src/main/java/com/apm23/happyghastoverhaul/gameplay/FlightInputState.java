package com.apm23.happyghastoverhaul.gameplay;

public final class FlightInputState {
    private static boolean descendPressed;

    private FlightInputState() {
    }

    public static boolean descendPressed() {
        return descendPressed;
    }

    public static void setDescendPressed(boolean pressed) {
        descendPressed = pressed;
    }

    public static void reset() {
        descendPressed = false;
    }
}
