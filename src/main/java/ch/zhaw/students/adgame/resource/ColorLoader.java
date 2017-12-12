package ch.zhaw.students.adgame.resource;

import java.util.HashMap;
import java.util.Map;

import ch.zhaw.students.adgame.configuration.ColorConfiguration;
import javafx.scene.paint.Color;

/**
 * Loader for colors used by the application. Also caches the colors for later use.
 */
public class ColorLoader {
	private static Map<ColorConfiguration, Color> cache = new HashMap<>();
	
	/**
	 * Checks if a wanted color is in the cache and loads it if not. Gives back the color.
	 */
	public static Color loadColor(ColorConfiguration colorConfig) {
		Color color = cache.get(colorConfig);
		if (color == null) {
			String colorCode = ColorConfiguration.getConfiguration(colorConfig);
			color = Color.web(colorCode);
			
			color = cache.values().stream().filter(color::equals).findFirst().orElse(color);
			cache.put(colorConfig, color);
		}
		return color;
	}
}
