package ch.zhaw.students.adgame.resource;

import java.util.ArrayList;
import java.util.List;

import ch.zhaw.students.adgame.configuration.FieldConfiguration;
import ch.zhaw.students.adgame.configuration.ResourcesConfiguration;
import ch.zhaw.students.adgame.domain.board.FieldType;
import ch.zhaw.students.adgame.domain.entity.Monster;
import ch.zhaw.students.adgame.domain.event.Teleport;

/**
 * Loader for fieldTypes used by the application. Also caches the fieldTypes for later use.
 */
public class FieldTypeLoader {
	private static List<FieldType> availableFieldTypes;
	
	/**
	 * Gives all the available fieldTypes and loads them if necessary.
	 */
	public static List<FieldType> getAvailableFieldTypes() {
		if (availableFieldTypes == null) {
			availableFieldTypes = new ArrayList<>();
			
			for (String fieldDefinition : FieldConfiguration.getFieldDefinitions()) {
				String[] definitionValues = fieldDefinition.split(":");
				
				switch (definitionValues[0].toLowerCase()) {
					case "teleport":
						availableFieldTypes.add(createTeleportField(definitionValues));
						break;
					case "monster":
						availableFieldTypes.add(createMonsterField(definitionValues));
						break;
					default:
						break;
				}
			}
		}
		return availableFieldTypes;
	}
	
	private static FieldType createTeleportField(String[] def) {
		Teleport teleport = new Teleport(ResourcesConfiguration.valueOf(def[3].toUpperCase()));
		return new FieldType(teleport, def[1], Integer.parseInt(def[2]));
	}

	private static FieldType createMonsterField(String[] def) {
		Monster monster = new Monster(def[1], Integer.parseInt(def[4]), Integer.parseInt(def[5]), Integer.parseInt(def[6]), Integer.parseInt(def[7]), ResourcesConfiguration.valueOf(def[3].toUpperCase()));
		return new FieldType(monster, def[1], Integer.parseInt(def[2]));
	}
}
