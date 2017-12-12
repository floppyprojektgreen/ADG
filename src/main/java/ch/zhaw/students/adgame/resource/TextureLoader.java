package ch.zhaw.students.adgame.resource;

import java.util.HashMap;
import java.util.Map;

import ch.zhaw.students.adgame.configuration.ResourcesConfiguration;
import javafx.scene.image.Image;

/**
 * Loader for textures used by the application. Also caches the texture for
 * later use.
 */
public class TextureLoader {
	private static Map<ResourcesConfiguration, Image> cache = new HashMap<>();

	/**
	 * Checks if a wanted texture is in the cache and loads it if not. Gives back
	 * the texture.
	 */
	public static Image loadImage(ResourcesConfiguration resource) {
		Image image = cache.get(resource);
		if (image == null) {
			String path = ResourcesConfiguration.getConfiguration(resource);
			image = new Image("file:" + path, 1280, 1280, true, true);
			cache.put(resource, image);
		}
		return image;
	}
}
