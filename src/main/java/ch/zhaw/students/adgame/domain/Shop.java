package ch.zhaw.students.adgame.domain;

import java.util.List;
import java.util.stream.Collectors;

import ch.zhaw.students.adgame.domain.item.Item;
import ch.zhaw.students.adgame.resource.ItemLoader;

/**
 * This class is used to provide a shop for characters. It has methods to
 * determine affordable items and for executing transactions with characters.
 */
public class Shop {
	private List<Item> availableItems;

	public Shop() {
		availableItems = ItemLoader.getAvailableItems();
	}

	/**
	 * This method returns the field types that are affordable by the current
	 * character.
	 */
	public List<Item> getAffordableItems() {
		GameState state = GameState.get();
		return availableItems.stream().filter(t -> t.getCost() <= state.getCurrentCharacter().getResource())
				.collect(Collectors.toList());

	}

	/**
	 * * This method returns the field types that are not affordable by the current
	 * character.
	 */
	public List<Item> getNonAffordableItems() {
		GameState state = GameState.get();
		return availableItems.stream().filter(t -> t.getCost() > state.getCurrentCharacter().getResource())
				.collect(Collectors.toList());
	}

	/**
	 * This method is used to buy an item for the character. It places an item in
	 * the character's inventory and reduces its resources by the amount of the
	 * cost.
	 */
	public void buyItem(Item item) {
		GameState state = GameState.get();
		state.getCurrentCharacter().addItemToInventory(item);
		// TODO error checking, shouldn't be allowed to go below zero maybe implement in Characters methods
		state.getCurrentCharacter().reduceResources(item.getCost());
	}
}
