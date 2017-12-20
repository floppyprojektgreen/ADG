package ch.zhaw.students.adgame.resource;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import ch.zhaw.students.adgame.configuration.Texture;
import javafx.scene.image.Image;

/**
 * Loader for textures used by the application. Also caches the texture for
 * later use.
 */
public class TextureLoader {
	private static Map<Texture, Image> cache = new HashMap<>();

	/**
	 * Checks if a wanted texture is in the cache and loads it if not. Gives back
	 * the texture.
	 */
	public static Image loadImage(Texture resource) {
		Image image = cache.get(resource);
		if (image == null) {
			String path = Texture.getConfiguration(resource);
			File file = new File(path);
			if (file.exists()) {
				image = new Image(file.toURI().toString(), 1280, 1280, true, true);
				cache.put(resource, image);
			} else {
				image = new Image("file:" + Texture.getConfiguration(null), 1280, 1280, true, true);
			}
		}
		return image;
	}
	
	/**
	 * clear everything in the texture cache.
	 */
	public static void clearCache() {
		cache.clear();
	}
}
