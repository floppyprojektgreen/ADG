package ch.zhaw.students.adgame.resource;

import java.util.Arrays;

import ch.zhaw.students.adgame.configuration.ColorConfiguration;
import ch.zhaw.students.adgame.configuration.ResourcesConfiguration;

public class CacheLoader {
	public static void initializeAllCachesInLoaders() {
		Arrays.stream(ColorConfiguration.values()).forEach(ColorLoader::loadColor);
		Arrays.stream(ResourcesConfiguration.values()).forEach(TextureLoader::loadImage);
		
		FieldTypeLoader.getAvailableFieldTypes();
		ItemLoader.getAvailableItems();
	}
}
