package me.thelionmc;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.gui.screen.TitleScreen;

public class Doyourhomework implements ClientModInitializer {
	public static final String MOD_ID = "sessioncontrol";

	@Override
	public void onInitializeClient() {
		SessionManager.load();

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.currentScreen instanceof TitleScreen && !SessionManager.isInitialPromptShown() && !SessionManager.isLockedForToday()) {
				System.out.println("Displaying StartUpPromptScreen");
				client.execute(() -> client.setScreen(new StartUpPromptScreen()));
				boolean initialPromptShown = SessionManager.isInitialPromptShown();
				initialPromptShown = true;
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
