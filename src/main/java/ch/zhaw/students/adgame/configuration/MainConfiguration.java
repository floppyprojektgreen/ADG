package ch.zhaw.students.adgame.configuration;

/**
 * Interface for Configuration constants
 */
public interface MainConfiguration {
	/** main directory for configuration files */
	public static final String CONFIGURATION_DIR = "config/";
	
	/** resource configuration file path */
	public static final String RESOURCE_CONFIGURATION = CONFIGURATION_DIR + "resources.properties";

	/** audio configuration file path */
	public static final String AUDIO_CONFIGURATION = CONFIGURATION_DIR + "audio.properties";
	
	/** color configuration file path */
	public static final String COLOR_CONFIGURATION = CONFIGURATION_DIR + "color.properties";
	
	/** field configuration file path */
	public static final String FIELD_CONFIGURATION = CONFIGURATION_DIR + "field.properties";
	
	/** item configuration file path */
	public static final String ITEM_CONFIGURATION = CONFIGURATION_DIR + "item.properties";
	
	/** character configuration file path */
	public static final String CHARACTER_CONFIGURATION = CONFIGURATION_DIR + "character.properties";
	
	/** system configuration file path */
	public static final String SYSTEM_CONFIGURATION = CONFIGURATION_DIR + "system.properties";
}
