package ch.zhaw.students.adgame.domain.entity;

import ch.zhaw.students.adgame.domain.Die;

import java.io.Serializable;

import ch.zhaw.students.adgame.configuration.ResourcesConfiguration;
import ch.zhaw.students.adgame.domain.Fightable;

/**
 * This is an abstract class that define a fighter for the battle system. It can
 * attack other fighters by reducing their hit points.
 */
public abstract class Fighter extends Entity implements Fightable, Serializable {
	private static final long serialVersionUID = -183017168317954446L;

	private static final int MIN_DAMAGE = 1;

	private int strength;
	private int defense;
	private int hitPoints;
	private int maxHitPoints;
	private int accuracy;

	public Fighter(String name, int maxHitPoints, int strength, int defense, int accuracy,
			ResourcesConfiguration textureKey) {
		super(name, textureKey);
		this.hitPoints = maxHitPoints;
		this.strength = strength;
		this.maxHitPoints = maxHitPoints;
		this.defense = defense;
		this.accuracy = accuracy;
	}

	/**
	 * This method resets the hit points to the maximum value.
	 */
	@Override
	public void resetHitPoints() {
		this.hitPoints = this.maxHitPoints;
	}

	/**
	 * This method is used to attack an enemy. If the attack is successful the enemy
	 * takes damage. If the attack fails, the enemy takes no damage at all.
	 */
	@Override
	public int attack(Fightable enemy) {
		int damage = calculateDamage(enemy.getDefense());
		if (isAttackSuccessful()) {
			enemy.reduceHitPoints(damage);
			return damage;
		}
		return 0;
	}

	private int calculateDamage(int defense) {
		int damage = Math.max(Die.roll(Die.D6) + this.strength - defense, MIN_DAMAGE);
		return damage;
	}

	private boolean isAttackSuccessful() {
		return Die.roll(Die.D100) <= this.accuracy;
	}

	/**
	 * This method is used to reduce hit points of the fighter.
	 */
	@Override
	public void reduceHitPoints(int damage) {
		this.hitPoints -= damage;
		if (this.hitPoints < 0)
			this.hitPoints = 0;
	}

	@Override
	public int getMaxHitPoints() {
		return maxHitPoints;
	}

	@Override
	public int getHitPoints() {
		return this.hitPoints;
	}

	@Override
	public int getStrength() {
		return this.strength;
	}

	@Override
	public int getDefense() {
		return this.defense;
	}

	@Override
	public int getAccuracy() {
		return accuracy;
	}

	protected void setDefense(int defense) {
		this.defense = defense;
	}

	protected void setStrength(int strength) {
		this.strength = strength;
	}

	protected void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

}
