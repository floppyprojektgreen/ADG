package ch.zhaw.students.adgame.resource;

import ch.zhaw.students.adgame.configuration.CharacterConfiguration;

/**
 * Loader for character configuration data used by the application.
 */
public class CharacterLoader {
	/**
	 * Loads the requested property.
	 */
	public static int loadProperty(CharacterConfiguration charConfig) {
		return loadProperty(charConfig, 0);
	}
	
	/**
	 * Loads the requested element of a property list.
	 * @throws ArrayIndexOutOfBoundsException if the given offset is greater than the list in the configuration
	 */
	public static int loadProperty(CharacterConfiguration charConfig, int index) {
		return Integer.parseInt(
				CharacterConfiguration.getConfiguration(charConfig)
				.split(",")[index]);
	}
}
