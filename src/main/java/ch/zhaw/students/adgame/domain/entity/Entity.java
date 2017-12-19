package ch.zhaw.students.adgame.domain.entity;

import java.io.Serializable;

import ch.zhaw.students.adgame.configuration.Texture;

/**
 * This class is an abstract representation of anything from an immovable to
 * characters except items that are used by characters.
 */
public abstract class Entity implements Serializable {
	private static final long serialVersionUID = -565506035009241806L;
	protected String name;
	private Texture textureKey;

	public Entity(String name, Texture textureKey) {
		this.name = name;
		this.textureKey = textureKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Texture getTextureKey() {
		return textureKey;
	}
}
