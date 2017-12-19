package ch.zhaw.students.adgame.domain.item;

import java.io.Serializable;

import ch.zhaw.students.adgame.configuration.Texture;

/**
 * This class is the representation of armor. The characters can wear this to
 * increase their defense and is used for battles.
 */
public class Armor extends Equipment implements Serializable {
	private static final long serialVersionUID = -2022792664285959438L;
	private int defense;

	public Armor(String name, int cost, int defense, Texture.Item.Equipment.Armor textureKey) {
		super(name, cost, textureKey);
		this.defense = defense;
	}

	public int getDefense() {
		return defense;
	}
}
