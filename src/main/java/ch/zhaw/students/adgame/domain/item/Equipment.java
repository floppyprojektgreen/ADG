package ch.zhaw.students.adgame.domain.item;

import java.io.Serializable;

import ch.zhaw.students.adgame.configuration.ResourcesConfiguration;

/**
 * Abstract representation of Equipment.
 */
public abstract class Equipment extends Item implements Serializable {
	private static final long serialVersionUID = 6395628207313001774L;
	private int type;

	public Equipment(String name, int cost, ResourcesConfiguration textureKey) {
		super(name, cost, textureKey);
	}

	public int getType() {
		return type;
	}
}
