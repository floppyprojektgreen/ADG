package ch.zhaw.students.adgame.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import ch.zhaw.students.adgame.domain.board.Board;
import ch.zhaw.students.adgame.domain.board.CardinalDirection;
import ch.zhaw.students.adgame.domain.board.Field;
import ch.zhaw.students.adgame.domain.board.FieldType;
import ch.zhaw.students.adgame.domain.entity.Character;
import ch.zhaw.students.adgame.domain.entity.Fighter;
import ch.zhaw.students.adgame.domain.event.Battle;
import ch.zhaw.students.adgame.domain.event.RandomBenefit;
import ch.zhaw.students.adgame.domain.event.Teleport;
import ch.zhaw.students.adgame.domain.item.Weapon;
import ch.zhaw.students.adgame.resource.FieldTypeLoader;

/**
 * This class controls the game. It has methods for moving characters, placing
 * fields and various getter- and setter-methods to access the current state of
 * the game and it also uses the round clock to count the rounds.
 */
public class GameState {
	private static GameState instance = null;
	private List<Consumer<ChangeEvent>> changeEventListeners;
	private Board board;
	private List<Character> characters;
	private int activePlayerIndex;
	private List<FieldType> availableFieldTypes;
	private Battle currentBattle;
	private RoundClock roundClock = RoundClock.get();

	private GameState() {
		changeEventListeners = new ArrayList<>();
	}

	public static GameState get() {
		if (instance == null) {
			instance = new GameState();
		}
		return instance;
	}

	public void init(int x, int y, Character... characters) {
		this.board = new Board(x, y);
		this.characters = new ArrayList<>(Arrays.asList(characters));

		availableFieldTypes = FieldTypeLoader.getAvailableFieldTypes();

		for (Character character : this.characters) {
			this.board.getField(character.getXPosition(), character.getYPosition()).addCharacterToField(character);
		}

		this.activePlayerIndex = 0;
	}

	public Character getCurrentCharacter() {
		return characters.get(activePlayerIndex);
	}

	/**
	 * Removes the given character from the character list and from the field he is
	 * standing on. <br>
	 * The next player in line will become active.
	 */
	public void killCharacterInGame(Character character) {
		if (characters.remove(character)) {
			board.getField(character.getXPosition(), character.getYPosition()).getCharactersOnField().remove(character);
			informListeners(ChangeEvent.CHARACTER_DIED);
			if (activePlayerIndex == characters.size()) {
				activePlayerIndex = 0;
			}
		}
	}

	/**
	 * Determines the direction that the character must walk in by rolling a die.
	 */
	public CardinalDirection determineCharacterDirection() {
		int rolledNumber = Die.roll(Die.D6);
		CardinalDirection direction = CardinalDirection.getDirection(rolledNumber);
		while (CardinalDirection.getOppositeDirection(getCurrentCharacter().getPreviousDirection()) == direction) {
			rolledNumber = Die.roll(Die.D6);
			direction = CardinalDirection.getDirection(rolledNumber);
		}
		return direction;
	}

	/**
	 * Moves the character in the specified cardinal direction and informs listeners
	 * what type the field has that the character landed on.
	 */
	public void moveCharacter(CardinalDirection direction) {
		getCurrentCharacter().move(direction);
		Field field = this.board.getField(getCurrentCharacter().getXPosition(), getCurrentCharacter().getYPosition());
		if (field.getCharactersOnField().size() > 1) {
			currentBattle = new Battle(getCurrentCharacter(), field.getOtherCharacter(getCurrentCharacter()));
			informListeners(ChangeEvent.CHARACTER_BATTLE_STARTED);
		} else if (field.isEmpty()) {
			informListeners(ChangeEvent.LANDED_ON_EMPTY_FIELD);
		} else if ((field.getFieldType().getEvent() instanceof Fighter)) {
			currentBattle = new Battle(getCurrentCharacter(), (Fighter) field.getFieldType().getEvent());
			informListeners(ChangeEvent.FIGHTABLE_BATTLE_STARTED);
		} else if (field.getFieldType().getEvent() instanceof RandomBenefit) {
			informListeners(ChangeEvent.FIELD_CHEST);
			field.getFieldType().getEvent().invoke(getCurrentCharacter());
			field.clearField();
		} else if (field.getFieldType().getEvent() instanceof Teleport) {
			informListeners(ChangeEvent.TELEPORTER);
			field.getFieldType().getEvent().invoke(getCurrentCharacter());
			field.clearField();
		}
	}

	private void informListeners(ChangeEvent event) {
		changeEventListeners.forEach(l -> l.accept(event));
	}

	/**
	 * Registers a {@link ChangeEvent} listener.
	 */
	public void registerListener(Consumer<ChangeEvent> listener) {
		changeEventListeners.add(listener);
	}

	public void endTurn() {
		activePlayerIndex++;
		if (activePlayerIndex == characters.size()) {
			activePlayerIndex = 0;
			incrementRound();
		}
	}

	private void incrementRound() {
		roundClock.incrementRounds();
	}

	/**
	 * Place Field This method places a field on a current field
	 */
	public void placeField(FieldType fieldType) {
		getCurrentCharacter().reduceResources(fieldType.getCost());
		this.board.getField(getCurrentCharacter().getXPosition(), getCurrentCharacter().getYPosition())
				.setFieldType(fieldType);
	}

	/**
	 * This method returns the field types that are affordable by the current
	 * character.
	 */
	public List<FieldType> getAffordableFieldTypes() {
		return availableFieldTypes.stream().filter(t -> t.getCost() <= getCurrentCharacter().getResource())
				.collect(Collectors.toList());
	}

	/**
	 * This method returns the field types that are not affordable by the current
	 * character.
	 */
	public List<FieldType> getNotAffordableFieldTypes() {
		return availableFieldTypes.stream().filter(t -> t.getCost() > getCurrentCharacter().getResource())
				.collect(Collectors.toList());
	}

	/**
	 * This method places a benefit randomly on the board
	 */
	public void placeRandomField(FieldType fieldType) {
		this.board.getRandomField().setFieldType(fieldType);
	}

	public void setRounds(int rounds) {
		roundClock.setRounds(rounds);
	}

	public int getRounds() {
		return roundClock.getRounds();
	}

	public List<Weapon> getAvailableWeapons() {
		return getCurrentCharacter().getWeapons();
	}

	public Battle getCurrentBattle() {
		return currentBattle;

	}

	public Board getBoard() {
		return this.board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public List<Character> getCharacters() {
		return this.characters;
	}

	public void setCharacters(List<Character> characters) {
		this.characters = characters;
	}

	public int getActivePlayer() {
		return this.activePlayerIndex;
	}

	public void setActivePlayerIndex(int activePlayer) {
		this.activePlayerIndex = activePlayer;
	}

	public List<FieldType> getAvailableFieldTypes() {
		return availableFieldTypes;
	}

	public void setAvailableFieldTypes(List<FieldType> availableFieldTypes) {
		this.availableFieldTypes = availableFieldTypes;
	}
}
