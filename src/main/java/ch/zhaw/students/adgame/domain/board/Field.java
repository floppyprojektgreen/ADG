package ch.zhaw.students.adgame.domain.board;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ch.zhaw.students.adgame.domain.entity.Character;

/**
 * This class represents a single field on the board. It has methods to get its
 * information, such as characters on field, field type and if it is empty.
 */
public class Field implements Serializable {
	private static final long serialVersionUID = 3429042289463105300L;
	private int vertices;
	private FieldType fieldType;
	private List<Character> charactersOnField;

	public Field() {
		charactersOnField = new ArrayList<>();
	}

	public FieldType getFieldType() {
		return this.fieldType;
	}

	/**
	 * Returns the other character on the field If only one Character on field:
	 * returns currentCharacter If no character on Field: returns null to test
	 * 
	 */
	public Character getOtherCharacter(Character currentCharacter) {
		Character otherCharacter = null;
		if (charactersOnField.size() == 1)
			return currentCharacter;
		for (Character character : charactersOnField) {
			if (character != currentCharacter) {
				otherCharacter = character;
			}
		}
		return otherCharacter;
	}

	public void setFieldType(FieldType fieldType) {
		this.fieldType = fieldType;
	}

	/**
	 * 
	 */
	public void clearField() {
		this.fieldType = null;
	}

	public boolean isEmpty() {
		return fieldType == null;
	}

	public Field(int vertices) {
		this.vertices = vertices;
	}

	public int getVertices() {
		return vertices;
	}

	public void setVertices(int vertices) {
		this.vertices = vertices;
	}

	public List<Character> getCharactersOnField() {
		return charactersOnField;
	}

	/**
	 * This method adds a character to the field.
	 */
	public void addCharacterToField(Character character) {
		charactersOnField.add(character);
	}
}
