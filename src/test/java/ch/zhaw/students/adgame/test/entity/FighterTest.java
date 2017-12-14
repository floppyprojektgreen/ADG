package ch.zhaw.students.adgame.test.entity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ch.zhaw.students.adgame.configuration.Texture;
import ch.zhaw.students.adgame.domain.entity.Character;
import ch.zhaw.students.adgame.domain.entity.Fighter;
import ch.zhaw.students.adgame.domain.entity.Monster;
import ch.zhaw.students.adgame.domain.item.Armor;
import ch.zhaw.students.adgame.domain.item.Weapon;

public class FighterTest {

	private Fighter character;
	private Fighter monster;

	@Mock
	private Weapon mockWeapon;

	@Mock
	private Armor mockArmor;

	@Before
	public void setUp() {
		mockWeapon = mock(Weapon.class);
		mockArmor = mock(Armor.class);
		when(mockWeapon.getAttackPower()).thenReturn(5);
		when(mockWeapon.getAccuracy()).thenReturn(6);
		when(mockArmor.getDefense()).thenReturn(20);
		this.character = new Character("TestCharacter", 100, 0, 0, 0, 9001, 0, 0, Texture.Player.PINK);
		this.monster = new Monster("Monster", 100, 4, 5, 6, Texture.Enemy.ORC);
		((Character) this.character).equipWeapon(mockWeapon);
		((Character) this.character).equipArmor(mockArmor);
	}

	@Test
	public void testFightingValues() {
		assertEquals(20, character.getDefense());
		assertEquals(5, character.getStrength());
		assertEquals(6, character.getAccuracy());
		assertEquals(100, character.getHitPoints());
		assertEquals(100, monster.getHitPoints());
		assertEquals(4, monster.getStrength());
		assertEquals(5, monster.getDefense());
		assertEquals(6, monster.getAccuracy());
	}

	@Test
	public void testAttack() {
	}

	@After
	public void tearDown() {
	}
}
