package ch.zhaw.students.adgame.resource;

import java.util.Arrays;

import ch.zhaw.students.adgame.configuration.ColorConfiguration;

public class CacheLoader {
	public static void initializeAllCachesInLoaders() {
		Arrays.stream(ColorConfiguration.values()).forEach(ColorLoader::loadColor);
		//TODO: implement new way to cache texture
//		Arrays.stream(Texture.values()).forEach(TextureLoader::loadImage);
		
		FieldTypeLoader.getAvailableFieldTypes();
		ItemLoader.getAvailableItems();
	}
}
