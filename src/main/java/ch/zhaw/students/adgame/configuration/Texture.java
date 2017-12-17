package ch.zhaw.students.adgame.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

/**
 * Enumeration with keys for the resource configuration file.
 */
public interface Texture {
	public enum General implements Texture {
		LOGO("icon_logo"),
		BOARD_COMPASS("board_compass"),
		BOARD_COMPASS_NEEDLE("board_compass_needle");
		
		private General(String key) {
			keyMap.put(this, key);
		}
	}
	
	public enum Hud implements Texture {
		SOUL("hud_soul"),
		MENU_MOVE("hud_menu_move"),
		MENU_SHOP("hud_menu_shop"),
		MENU_ENDTURN("hud_menu_endturn"),
		MENU_SAVE("hud_menu_save"),
		MENU_EXIT("hud_menu_exit");
		
		private Hud(String key) {
			keyMap.put(this, key);
		}
	}
	
	public interface Item extends Texture {
		public interface Equipment extends Item {
			public interface Armor extends Equipment {
				public enum Leather implements Armor {
					LEATHER_ARMOR("arm_let_leather_comn"),
					UNCOMMON_LEATHER_ARMOR("arm_let_leather_unco"),
					RARE_LEATHER_ARMOR("arm_let_leather_rare"),
					EPIC_LEATHER_ARMOR("arm_let_leather_epic");
					
					private Leather(String key) {
						keyMap.put(this, key);
					}
				}
				
				public enum Plate implements Armor {
					BARREL("arm_plt_barrel_comn"),
					UNCOMMON_BARREL("arm_plt_barrel_unco"),
					RARE_BARREL("arm_plt_barrel_rare"),
					EPIC_BARREL("arm_plt_barrel_epic"),
					IRON_ARMOR("arm_plt_iron_comn"),
					UNCOMMON_IRON_ARMOR("arm_plt_iron_unco"),
					RARE_IRON_ARMOR("arm_plt_iron_rare"),
					EPIC_IRON_ARMOR("arm_plt_iron_epic");
					
					private Plate(String key) {
						keyMap.put(this, key);
					}
				}
				
				public enum Robe implements Armor {
					GARMENT("arm_robe_garment_comn"),
					UNCOMMON_GARMENT("arm_robe_garment_unco"),
					RARE_GARMENT("arm_robe_garment_rare"),
					EPIC_GARMENT("arm_robe_garment_epic");
					
					private Robe(String key) {
						keyMap.put(this, key);
					}
				}
			}
			
			public interface Weapon extends Equipment {
				public enum General implements Weapon {
					NO_WEAPON("wpn_no_weapon");
					
					private General(String key) {
						keyMap.put(this, key);
					}
				}
				
				public enum Bow implements Weapon {
					BOW("wpn_bow_bow_comn"),
					UNCOMMON_BOW("wpn_bow_bow_unco"),
					RARE_BOW("wpn_bow_bow_rare"),
					EPIC_BOW("wpn_bow_bow_epic");
					
					private Bow(String key) {
						keyMap.put(this, key);
					}
				}
				
				public enum Dagger implements Weapon {
					DAGGER("wpn_dag_dagger_comn"),
					UNCOMMON_DAGGER("wpn_dag_dagger_unco"),
					RARE_DAGGER("wpn_dag_dagger_rare"),
					EPIC_DAGGER("wpn_dag_dagger_epic");
					
					private Dagger(String key) {
						keyMap.put(this, key);
					}
				}
				
				public enum Hammer implements Weapon {
					WARHAMMER("wpn_hmr_warhammer_comn"),
					UNCOMMON_WARHAMMER("wpn_hmr_warhammer_unco"),
					RARE_WARHAMMER("wpn_hmr_warhammer_rare"),
					EPIC_WARHAMMER("wpn_hmr_warhammer_epic");
					
					private Hammer(String key) {
						keyMap.put(this, key);
					}
				}
				
				public enum Mace implements Weapon {
					MACE("wpn_mce_mace_comn"),
					UNCOMMON_MACE("wpn_mce_mace_unco"),
					RARE_MACE("wpn_mce_mace_rare"),
					EPIC_MACE("wpn_mce_mace_epic");
					
					private Mace(String key) {
						keyMap.put(this, key);
					}
				}
				
				public enum Sword implements Weapon {
					SWORD("wpn_swd_sword_comn"),
					UNCOMMON_SWORD("wpn_swd_sword_unco"),
					RARE_SWORD("wpn_swd_sword_rare"),
					EPIC_SWORD("wpn_swd_sword_epic");
					
					private Sword(String key) {
						keyMap.put(this, key);
					}
				}
			}
		}
	}
	
	public enum Enemy implements Texture {
		ORC("enemy_orc"),
		SKELETON("enemy_skeleton"),
		WARCAMP_BANNER("enemy_warcamp_banner");
		
		private Enemy(String key) {
			keyMap.put(this, key);
		}
	}
	
	public enum Field implements Texture {
		TELEPORTER("field_teleporter"),
		CHEST("field_chest");
		
		private Field(String key) {
			keyMap.put(this, key);
		}
	}
	
	public enum Player implements Texture {
		BLUE("char_blue"),
		GREEN("char_green"),
		LIME("char_lime"),
		ORANGE("char_orange"),
		PINK("char_pink"),
		PURPLE("char_purple"),
		RED("char_red"),
		TURQUOISE("char_turquoise");
		
		private Player(String key) {
			keyMap.put(this, key);
		}
	}

	
	static Properties resourceProp = new XProperties();
	static Map<Texture, String> keyMap = new HashMap<>();	
	
	/**
	 * Gets the loaded configuration for the given configuration key.
	 */
	public static String getConfiguration(Texture configKey) {
		if (resourceProp.isEmpty()) {
			try (InputStream in = new FileInputStream(MainConfiguration.RESOURCE_CONFIGURATION)) {
				resourceProp.load(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return resourceProp.getProperty(keyMap.getOrDefault(configKey, ""), resourceProp.getProperty("tex_missing"));
	}
	
	public static <T extends Texture> T getTextureByExternalKey(Class<T> typeRestriction, String texture) {
		int valStartIndex = texture.lastIndexOf(".");
		
		try {
			Class<? extends T> textureClass = typeRestriction.isEnum()
					? typeRestriction
					: Class.forName(typeRestriction.getTypeName() + "$" + texture.substring(0, 1).toUpperCase() + texture.substring(1, valStartIndex).toLowerCase()).asSubclass(typeRestriction);
			
			return Arrays.stream(textureClass.getEnumConstants())
				.filter(t -> t.toString().equalsIgnoreCase(texture.substring(valStartIndex + 1).trim()))
				.findFirst().get();
		} catch (ClassNotFoundException | StringIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NoSuchElementException e) {}
		
		return null;
	}
}
