package ch.zhaw.students.adgame.domain.event;

import java.util.List;

import ch.zhaw.students.adgame.domain.Die;
import ch.zhaw.students.adgame.domain.Fightable;
import ch.zhaw.students.adgame.domain.GameState;
import ch.zhaw.students.adgame.domain.entity.Character;
import ch.zhaw.students.adgame.domain.entity.Monster;
import ch.zhaw.students.adgame.domain.item.Weapon;

/**
 * This class represents a battle between and another character or fightable. It
 * is used to control the battle by selecting which weapons the characters use
 * and ordering the participants to attack. The battle is finished once on of
 * the participants has zero hit points left. At the end the resources are
 * distributed according to the outcome of the battle.
 */
public class Battle {
	private Character character;
	private Fightable fightable;
	private static final int RESOURCE_AMOUNT = 10;

	public Battle(Character character, Fightable opponent) {
		this.character = character;
		fightable = opponent;
	}

	/**
	 * This method checks if the battle is fought between two characters.
	 */
	public boolean isCharacterBattle() {
		return (fightable instanceof Character);
	}

	/**
	 * This method checks if the one of the participants is alive. If not then the
	 * battle is finished.
	 */
	public boolean isFinished() {
		return (character.getHitPoints() <= 0 || fightable.getHitPoints() <= 0);
	}

	/**
	 * Returns the Winner, if the Battle has finished
	 */
	public Fightable getWinner() {
		if (isFinished()) {
			return character.getHitPoints() <= 0 ? fightable : character;
		}
		return null;
	}

	/**
	 * Returns the Loser, if the Battle is finished
	 */
	public Fightable getLoser() {
		if (isFinished()) {
			return character.getHitPoints() <= 0 ? character : fightable;
		}
		return null;
	}

	/**
	 * The Last Method which has to be called, to get the items from the Monster, or
	 * distribute the resources among the player
	 */
	public void distributeResources() {
		if (isCharacterBattle()) {
			Character winner = (Character) getWinner();
			winner.addResources(RESOURCE_AMOUNT);
			Character loser = (Character) getLoser();
			loser.reduceResources(RESOURCE_AMOUNT);
		} else {
			if (getWinner() != fightable)
				character.addItemsToInventory(((Monster) fightable).getRewards());
			else
				character.reduceResources(RESOURCE_AMOUNT);
		}
	}

	/**
	 * This method teleports a character away or removes a monster from the field.
	 */
	private void cleanupField() {
		if (isCharacterBattle() || getLoser() == getCharacter()) {
			Teleport tp = new Teleport(null);
			tp.invoke((Character) getLoser());
		} else {
			GameState.get().getBoard().getField(getCharacter().getXPosition(), getCharacter().getYPosition())
					.clearField();
		}
	}

	private void resetHitPoints() {
		character.resetHitPoints();
		fightable.resetHitPoints();
	}

	/**
	 * This method is used to execute an attack from the character. It checks after
	 * every attack if the enemy is alive and returns the round results.
	 */
	public RoundResults characterAttacksFightable() {
		int damage = character.attack(fightable);
		boolean isFinished = isFinished();
		if (isFinished) {
			distributeResources();
			cleanupField();
			resetHitPoints();
		}
		return new RoundResults(damage, isFinished);
	}

	/**
	 * This method is used to execute an attack from the enemy. It checks after
	 * every attack if the enemy is alive and returns the round results.
	 */
	public RoundResults fightableAttacksCharacter() {
		int damage = fightable.attack(character);
		boolean isFinished = isFinished();
		if (isFinished) {
			distributeResources();
			cleanupField();
			resetHitPoints();
		}
		return new RoundResults(damage, isFinished);
	}

	public Character getCharacter() {
		return character;

	}

	public Fightable getFightable() {
		return fightable;
	}

	/**
	 * Returns all weapons that a character has in his inventory.
	 */
	public List<Weapon> getWeaponsOfCharacter() {
		return character.getWeapons();
	}

	/**
	 * This method returns the weapons of the opponent character.
	 */
	public List<Weapon> getWeaponsOfOpponentCharacter() {
		if (isCharacterBattle())
			return ((Character) fightable).getWeapons();
		return null;
	}

	/**
	 * Equips a weapon to the current character.
	 */
	public void equipCharacterWithWeapon(Weapon weapon) {
		character.equipWeapon(weapon);
	}

	/**
	 * Equips a weapon to the opponentCharacter.
	 */
	public void equipOpponentCharacterWithWeapon(Weapon weapon) {
		if (isCharacterBattle())
			((Character) fightable).equipWeapon(weapon);
	}

	/**
	 * This method decides which fighter makes the first attack.
	 */
	public Fightable getFirstFighterToRoll() {
		int decidingNumber = 0;
		decidingNumber = Die.roll(Die.D6);

		if (decidingNumber % 2 == 0)
			return getCharacter();
		return getFightable();
	}
}
