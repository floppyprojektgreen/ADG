package ch.zhaw.students.adgame.test.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import ch.zhaw.students.adgame.domain.Die;
import ch.zhaw.students.adgame.domain.Fightable;
import ch.zhaw.students.adgame.domain.GameState;
import ch.zhaw.students.adgame.domain.entity.Character;
import ch.zhaw.students.adgame.domain.entity.Fighter;
import ch.zhaw.students.adgame.domain.entity.Monster;
import ch.zhaw.students.adgame.domain.event.Battle;
import ch.zhaw.students.adgame.domain.item.Armor;
import ch.zhaw.students.adgame.domain.item.Weapon;

public class BattleTest {

	private Character character1;
	private Character character2;
	private Fightable monster;
	private Weapon weapon;
	private Armor armor;
	private Battle battle;
	private List<Weapon> weapons = new ArrayList<>();
	
	
	@Before
	public void setUp() {
		weapon = mock(Weapon.class);
		armor = mock(Armor.class);
		character1 = mock(Character.class);
		character2 = mock(Character.class);
		monster = mock(Monster.class);
		when(weapon.getAttackPower()).thenReturn(5);
		when(weapon.getAccuracy()).thenReturn(6);
		when(armor.getDefense()).thenReturn(20);
		when(character1.getWeapons()).thenReturn(weapons);
		when(character1.getHitPoints()).thenReturn(10);
		when(character2.getHitPoints()).thenReturn(10);
		when(character2.getWeapons()).thenReturn(weapons);
		when(monster.getDefense()).thenReturn(20);
		when(monster.getHitPoints()).thenReturn(10);
		when(character1.attack(monster)).thenReturn(4);
	}
	
	
	@Test
	public void testBattleWithMonster() {
		Fightable test;
		battle = new Battle(character1, monster);
		battle.equipCharacterWithWeapon(weapon);
		verify(character1).equipWeapon(weapon);
		assertEquals(monster, battle.getFightable());
		test = battle.getFirstFighterToRoll();
		assertTrue((character1 == test)||(monster == test));
		battle.characterAttacksFightable();
		assertEquals(4 , battle.characterAttacksFightable().getInflictedDamage());
		when(character1.getHitPoints()).thenReturn(12);
		when(monster.getHitPoints()).thenReturn(12);
		assertFalse(battle.isFinished());
		assertEquals(0 , battle.fightableAttacksCharacter().getInflictedDamage());
		assertFalse(battle.fightableAttacksCharacter().isBattleFinished());
		when(character1.getHitPoints()).thenReturn(0);
		assertTrue(battle.isFinished());
	}
	
	@Test
	public void testBattleWithCharacter() {
		Fightable testfighter;
		battle = new Battle(character1, character2);
		battle.equipCharacterWithWeapon(weapon);
		assertEquals(weapons, battle.getWeaponsOfOpponentCharacter());
		battle.equipOpponentCharacterWithWeapon(weapon);
		verify(character1).equipWeapon(weapon);
		verify(character2).equipWeapon(weapon);
		testfighter = battle.getFirstFighterToRoll();
		assertTrue((testfighter == character1) || (testfighter == character2));
		when(character1.attack(character2)).thenReturn(5);
		when(character2.attack(character1)).thenReturn(50);
		assertEquals(5 , battle.characterAttacksFightable().getInflictedDamage());
		assertEquals(50 , battle.fightableAttacksCharacter().getInflictedDamage());
		assertFalse(battle.isFinished());
		when(character1.getHitPoints()).thenReturn(0);
		assertTrue(battle.isFinished());
	}
	
}
