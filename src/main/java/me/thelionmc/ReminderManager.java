package me.thelionmc;

import java.util.logging.Logger;

public class ReminderManager {
    private static long sessionStart = 0;
    private static long sessionDuration = 0;

    private static boolean quarterSent = false;
    private static boolean halfSent = false;
    private static boolean threeQuartersSent = false;
    private static boolean tenMinSent = false;
    private static boolean fiveMinSent = false;
    private static boolean threeMinSent = false;
    private static boolean twoMinSent = false;
    private static boolean oneMinSent = false;

    private static int lastCountdownSecond = -1;

    public static void startSession(long startTimeMillis, long durationMillis) {
        sessionStart = startTimeMillis;
        sessionDuration = durationMillis;

        quarterSent = false;
        halfSent = false;
        threeQuartersSent = false;
        tenMinSent = false;
        fiveMinSent = false;
        threeMinSent = false;
        twoMinSent = false;
        oneMinSent = false;
        lastCountdownSecond = -1;
    }

    public static void reset() {
        sessionStart = 0;
        sessionDuration = 0;
        quarterSent = halfSent = threeQuartersSent = false;
        tenMinSent = fiveMinSent = false;
        threeMinSent = twoMinSent = oneMinSent = false;
        lastCountdownSecond = -1;
    }

    public static void update() {
        if (sessionStart == 0 || sessionDuration == 0) return;

        long now = System.currentTimeMillis();
        long elapsed = now - sessionStart;
        long remaining = sessionDuration - elapsed;

        if (remaining <= 0) {
            reset();
            return;
        }

        long remainingSeconds = remaining / 1000;
        long remainingMinutes = remainingSeconds / 60;

        if (!quarterSent && elapsed >= sessionDuration / 4) {
            ReminderOverlay.showReminder("⏳ One quarter of your session has passed.", 5000);
            quarterSent = true;
        }
        if (!halfSent && elapsed >= sessionDuration / 2) {
            ReminderOverlay.showReminder("⏳ Half of your session is done.", 5000);
            halfSent = true;
        }
        if (!threeQuartersSent && elapsed >= sessionDuration * 3 / 4) {
            ReminderOverlay.showReminder("⏳ Three quarters of your session gone.", 5000);
            threeQuartersSent = true;
        }

        if (!tenMinSent && sessionDuration > 10 * 60_000 && remainingMinutes == 10) {
            ReminderOverlay.showReminder("⚠️ 10 minutes left in your session.", 5000);
            tenMinSent = true;
        }

        if (!fiveMinSent && sessionDuration > 5 * 60_000 && remainingMinutes == 5) {
            ReminderOverlay.showReminder("⚠️ 5 minutes left in your session.", 5000);
            fiveMinSent = true;
        }

        if (!threeMinSent && sessionDuration > 3 * 60_000 && remainingMinutes == 3) {
            ReminderOverlay.showReminder("⚠️ 3 minutes left.", 5000);
            threeMinSent = true;
        }
        if (!twoMinSent && sessionDuration > 2 * 60_000 && remainingMinutes == 2) {
            ReminderOverlay.showReminder("⚠️ 2 minutes left.", 5000);
            twoMinSent = true;
        }
        if (!oneMinSent && sessionDuration > 60_000 && remainingMinutes == 1) {
            ReminderOverlay.showReminder("⚠️ 1 minute left.", 5000);
            oneMinSent = true;
        }

        if (remainingSeconds <= 10 && remainingSeconds > 0 && lastCountdownSecond != remainingSeconds) {
            ReminderOverlay.showReminder("⏳ " + remainingSeconds + " second" + (remainingSeconds == 1 ? "" : "s") + " left!", 1000);
            lastCountdownSecond = (int) remainingSeconds;
        }
    }
}
