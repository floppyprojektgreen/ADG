package ch.zhaw.students.adgame.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Enumeration with keys for the character configuration file.
 */
public enum CharacterConfiguration {
	CHARACTER_MIN("character_min"),
	HEALTH("health"),
	RESOURCE("resource"),
	BASE_STRENGTH("base_strength"),
	BASE_DEFENSE("base_defense"),
	BASE_ACCURACY("base_accuracy"),
	X_POSITIONS("start_x"),
	Y_POSITIONS("start_y");
	
	private String key;
	
	private CharacterConfiguration(String key) {
		this.key = key;
	}
	
	private static Properties characterProp;
	
	static {
		characterProp = new XProperties();
		
		try (InputStream in = new FileInputStream(MainConfiguration.CHARACTER_CONFIGURATION)) {
			characterProp.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the loaded configuration for the given configuration key.
	 */
	public static String getConfiguration(CharacterConfiguration configKey) {
		return characterProp.getProperty(configKey.key);
	}
}
