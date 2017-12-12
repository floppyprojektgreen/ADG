package ch.zhaw.students.adgame.resource;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.students.adgame.configuration.ItemConfiguration;
import ch.zhaw.students.adgame.configuration.ResourcesConfiguration;
import ch.zhaw.students.adgame.domain.item.Armor;
import ch.zhaw.students.adgame.domain.item.Item;
import ch.zhaw.students.adgame.domain.item.Weapon;

/**
 * Loader for items used by the application. Also caches the items for later
 * use.
 */
public class ItemLoader {
	private static List<Item> availableItems;

	/**
	 * returns all the available items and loads them if necessary.
	 */
	public static List<Item> getAvailableItems() {
		if (availableItems == null) {
			availableItems = new ArrayList<>();

			for (String itemDefinition : ItemConfiguration.getItemDefinitions()) {
				String[] definitionValues = itemDefinition.split(":");

				switch (definitionValues[0].toLowerCase()) {
				case "armor":
					availableItems.add(createArmor(definitionValues));
					break;
				case "weapon":
					availableItems.add(createWeapon(definitionValues));
					break;
				default:
					break;
				}
			}
		}

		return availableItems;
	}

	private static Armor createArmor(String[] armorString) {
		return new Armor(armorString[1], Integer.parseInt(armorString[2]), Integer.parseInt(armorString[4]),
				ResourcesConfiguration.valueOf(armorString[3].toUpperCase()));
	}

	private static Weapon createWeapon(String[] armorString) {
		return new Weapon(armorString[1], Integer.parseInt(armorString[2]), Integer.parseInt(armorString[4]),
				Integer.parseInt(armorString[5]), ResourcesConfiguration.valueOf(armorString[3].toUpperCase()));
	}

}
