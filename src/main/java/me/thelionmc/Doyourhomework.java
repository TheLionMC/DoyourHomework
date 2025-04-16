package me.thelionmc;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.gui.screen.TitleScreen;

import static me.thelionmc.SessionManager.*;

public class Doyourhomework implements ClientModInitializer {
	public static final String MOD_ID = "sessioncontrol";
	private static long lastremindermillis = 0;

	@Override
	public void onInitializeClient() {
		SessionManager.load();
		SessionManager.checkForNewDay();

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (sessionStartTime > 0 && getsessiondurationmillis() > 0) {
				long now = System.currentTimeMillis();
				long elapsed = now - sessionStartTime;
				long remaining = getsessiondurationmillis() - elapsed;

				long remainingMinutes = remaining / 60000;

				if (now - lastremindermillis >= 60_000) {
					if (remainingMinutes % 60 == 0 && remainingMinutes >= 60) {
						ReminderOverlay.showReminder("⏰ " + remainingMinutes / 60 + " hour(s) left in your session.", 5);
						lastremindermillis = now;
					} else if (remainingMinutes == 30 || remainingMinutes == 10 || remainingMinutes == 5) {
						ReminderOverlay.showReminder("⚠️ " + remainingMinutes + " minutes left in your session.", 5);
						lastremindermillis = now;
					}
				}
			}

			if (client.currentScreen instanceof TitleScreen &&
					!SessionManager.isInitialPromptShown() &&
					!SessionManager.isLockedForToday()) {
				System.out.println("Displaying StartUpPromptScreen");
				client.execute(() -> client.setScreen(new StartUpPromptScreen()));
				SessionManager.setInitialPromptShown(true);
			}

			if (SessionManager.isTimeUp()) {
				SessionManager.lockUntilTomorrow();
				client.execute(() -> {
					if (client.getNetworkHandler() != null) {
						client.disconnect();
					}
					client.setScreen(new LockoutScreen());
				});
			}
		});
	}

}
