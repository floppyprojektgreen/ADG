package ch.zhaw.students.adgame.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;

import ch.zhaw.students.adgame.logging.LoggingHandler;

/**
 * Enumeration with keys for the color configuration file.
 */
public enum ColorConfiguration {
	MAIN_MENU_PLAY("main_menu_play"),
	MAIN_MENU_LOAD("main_menu_load"),
	MAIN_MENU_EXIT("main_menu_exit"),
	CHARACTER_SELECTION_MENU_PLAY("character_selection_menu_play"),
	CHARACTER_SELECTION_MENU_BACK("character_selection_menu_back"),
	BOARD_MENU_MOVE("board_menu_move"),
	BOARD_MENU_SHOP("board_menu_shop"),
	BOARD_MENU_ENDTURN("board_menu_endturn"),
	BOARD_MENU_SAVE("board_menu_save"),
	BOARD_MENU_EXIT("board_menu_exit"),
	BOARD_BG("board_bg"),
	BOARD_FIELD_OUTLINE("board_field_outline"),
	BOARD_FIELD_FILL("board_field_fill"),
	BOARD_FIELD_CURR_CHAR("board_field_current_character"),
	FIELD_SELECTION_EMPTY("field_selection_empty"),
	FIELD_SELECTION_FIELD("field_selection_field"),
	FIELD_SELECTION_BG("field_selection_bg"),
	SHOP_BG("shop_bg"),
	SHOP_SCROLL_BAR("shop_scroll_bar"),
	SHOP_ROW_EVEN("shop_row_even"),
	SHOP_ROW_ODD("shop_row_odd"),
	SHOP_ROW_HOVER("shop_row_hover"),
	SHOP_EXIT("shop_exit"),
	BATTLE_FIGHTABLE_BG("battle_fightable_bg"),
	BATTLE_FIGHTABLE_BORDER("battle_fightable_border"),
	BATTLE_FIGHTABLE_SPACING("battle_fightable_spacing"),
	BATTLE_CHARACTER_BG("battle_character_bg"),
	BATTLE_CHARACTER_BORDER("battle_character_border"),
	BATTLE_CHARACTER_SPACING("battle_character_spacing"),
	BATTLE_CHARACTER_ATTACK_UP("battle_character_attack_up"),
	BATTLE_CHARACTER_ATTACK_DOWN("battle_character_attack_down"),
	BATTLE_MAIN_CHAR_STROKE("battle_main_char_stroke"),
	BATTLE_MAIN_CHAR_DMG_LOG("battle_main_char_damage_log"),
	BATTLE_MAIN_CHAR_HEALTH("battle_main_char_health"),
	BATTLE_MAIN_CHAR_HEALTH_MISSING("battle_main_char_health_missing"),
	BATTLE_MAIN_CHAR_ATTACK("battle_main_char_attack"),
	BATTLE_SECOND_CHAR_STROKE("battle_second_char_stroke"),
	BATTLE_SECOND_CHAR_DMG_LOG("battle_second_char_damage_log"),
	BATTLE_SECOND_CHAR_HEALTH("battle_second_char_health"),
	BATTLE_SECOND_CHAR_HEALTH_MISSING("battle_second_char_health_missing"),
	BATTLE_SECOND_CHAR_ATTACK("battle_second_char_attack"),
	BATTLE_ENEMY_STROKE("battle_enemy_stroke"),
	BATTLE_ENEMY_DMG_LOG("battle_enemy_damage_log"),
	BATTLE_ENEMY_HEALTH("battle_enemy_health"),
	BATTLE_ENEMY_HEALTH_MISSING("battle_enemy_health_missing");
	
	private String key;
	
	private ColorConfiguration(String key) {
		this.key = key;
	}
	
	private static Properties colorProp;
	
	static {
		colorProp = new XProperties();
		
		try (InputStream in = new FileInputStream(MainConfiguration.COLOR_CONFIGURATION)) {
			colorProp.load(in);
		} catch (IOException e) {
			LoggingHandler.log(e, Level.SEVERE);
		}
	}
	
	/**
	 * Gets the loaded configuration for the given configuration key.
	 */
	public static String getConfiguration(ColorConfiguration configKey) {
		return colorProp.getProperty(configKey.key);
	}
}
