package ch.zhaw.students.adgame.domain.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ch.zhaw.students.adgame.configuration.Texture;
import ch.zhaw.students.adgame.domain.GameState;
import ch.zhaw.students.adgame.domain.board.Board;
import ch.zhaw.students.adgame.domain.board.CardinalDirection;
import ch.zhaw.students.adgame.domain.board.Field;
import ch.zhaw.students.adgame.domain.item.Armor;
import ch.zhaw.students.adgame.domain.item.Item;
import ch.zhaw.students.adgame.domain.item.Weapon;

/**
 * This class is the representation of the character on the field.
 * It can participate in battles, move on the playing field, equip weapons and armor
 * and collect resources or lose them.
 */
public class Character extends Fighter implements Serializable{
	private static final long serialVersionUID = 1148766762028790881L;
	private int xPosition;
	private int yPosition;
	private CardinalDirection previousDirection;
	private Weapon weapon;
	private Armor armor;
	private int resource;
	private List<Item> inventory;
	private boolean isAlive;

	/**
	 * 
	 * @param name
	 * @param strength
	 * @param resource
	 * @param xPosition
	 *            Determined by column index of board
	 * @param yPosition
	 *            Determined by row index of board
	 *
	 */
	public Character(String name, int hitPoints, int strength, int defense, int accuracy, int resource, int xPosition, int yPosition,
			Texture.Player textureKey) {
		super(name, hitPoints, strength, defense, accuracy, textureKey);
		this.resource = resource;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.isAlive = true;
		this.inventory = new ArrayList<>();
		this.previousDirection = CardinalDirection.NO_DIRECTION;
	}

	public int getXPosition() {
		return xPosition;
	}

	public int getYPosition() {
		return yPosition;
	}

	public boolean isAlive() {
		return this.isAlive;
	}

	public void setXPosition(int x) {
		this.xPosition = x;
	}

	public void setYPosition(int y) {
		this.yPosition = y;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	/**
	 * Equip a weapon to a Character
	 */
	public void equipWeapon(Weapon weapon) {
		super.setStrength(weapon.getAttackPower());
		super.setAccuracy(weapon.getAccuracy());
		this.weapon = weapon;
	}

	/**
	 * Returns a list of weapons that the character carries.
	 */
	public List<Weapon> getWeapons() {
		List<Weapon> weapons = new ArrayList<>();
		for (Item item : inventory) {
			if (item instanceof Weapon)
				weapons.add((Weapon) item);
		}
		return weapons;
	}

	public Armor getArmor() {
		return armor;
	}

	/**
	 * Equips armor to the character
	 */
	public void equipArmor(Armor armor) {
		super.setDefense(armor.getDefense());
		this.armor = armor;
	}

	/**
	 * This method adds an item to the inventory of the character.
	 */
	public void addItemToInventory(Item item) {
		inventory.add(item);
	}

	/**
	 * This methods adds items to the inventory of the character.
	 */
	public void addItemsToInventory(List<Item> items) {
		items.addAll(items);
	}

	public int getResource() {
		return resource;
	}

	/**
	 * Reduces resources by the specified amount.
	 */
	public void reduceResources(int amount) {
		amount = Math.abs(amount);
		resource -= amount;
		if (resource <= 0) {
			resource = 0;
			GameState.get().killCharacterInGame(this);
		}
	}

	/**
	 * Add resources by the specified amount
	 */
	public void addResources(int amount) {
		amount = Math.abs(amount);
		if (amount >= 0) {
			resource += amount;
		}
	}

	/**
	 * This method uses a cardinal direction to move on the board.
	 * The offsets for moving are calculated as follows:
	 * North: x=0, y=(-1)
	 * North East (even column number): x=1, y=0
	 * North East (odd column number): x=1, y=(-1)
	 * South East (even column number): x=1, y=1
	 * South East (odd column number): x=1, y=0
	 * South: x=0, y=1
	 * South West (even column number): x=(-1), y=1
	 * South West (odd column number): x=(-1), y=0
	 * North West (even column number): x=(-1), y=0
	 * North West (odd column number): x=(-1), y=(-1)
	 */
	public void move(CardinalDirection cardinalDirection) {
		previousDirection = cardinalDirection;
		Board board = GameState.get().getBoard();
		int boardHeight = board.getHeight();
		int boardWidth = board.getWidth();
		Field currentField = board.getField(this.xPosition, this.yPosition);
		currentField.getCharactersOnField().remove(this);
		int yOffset = 0;
		int xOffset = 0;

		switch (cardinalDirection) {

		case NORTH:
			yOffset = -1;
			xOffset = 0;
			break;
		case NORTH_EAST:

			if (this.xPosition % 2 == 0) {
				xOffset = 1;
				yOffset = 0;
			} else {
				xOffset = 1;
				yOffset = -1;
			}
			break;

		case SOUTH_EAST:
			if (this.xPosition % 2 == 0) {
				xOffset = 1;
				yOffset = 1;
			} else {
				xOffset = 1;
				yOffset = 0;
			}
			break;

		case SOUTH:
			yOffset = 1;
			xOffset = 0;
			break;

		case SOUTH_WEST:
			if (this.xPosition % 2 == 0) {
				xOffset = -1;
				yOffset = 1;
			} else {
				xOffset = -1;
				yOffset = 0;
			}
			break;

		case NORTH_WEST:
			if (this.xPosition % 2 == 0) {
				yOffset = 0;
				xOffset = -1;
			} else {
				xOffset = -1;
				yOffset = -1;
			}
			break;

		default:
			break;
		}

		this.xPosition += xOffset;
		this.yPosition += yOffset;
		normalizePosition(boardHeight, boardWidth);
		Field nextField = board.getField(this.xPosition, this.yPosition);
		nextField.addCharacterToField(this);
	}

	private void normalizePosition(int boardHeight, int boardWidth) {
		if (this.yPosition >= boardHeight)
			this.yPosition -= boardHeight;
		else if (yPosition < 0)
			this.yPosition += boardHeight;

		if (xPosition >= boardWidth)
			this.xPosition -= boardWidth;
		else if (xPosition < 0)
			this.xPosition += boardWidth;
	}

	/**
	 * This method returns the previous direction that the character walked in.
	 */
	public CardinalDirection getPreviousDirection() {
		return this.previousDirection;
	}
}
