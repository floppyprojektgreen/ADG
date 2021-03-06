package ch.zhaw.students.adgame.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;

import ch.zhaw.students.adgame.logging.LoggingHandler;

/**
 * Enumeration with keys for the system configuration file.
 */
public enum SystemConfiguration {
	SINGLE_SAVE("single_save"),
	PRE_CACHING("pre_caching"),
	DEV_ENV("dev_env");
	
	public static final String SAVE_FILE = "save.adg";
	
	private String key;
	
	private SystemConfiguration(String key) {
		this.key = key;
	}
	
	private static Properties resourceProp;
	
	static {
		resourceProp = new XProperties();
		
		try (InputStream in = new FileInputStream(MainConfiguration.SYSTEM_CONFIGURATION)) {
			resourceProp.load(in);
		} catch (IOException e) {
			LoggingHandler.log(e, Level.SEVERE);
		}
	}
	
	/**
	 * Checks if a configuration for the given configuration key is set to true.
	 */
	public static boolean isSet(SystemConfiguration configKey) {
		return Boolean.parseBoolean(resourceProp.getProperty(configKey.key));
	}
}
