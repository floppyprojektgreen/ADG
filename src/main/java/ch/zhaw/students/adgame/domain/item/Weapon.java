package ch.zhaw.students.adgame.domain.item;

import java.io.Serializable;

import ch.zhaw.students.adgame.configuration.Texture;

/**
 * This class is used for the battle system to determine the strength of a
 * character by equipping it. It has two stats that influence the outcome of a
 * battle: strength and accuracy.
 */
public class Weapon extends Equipment implements Serializable {
	private static final long serialVersionUID = -1900585704219470675L;
	private int attackPower;
	private int accuracy;

	public Weapon(String name, int cost, int attackPower, int accuracy, Texture.Item.Equipment.Weapon textureKey) {
		super(name, cost, textureKey);
		this.attackPower = attackPower;
		this.accuracy = accuracy;
	}

	public int getAttackPower() {
		return attackPower;
	}

	public int getAccuracy() {
		return accuracy;
	}
}
