package ch.zhaw.students.adgame.domain.event;

import java.io.Serializable;

import ch.zhaw.students.adgame.configuration.ResourcesConfiguration;
import ch.zhaw.students.adgame.domain.GameState;
import ch.zhaw.students.adgame.domain.board.Field;
import ch.zhaw.students.adgame.domain.entity.Character;

/**
 * This class is the representation of a teleport event. It has methods to
 * determine random vacant fields and move characters to it.
 */
public class Teleport implements Invokable, Serializable {
	private static final long serialVersionUID = -2592329936364724325L;
	private Field destinationField;
	private ResourcesConfiguration textureKey;

	public Teleport(ResourcesConfiguration textureKey) {
		this.textureKey = textureKey;
	}

	public void setDestinationField(Field field) {
		this.destinationField = field;
	}

	public Field getDestinationField() {
		return this.destinationField;
	}

	private Field chooseRandomFieldWithNoCharacters() {
		Field destinationField = GameState.get().getBoard().getRandomField();
		while (!destinationField.getCharactersOnField().isEmpty()) {
			destinationField = GameState.get().getBoard().getRandomField();
		}
		return destinationField;
	}

	private void removeCharacterFromField(Character character) {
		GameState.get().getBoard().getField(character.getXPosition(), character.getYPosition()).getCharactersOnField()
				.remove(character);
	}

	/**
	 * Moves a character to a vacant field by removing it from the field it is
	 * currently standing on and placing it to a random vacant field.
	 */
	@Override
	public void invoke(Character character) {
		this.destinationField = chooseRandomFieldWithNoCharacters();
		this.destinationField.addCharacterToField(character);
		for (int x = 0; x < GameState.get().getBoard().getWidth(); x++) {
			for (int y = 0; y < GameState.get().getBoard().getHeight(); y++) {
				if (GameState.get().getBoard().getField(x, y).equals(this.destinationField)) {
					removeCharacterFromField(character);
					character.setXPosition(x);
					character.setYPosition(y);
				}
			}
		}
	}

	@Override
	public ResourcesConfiguration getTextureKey() {
		return textureKey;
	}

}
