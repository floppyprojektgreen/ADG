package ch.zhaw.students.adgame.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Enumeration with keys for the resource configuration file.
 */
public enum ResourcesConfiguration {
	LOGO("icon_logo"),
	BOARD_COMPASS("board_compass"),
	BOARD_COMPASS_NEEDLE("board_compass_needle"),
	EMENY_ORC("enemy_orc"),
	EMENY_SKELETON("enemy_skeleton"),
	EMENY_WARCAMP_BANNER("enemy_warcamp_banner"),
	EQ_NO_WEAPON("eqp_no_weapon"),
	EQ_ARMOR_BARREL("armor_barrel"),
	EQ_ARMOR_GARMENT("armor_garment"),
	EQ_ARMOR_LEATHER("armor_leather"),
	EQ_ARMOR_IRON("armor_iron"),
	EQ_BOW_BASIC("bow_basic"),
	EQ_DAGGER_BASIC("dagger_basic"),
	EQ_HAMMER_WARHAMMER("hammer_warhammer"),
	EQ_MACE_BASIC("mace_basic"),
	EQ_SWORD_COMMON("sword_common"),
	FIELD_TELEPORTER("field_teleporter"),
	FIELD_CHEST("field_chest"),
	HUD_SOUL("hud_soul"),
	HUD_MENU_MOVE("hud_menu_move"),
	HUD_MENU_SHOP("hud_menu_shop"),
	HUD_MENU_ENDTURN("hud_menu_endturn"),
	HUD_MENU_SAVE("hud_menu_save"),
	HUD_MENU_EXIT("hud_menu_exit"),
	PLAYER_BLUE("char_blue"),
	PLAYER_GREEN("char_green"),
	PLAYER_LIME("char_lime"),
	PLAYER_ORANGE("char_orange"),
	PLAYER_PINK("char_pink"),
	PLAYER_PURPLE("char_purple"),
	PLAYER_RED("char_red"),
	PLAYER_TURQUOISE("char_turquoise");
	
	private String key;
	
	private ResourcesConfiguration(String key) {
		this.key = key;
	}
	
	private static Properties resourceProp;
	
	static {
		resourceProp = new XProperties();
		
		try (InputStream in = new FileInputStream(MainConfiguration.RESOURCE_CONFIGURATION)) {
			resourceProp.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the loaded configuration for the given configuration key.
	 */
	public static String getConfiguration(ResourcesConfiguration configKey) {
		return resourceProp.getProperty(configKey.key);
	}
}
