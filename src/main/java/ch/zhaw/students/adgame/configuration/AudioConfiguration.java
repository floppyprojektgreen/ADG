package ch.zhaw.students.adgame.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Enumeration with keys for the audio configuration file.
 */
public enum AudioConfiguration {
	THEME_MAIN("theme_main"),
	THEME_FIGHT("theme_fight"),
	DICE_ROLL("dice_roll"),
	WILHELM_SCREAM("wilhelm_scream");
	
	private String key;
	
	private AudioConfiguration(String key) {
		this.key = key;
	}
	
	private static Properties audioProp;
	
	static {
		audioProp = new XProperties();
		
		try (InputStream in = new FileInputStream(MainConfiguration.AUDIO_CONFIGURATION)) {
			audioProp.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the loaded configuration for the given configuration key.
	 */
	public static String getConfiguration(AudioConfiguration configKey) {
		return audioProp.getProperty(configKey.key);
	}
}
