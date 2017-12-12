package ch.zhaw.students.adgame.domain.event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import ch.zhaw.students.adgame.configuration.ResourcesConfiguration;
import ch.zhaw.students.adgame.domain.RoundClock;
import ch.zhaw.students.adgame.domain.entity.Character;
import ch.zhaw.students.adgame.domain.item.Item;

/**
 * This class is the representation of the random benefit event. It grants a
 * character random benefits.
 */
public class RandomBenefit implements Invokable, Serializable {
	private static final long serialVersionUID = 7909247758872524455L;

	private int resources;
	private List<Item> items;
	private ResourcesConfiguration textureKey;

	public RandomBenefit(int resources, List<Item> items, ResourcesConfiguration textureKey) {
		this.resources = resources;
		this.items = items;
		this.textureKey = textureKey;
	}

	public RandomBenefit(int rounds) {
		this(ThreadLocalRandom.current().nextInt(rounds, rounds * 5), new ArrayList<>(),
				ResourcesConfiguration.FIELD_CHEST);
	}

	public RandomBenefit() {
		this(RoundClock.get().getRounds());
	}

	public void setResources(int resources) {
		this.resources = resources;
	}

	public int getResources() {
		return this.resources;
	}

	public List<Item> getItems() {
		return this.items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	/**
	 * Adds randomly generated benefits to the character.
	 */
	@Override
	public void invoke(Character character) {
		character.addResources(getResources());
	}

	@Override
	public ResourcesConfiguration getTextureKey() {
		return textureKey;
	}
}
