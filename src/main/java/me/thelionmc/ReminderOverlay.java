package me.thelionmc;

public class ReminderOverlay {
    public static String currentMessage = null;
    public static long displayUntil = 0;

    public static void showReminder(String message, long durationMs) {
        currentMessage = message;
        displayUntil = System.currentTimeMillis() + durationMs;
    }

    public static boolean shouldRender() {
        return currentMessage != null && System.currentTimeMillis() < displayUntil;
    }

    public static String getMessage() {
        return currentMessage;
    }
}
