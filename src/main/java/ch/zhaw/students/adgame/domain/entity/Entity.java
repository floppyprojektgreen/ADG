package ch.zhaw.students.adgame.domain.entity;

import java.io.Serializable;

import ch.zhaw.students.adgame.configuration.ResourcesConfiguration;

/**
 * This class is an abstract representation of anything from an immovable to
 * characters except items that are used by characters.
 */
public abstract class Entity implements Serializable {
	private static final long serialVersionUID = -565506035009241806L;
	protected String name;
	private ResourcesConfiguration textureKey;

	public Entity(String name, ResourcesConfiguration textureKey) {
		this.name = name;
		this.textureKey = textureKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ResourcesConfiguration getTextureKey() {
		return textureKey;
	}
}
