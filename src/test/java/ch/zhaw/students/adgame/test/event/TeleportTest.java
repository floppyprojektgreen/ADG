package ch.zhaw.students.adgame.test.event;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ch.zhaw.students.adgame.configuration.Texture;
import ch.zhaw.students.adgame.domain.GameState;
import ch.zhaw.students.adgame.domain.board.Board;
import ch.zhaw.students.adgame.domain.board.Field;
import ch.zhaw.students.adgame.domain.board.FieldType;
import ch.zhaw.students.adgame.domain.entity.Character;
import ch.zhaw.students.adgame.domain.entity.Monster;
import ch.zhaw.students.adgame.domain.event.Teleport;

public class TeleportTest {
	@Mock
	private Character mockCharacterToTeleport;
	private Character mockCharacter2;
	private Character mockCharacter3;
	private Character mockCharacter4;
	@Mock
	private Monster monster;
	private FieldType monsterFieldType;
	private List<Character> characterList1;
	private List<Character> characterList2;
	private Teleport teleport;
	private Board board1;
	private Board board2;
	private GameState gameState;

	@Before
	public void setUp() {
		mockCharacterToTeleport = mock(Character.class);
		monster = new Monster("Orc", 0, 0, 0, 0, Texture.Enemy.ORC);
		monsterFieldType = new FieldType(monster, "monster field", 9001);
		board1 = new Board(2, 2);
		board2 = new Board(2, 2);
		teleport = new Teleport(Texture.Field.TELEPORTER);
		characterList1 = new ArrayList<>();
		characterList2 = new ArrayList<>();
		characterList1.add(mockCharacterToTeleport);
		characterList1.add(mockCharacter2);
		characterList1.add(mockCharacter3);
		characterList1.add(mockCharacter4);
		characterList2.add(mockCharacterToTeleport);
		characterList2.add(mockCharacter2);
		gameState = GameState.get();

		// Test with three characters and one remaining field.
		gameState.setCharacters(characterList1);
		gameState.setBoard(board1);
		gameState.getBoard().getField(0, 0).addCharacterToField(mockCharacterToTeleport);
		gameState.getBoard().getField(0, 1).addCharacterToField(mockCharacter2);
		gameState.getBoard().getField(1, 0).addCharacterToField(mockCharacter3);
	}

	@Test
	public void testTeleport() {
		teleport.invoke(mockCharacterToTeleport);
		Field destinationField = teleport.getDestinationField();
		assertEquals(gameState.getBoard().getField(1, 1), destinationField);

		// Test if it is possible to land on monster field
		gameState.setCharacters(characterList2);
		gameState.setBoard(board2);
		gameState.getBoard().getField(0, 0).addCharacterToField(mockCharacterToTeleport);
		gameState.getBoard().getField(0, 1).addCharacterToField(mockCharacter2);
		gameState.getBoard().getField(1, 0).setFieldType(monsterFieldType);
		gameState.getBoard().getField(1, 1).addCharacterToField(mockCharacter3);

		teleport.invoke(mockCharacterToTeleport);
		Field destinationField2 = teleport.getDestinationField();
		assertEquals(gameState.getBoard().getField(1, 0), destinationField2);
	}
}
