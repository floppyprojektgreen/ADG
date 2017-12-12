package ch.zhaw.students.adgame.domain.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ch.zhaw.students.adgame.configuration.ResourcesConfiguration;
import ch.zhaw.students.adgame.domain.event.Invokable;
import ch.zhaw.students.adgame.domain.item.Item;

/**
 * This class is the representation of a Monster, which can be fought by
 * characters. Once it is defeated, it drops items that characters can collect
 * and becomes inactive for a set amount of rounds. After its recovery it
 * becomes ready to fight again.
 */
public class Monster extends Fighter implements Invokable, Serializable {
	private static final long serialVersionUID = -3300613058326933371L;
	private List<Item> rewards;
	private boolean isReadyForBattle;

	public Monster(String name, int hitPoints, int strength, int defense, int accuracy,
			ResourcesConfiguration textureKey) {
		super(name, hitPoints, strength, defense, accuracy, textureKey);
		rewards = new ArrayList<>();
	}

	public boolean isReadyForBattle() {
		return this.isReadyForBattle;
	}

	/**
	 * This method is used to control the state of the monster. Setting the value to
	 * true signifies that the monster is ready fight. Setting the value to false
	 * signifies that the monster is currently recovering.
	 */
	public void recover(boolean isReadyForBattle) {
		this.isReadyForBattle = isReadyForBattle;
	}

	public void addItemToRewards(Item item) {
		rewards.add(item);
	}

	public List<Item> getRewards() {
		return this.rewards;
	}

	@Override
	public void invoke(Character character) {
	}
}
