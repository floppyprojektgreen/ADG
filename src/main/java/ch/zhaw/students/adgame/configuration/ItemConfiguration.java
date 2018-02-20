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
 * Representation of the configuration saved in the item configuration file
 */
public class ItemConfiguration {
	private static Properties itemProp;
	
	static {
		itemProp = new XProperties();
		
		try (InputStream in = new FileInputStream(MainConfiguration.ITEM_CONFIGURATION)) {
			itemProp.load(in);
		} catch (IOException e) {
			LoggingHandler.log(e, Level.SEVERE);
		}
	}
	
	/***
	 * Gets the loader definition for all items.
	 */
	public static List<String> getItemDefinitions() {
		return itemProp.values().stream().map(String::valueOf).collect(Collectors.toList());
	}
	

}
