package com.apm23.happyghastoverhaul.gameplay;

public final class FlightInputState {
    private static boolean ascendPressed;
    private static boolean descendPressed;

    private FlightInputState() {
    }

    public static boolean ascendPressed() {
        return ascendPressed;
    }

    public static void setAscendPressed(boolean pressed) {
        ascendPressed = pressed;
    }

    public static boolean descendPressed() {
        return descendPressed;
    }

    public static void setDescendPressed(boolean pressed) {
        descendPressed = pressed;
    }

    public static void reset() {
        ascendPressed = false;
        descendPressed = false;
    }
}
