package ch.zhaw.students.adgame.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.stream.Collectors;

import ch.zhaw.students.adgame.logging.LoggingHandler;

/**
 * Representation of the configuration saved in the field configuration file.
 */
public class FieldConfiguration {
	private static Properties fieldProp;
	
	static {
		fieldProp = new XProperties();
		
		try (InputStream in = new FileInputStream(MainConfiguration.FIELD_CONFIGURATION)) {
			fieldProp.load(in);
		} catch (IOException e) {
			LoggingHandler.log(e, Level.SEVERE);
		}
	}
	
	/**
	 * Gets the loaded definition for all fieldTypes.
	 */
	public static List<String> getFieldDefinitions() {
		return fieldProp.values().stream().map(String::valueOf).collect(Collectors.toList());
	}
}
