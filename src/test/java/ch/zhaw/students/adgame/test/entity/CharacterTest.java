package ch.zhaw.students.adgame.test.entity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import ch.zhaw.students.adgame.configuration.ResourcesConfiguration;
import ch.zhaw.students.adgame.domain.GameState;
import ch.zhaw.students.adgame.domain.board.Board;
import ch.zhaw.students.adgame.domain.board.CardinalDirection;
import ch.zhaw.students.adgame.domain.board.Field;
import ch.zhaw.students.adgame.domain.entity.Character;
import ch.zhaw.students.adgame.domain.item.Armor;
import ch.zhaw.students.adgame.domain.item.Weapon;

@RunWith(PowerMockRunner.class)
@PrepareForTest(GameState.class)
public class CharacterTest {
	@Mock
	private Board mockBoard;

	@Mock
	private GameState mockGameState;

	@Mock
	private Field mockField;

	@Mock
	private Weapon mockWeapon;

	@Mock
	private Weapon mockWeapon2;

	@Mock
	private Armor mockArmor;

	private Character characterAtOrigin;
	private Character characterAtUpperRightCorner;
	private Character characterInMiddleOfBoardEven;
	private Character characterInMiddleOfBoardOdd;
	private Character characterAtLeftBoundary;
	private Character characterAtRightBoundary;
	private Character characterAtLowerBoundaryEven;
	private Character characterAtLowerBoundaryOdd;
	private Character characterAtUpperBoundaryEven;
	private Character characterAtUpperBoundaryOdd;

	/**
	 * Board should always have an even number of columns, which means that the
	 * index of the last column is always an odd number.
	 */
	@Before
	public void setUp() {
		mockBoard = mock(Board.class);
		mockGameState = mock(GameState.class);
		mockField = mock(Field.class);
		mockWeapon = mock(Weapon.class);
		mockWeapon2 = mock(Weapon.class);
		mockArmor = mock(Armor.class);

		PowerMockito.mockStatic(GameState.class);
		when(GameState.get()).thenReturn(mockGameState);
		when(mockGameState.getBoard()).thenReturn(mockBoard);
		when(mockBoard.getField(anyInt(), anyInt())).thenReturn(mockField);
		when(mockBoard.getHeight()).thenReturn(10);
		when(mockBoard.getWidth()).thenReturn(10);

		when(mockWeapon.getAttackPower()).thenReturn(5);
		when(mockWeapon.getAccuracy()).thenReturn(10);

		when(mockWeapon2.getAttackPower()).thenReturn(0);
		when(mockWeapon2.getAccuracy()).thenReturn(0);

		when(mockArmor.getDefense()).thenReturn(20);

		characterAtOrigin = new Character("Origin", 9001, 0, 0, 0, 9001, 0, 0, ResourcesConfiguration.PLAYER_GREEN);
		characterAtUpperRightCorner = new Character("Upper Right Corner", 9001, 0, 0, 0, 9001, 9, 0,
				ResourcesConfiguration.PLAYER_GREEN);
		characterInMiddleOfBoardOdd = new Character("Middle", 9001, 0, 0, 0, 9001, 5, 5, ResourcesConfiguration.PLAYER_GREEN);
		characterAtLeftBoundary = new Character("Left", 9001, 0, 0, 0, 9001, 0, 5, ResourcesConfiguration.PLAYER_GREEN);
		characterAtRightBoundary = new Character("Right", 9001, 0, 0, 0, 9001, 9, 5, ResourcesConfiguration.PLAYER_GREEN);
		characterAtLowerBoundaryOdd = new Character("Lower", 9001, 0, 0, 0, 9001, 5, 9, ResourcesConfiguration.PLAYER_GREEN);
		characterAtUpperBoundaryOdd = new Character("Upper", 9001, 0, 0, 0, 9001, 5, 0, ResourcesConfiguration.PLAYER_GREEN);

		characterInMiddleOfBoardEven = new Character("Middle (even)", 9001, 0, 0, 0, 9001, 4, 4,
				ResourcesConfiguration.PLAYER_GREEN);
		characterAtLowerBoundaryEven = new Character("Lower (even)", 9001, 0, 0, 0, 9001, 4, 9,
				ResourcesConfiguration.PLAYER_GREEN);
		characterAtUpperBoundaryEven = new Character("Upper (even)", 9001, 0, 0, 0, 9001, 4, 0,
				ResourcesConfiguration.PLAYER_GREEN);
	}

	@Test
	public void testMoveNorth() {
		characterAtOrigin.move(CardinalDirection.NORTH);
		characterInMiddleOfBoardOdd.move(CardinalDirection.NORTH);
		characterAtLeftBoundary.move(CardinalDirection.NORTH);
		characterAtRightBoundary.move(CardinalDirection.NORTH);
		characterAtLowerBoundaryOdd.move(CardinalDirection.NORTH);
		characterAtUpperBoundaryOdd.move(CardinalDirection.NORTH);

		Assert.assertEquals(0, characterAtOrigin.getXPosition());
		Assert.assertEquals(9, characterAtOrigin.getYPosition());
		Assert.assertEquals(5, characterInMiddleOfBoardOdd.getXPosition());
		Assert.assertEquals(4, characterInMiddleOfBoardOdd.getYPosition());
		Assert.assertEquals(0, characterAtLeftBoundary.getXPosition());
		Assert.assertEquals(4, characterAtLeftBoundary.getYPosition());
		Assert.assertEquals(9, characterAtRightBoundary.getXPosition());
		Assert.assertEquals(4, characterAtRightBoundary.getYPosition());
		Assert.assertEquals(5, characterAtLowerBoundaryOdd.getXPosition());
		Assert.assertEquals(8, characterAtLowerBoundaryOdd.getYPosition());
		Assert.assertEquals(5, characterAtUpperBoundaryOdd.getXPosition());
		Assert.assertEquals(9, characterAtUpperBoundaryOdd.getYPosition());

	}

	@Test
	public void testMoveNorthEast() {
		characterAtOrigin.move(CardinalDirection.NORTH_EAST);
		characterAtUpperRightCorner.move(CardinalDirection.NORTH_EAST);
		characterInMiddleOfBoardOdd.move(CardinalDirection.NORTH_EAST);
		characterAtLeftBoundary.move(CardinalDirection.NORTH_EAST);
		characterAtRightBoundary.move(CardinalDirection.NORTH_EAST);
		characterAtLowerBoundaryOdd.move(CardinalDirection.NORTH_EAST);
		characterAtUpperBoundaryOdd.move(CardinalDirection.NORTH_EAST);
		Assert.assertEquals(1, characterAtOrigin.getXPosition());
		Assert.assertEquals(0, characterAtOrigin.getYPosition());
		Assert.assertEquals(0, characterAtUpperRightCorner.getXPosition());
		Assert.assertEquals(9, characterAtUpperRightCorner.getYPosition());
		Assert.assertEquals(6, characterInMiddleOfBoardOdd.getXPosition());
		Assert.assertEquals(4, characterInMiddleOfBoardOdd.getYPosition());
		Assert.assertEquals(1, characterAtLeftBoundary.getXPosition());
		Assert.assertEquals(5, characterAtLeftBoundary.getYPosition());
		Assert.assertEquals(0, characterAtRightBoundary.getXPosition());
		Assert.assertEquals(4, characterAtRightBoundary.getYPosition());
		Assert.assertEquals(6, characterAtLowerBoundaryOdd.getXPosition());
		Assert.assertEquals(8, characterAtLowerBoundaryOdd.getYPosition());
		Assert.assertEquals(6, characterAtUpperBoundaryOdd.getXPosition());
		Assert.assertEquals(9, characterAtUpperBoundaryOdd.getYPosition());
	}

	@Test
	public void testMoveSouthEast() {
		characterAtOrigin.move(CardinalDirection.SOUTH_EAST);
		characterAtUpperRightCorner.move(CardinalDirection.SOUTH_EAST);
		characterInMiddleOfBoardOdd.move(CardinalDirection.SOUTH_EAST);
		characterAtLeftBoundary.move(CardinalDirection.SOUTH_EAST);
		characterAtRightBoundary.move(CardinalDirection.SOUTH_EAST);
		characterAtLowerBoundaryOdd.move(CardinalDirection.SOUTH_EAST);
		characterAtUpperBoundaryOdd.move(CardinalDirection.SOUTH_EAST);
		Assert.assertEquals(1, characterAtOrigin.getXPosition());
		Assert.assertEquals(1, characterAtOrigin.getYPosition());
		Assert.assertEquals(0, characterAtUpperRightCorner.getXPosition());
		Assert.assertEquals(0, characterAtUpperRightCorner.getYPosition());
		Assert.assertEquals(6, characterInMiddleOfBoardOdd.getXPosition());
		Assert.assertEquals(5, characterInMiddleOfBoardOdd.getYPosition());
		Assert.assertEquals(1, characterAtLeftBoundary.getXPosition());
		Assert.assertEquals(6, characterAtLeftBoundary.getYPosition());
		Assert.assertEquals(0, characterAtRightBoundary.getXPosition());
		Assert.assertEquals(5, characterAtRightBoundary.getYPosition());
		Assert.assertEquals(6, characterAtLowerBoundaryOdd.getXPosition());
		Assert.assertEquals(9, characterAtLowerBoundaryOdd.getYPosition());
		Assert.assertEquals(6, characterAtUpperBoundaryOdd.getXPosition());
		Assert.assertEquals(0, characterAtUpperBoundaryOdd.getYPosition());
	}

	@Test
	public void testMoveSouth() {
		characterAtOrigin.move(CardinalDirection.SOUTH);
		characterInMiddleOfBoardOdd.move(CardinalDirection.SOUTH);
		characterAtLowerBoundaryOdd.move(CardinalDirection.SOUTH);
		Assert.assertEquals(0, characterAtOrigin.getXPosition());
		Assert.assertEquals(1, characterAtOrigin.getYPosition());
		Assert.assertEquals(5, characterInMiddleOfBoardOdd.getXPosition());
		Assert.assertEquals(6, characterInMiddleOfBoardOdd.getYPosition());
		Assert.assertEquals(5, characterAtLowerBoundaryOdd.getXPosition());
		Assert.assertEquals(0, characterAtLowerBoundaryOdd.getYPosition());
	}

	@Test
	public void testMoveSouthWest() {
		characterAtOrigin.move(CardinalDirection.SOUTH_WEST);
		characterAtLeftBoundary.move(CardinalDirection.SOUTH_WEST);
		characterAtLowerBoundaryEven.move(CardinalDirection.SOUTH_WEST);
		characterAtLowerBoundaryOdd.move(CardinalDirection.SOUTH_WEST);
		characterInMiddleOfBoardOdd.move(CardinalDirection.SOUTH_WEST);
		characterInMiddleOfBoardEven.move(CardinalDirection.SOUTH_WEST);
		Assert.assertEquals(9, characterAtOrigin.getXPosition());
		Assert.assertEquals(1, characterAtOrigin.getYPosition());
		Assert.assertEquals(4, characterInMiddleOfBoardOdd.getXPosition());
		Assert.assertEquals(5, characterInMiddleOfBoardOdd.getYPosition());
		Assert.assertEquals(9, characterAtLeftBoundary.getXPosition());
		Assert.assertEquals(6, characterAtLeftBoundary.getYPosition());
		Assert.assertEquals(4, characterAtLowerBoundaryOdd.getXPosition());
		Assert.assertEquals(9, characterAtLowerBoundaryOdd.getYPosition());
		Assert.assertEquals(3, characterAtLowerBoundaryEven.getXPosition());
		Assert.assertEquals(0, characterAtLowerBoundaryEven.getYPosition());
	}

	@Test
	public void testMoveNorthWest() {
		characterAtOrigin.move(CardinalDirection.NORTH_WEST);
		characterAtLeftBoundary.move(CardinalDirection.NORTH_WEST);
		characterInMiddleOfBoardEven.move(CardinalDirection.NORTH_WEST);
		characterInMiddleOfBoardOdd.move(CardinalDirection.NORTH_WEST);
		characterAtUpperBoundaryEven.move(CardinalDirection.NORTH_WEST);
		characterAtUpperBoundaryOdd.move(CardinalDirection.NORTH_WEST);
		Assert.assertEquals(9, characterAtOrigin.getXPosition());
		Assert.assertEquals(0, characterAtOrigin.getYPosition());
		Assert.assertEquals(4, characterInMiddleOfBoardOdd.getXPosition());
		Assert.assertEquals(4, characterInMiddleOfBoardOdd.getYPosition());
		Assert.assertEquals(3, characterInMiddleOfBoardEven.getXPosition());
		Assert.assertEquals(4, characterInMiddleOfBoardEven.getYPosition());
		Assert.assertEquals(9, characterAtLeftBoundary.getXPosition());
		Assert.assertEquals(5, characterAtLeftBoundary.getYPosition());
		Assert.assertEquals(4, characterAtUpperBoundaryOdd.getXPosition());
		Assert.assertEquals(9, characterAtUpperBoundaryOdd.getYPosition());
		Assert.assertEquals(3, characterAtUpperBoundaryEven.getXPosition());
		Assert.assertEquals(0, characterAtUpperBoundaryEven.getYPosition());
	}

	@Test
	public void testReduceRessources() {
		// test subtract exact amount of resources
		Character richCharacter = new Character("Origin", 9001, 0, 0, 0, 10_000, 0, 0, ResourcesConfiguration.PLAYER_GREEN);
		richCharacter.reduceResources(10_000);
		Assert.assertEquals(0, richCharacter.getResource());

		// test subtract more than amount of resources
		richCharacter = new Character("Origin", 9001, 0, 0, 0, 10_000, 0, 0, ResourcesConfiguration.PLAYER_GREEN);
		richCharacter.reduceResources(12_000);
		Assert.assertEquals(0, richCharacter.getResource());

		// test negative values
		richCharacter = new Character("Origin", 9001, 0, 0, 0, 10_000, 0, 0, ResourcesConfiguration.PLAYER_GREEN);
		richCharacter.reduceResources(-5_000);
		Assert.assertEquals(5_000, richCharacter.getResource());
	}

	@Test
	public void testAddResources() {
		// Test add resources
		Character poorCharacter = new Character("Origin", 9001, 0, 0, 0, 0, 0, 0, ResourcesConfiguration.PLAYER_GREEN);
		poorCharacter.addResources(10_000);
		Assert.assertEquals(10_000, poorCharacter.getResource());

		// Test add negative values
		poorCharacter = new Character("Origin", 9001, 0, 0, 0, 0, 0, 0, ResourcesConfiguration.PLAYER_GREEN);
		poorCharacter.addResources(-10_000);
		Assert.assertEquals(10_000, poorCharacter.getResource());
	}

	@Test
	public void testEquipWeapon() {
		Character testCharacter = new Character("test", 100, 0, 0, 0, 100, 0, 0, ResourcesConfiguration.PLAYER_BLUE);
		assertEquals(0, testCharacter.getStrength());
		assertEquals(0, testCharacter.getAccuracy());
		testCharacter.equipWeapon(mockWeapon);
		assertEquals(5, testCharacter.getStrength());
		assertEquals(10, testCharacter.getAccuracy());
		testCharacter.equipWeapon(mockWeapon2);
		assertEquals(0, testCharacter.getStrength());
		assertEquals(0, testCharacter.getAccuracy());
	}

	@Test
	public void testEquipArmor() {
		Character testCharacter = new Character("test", 100, 0, 0, 0, 100, 0, 0, ResourcesConfiguration.PLAYER_GREEN);
		assertEquals(0, testCharacter.getDefense());
		testCharacter.equipArmor(mockArmor);
		assertEquals(20, testCharacter.getDefense());
	}

	@After
	public void tearDown() {
	}
}
