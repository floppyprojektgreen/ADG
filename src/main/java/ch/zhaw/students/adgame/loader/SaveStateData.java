package ch.zhaw.students.adgame.loader;

import java.io.Serializable;
import java.util.List;

import ch.zhaw.students.adgame.domain.board.Board;
import ch.zhaw.students.adgame.domain.entity.Character;
import ch.zhaw.students.adgame.domain.board.FieldType;

/**
 * This class represents a save state. It holds data such as the board, the
 * characters, available field types, active player index and round count.
 */
public class SaveStateData implements Serializable {

	private static final long serialVersionUID = -2174674507968294945L;
	private Board board;
	private List<Character> characters;
	private int activePLayerIndex;
	private List<FieldType> availableFieldTypes;
	private int rounds;

	public SaveStateData(Board board, List<Character> characters, int activePLayerIndex,
			List<FieldType> availableFieldTypes, int rounds) {
		this.board = board;
		this.characters = characters;
		this.activePLayerIndex = activePLayerIndex;
		this.availableFieldTypes = availableFieldTypes;
		this.rounds = rounds;
	}

	public Board getBoard() {
		return this.board;
	}

	public List<Character> getCharacters() {
		return this.characters;
	}

	public int getActivePLayer() {
		return this.activePLayerIndex;
	}

	public List<FieldType> getAvailableFieldTypes() {
		return this.availableFieldTypes;
	}

	public int getRounds() {
		return this.rounds;
	}

}
