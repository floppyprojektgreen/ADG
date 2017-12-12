package ch.zhaw.students.adgame.domain.item;

import java.io.Serializable;

import ch.zhaw.students.adgame.configuration.ResourcesConfiguration;

/**
 * Abstract representation of item.
 */
public abstract class Item implements Serializable {
	private static final long serialVersionUID = 9054086180633388830L;
	private String name;
	private int cost;
	private ResourcesConfiguration textureKey;

	public Item(String name, int cost, ResourcesConfiguration textureKey) {
		super();
		this.name = name;
		this.cost = cost;
		this.textureKey = textureKey;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public int getCost() {
		return cost;
	}

	public ResourcesConfiguration getTextureKey() {
		return textureKey;
	}

}
