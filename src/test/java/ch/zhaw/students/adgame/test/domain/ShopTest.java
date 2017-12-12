package ch.zhaw.students.adgame.test.domain;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import org.mockito.Mock;

import ch.zhaw.students.adgame.domain.GameState;
import ch.zhaw.students.adgame.domain.Shop;
import ch.zhaw.students.adgame.domain.entity.Character;
import ch.zhaw.students.adgame.domain.item.Item;

public class ShopTest {
	
	private Shop shop;
	
	@Mock
	private Item item;
	
	@Mock
	private Character character;
	private Character character2;
	
	@Before
	public void setUp() {
		shop = new Shop();
		item = mock(Item.class);
		character = mock(Character.class);
		character2 = mock(Character.class);
		when(item.getCost()).thenReturn(1);
	}
	
	@Test
	public void intergrationTestBuyItem() {
		GameState state = GameState.get();
		state.init(10, 7, character, character2);
		shop.buyItem(item);
		verify(character).reduceResources(1);
		state.endTurn();
		when(item.getCost()).thenReturn(10);
		shop.buyItem(item);
		verify(character2).reduceResources(10);
	}
	
	@AfterClass
	public static void tearDown() {
		
	}
	
}



