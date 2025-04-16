package me.thelionmc;

import net.minecraft.client.MinecraftClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.Properties;

public class SessionManager {
	private static final File SESSION_FILE = new File(MinecraftClient.getInstance().runDirectory, "config/sessioncontrol.properties");

	private static boolean lockedForToday = false;
	private static boolean initialPromptShown = false;
	static long sessionStartTime = 0;
	private static long sessionDurationMillis = 0;
	private static String postSessionReason = "";
	private static int lastDayChecked = -1;

	public static boolean delayedLockout = false;
	public static int lockoutDelayTicks = 0; // number of ticks to wait

	private static Properties properties = new Properties();

	public static void checkForNewDay() {
		int currentDay = Instant.now().atZone(java.time.ZoneId.systemDefault()).getDayOfYear();
		if (lastDayChecked == -1) {
			lastDayChecked = currentDay;
		} else if (currentDay != lastDayChecked) {
			lastDayChecked = currentDay;
			resetSessionForNewDay();  // This will unlock and reset session for the new day
		}
	}

	private static void resetSessionForNewDay() {
		// Unlock session for the new day
		unlockForTomorrow(); // Unlocks the session when a new day is detected
		initialPromptShown = false;
		sessionStartTime = 0;
		sessionDurationMillis = 0;
		postSessionReason = "";
		save();
	}

	public static void load() {
		if (SESSION_FILE.exists()) {
			try (FileInputStream fis = new FileInputStream(SESSION_FILE)) {
				properties.load(fis);
				lockedForToday = Boolean.parseBoolean(properties.getProperty("lockedForToday", "false"));
				initialPromptShown = Boolean.parseBoolean(properties.getProperty("initialPromptShown", "false"));
				sessionStartTime = Long.parseLong(properties.getProperty("sessionStartTime", "0"));
				sessionDurationMillis = Long.parseLong(properties.getProperty("sessionDurationMillis", "0"));
				postSessionReason = properties.getProperty("postSessionReason", "");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void save() {
		try (FileOutputStream fos = new FileOutputStream(SESSION_FILE)) {
			properties.setProperty("lockedForToday", Boolean.toString(lockedForToday));
			properties.setProperty("initialPromptShown", Boolean.toString(initialPromptShown));
			properties.setProperty("sessionStartTime", Long.toString(sessionStartTime));
			properties.setProperty("sessionDurationMillis", Long.toString(sessionDurationMillis));
			properties.setProperty("postSessionReason", postSessionReason);

			properties.store(fos, "Session Control Settings");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean isLockedForToday() {
		return lockedForToday;
	}

	public static void lockUntilTomorrow() {
		lockedForToday = true;
		save();
	}

	public static void unlockForTomorrow() {
		lockedForToday = false;
		save();
	}

	public static void startSession(int minutes, String reason) {
		sessionStartTime = System.currentTimeMillis();
		sessionDurationMillis = minutes * 60_000L;
		postSessionReason = reason;
		initialPromptShown = true;
		save();
	}

	private static void sendChat(MinecraftClient client, String msg) {
		ReminderOverlay.showReminder(msg, 5);
	}

	public static boolean isTimeUp() {
		if (sessionStartTime == 0 || sessionDurationMillis == 0) {
			return false;
		}
		long currentTime = System.currentTimeMillis();
		return (currentTime - sessionStartTime) >= sessionDurationMillis;
	}

	public static boolean isInitialPromptShown() {
		return initialPromptShown;
	}

	public static void setPostSessionReason(String reason) {
		postSessionReason = reason;
		save();
	}

	public static void setInitialPromptShown(boolean a) {
		initialPromptShown = a;
	}

	public static long getsessiondurationmillis() {
		return sessionDurationMillis;
	}

	public static String getPostSessionReason() {
		return postSessionReason;
	}
}
