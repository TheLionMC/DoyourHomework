package me.thelionmc;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.Text;

public class ReminderOverlay {

    public static void showReminder(String msg, int durationSeconds) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.getToastManager() == null) return;

        SystemToast toast = new SystemToast(
                SystemToast.Type.NARRATOR_TOGGLE,
                Text.literal("⏰ Reminder"),
                Text.literal(msg)
        );

        client.getToastManager().add(toast);
    }
}
