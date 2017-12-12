package ch.zhaw.students.adgame.test.domain.board;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import ch.zhaw.students.adgame.domain.board.Field;
import ch.zhaw.students.adgame.domain.entity.Character;

public class FieldTest {
	@Mock
	private Character mockCharacter1;
	@Mock
	private Character mockCharacter2;
	private Field field;

	@Before
	public void setUp() {
		field = new Field();
		mockCharacter1 = mock(Character.class);
		mockCharacter2 = mock(Character.class);
		field.addCharacterToField(mockCharacter1);
		field.addCharacterToField(mockCharacter2);
	}

	@Test
	public void testGetOtherCharacter() {
		Character otherCharacterOnField = field.getOtherCharacter(mockCharacter1);
		assertEquals(otherCharacterOnField, mockCharacter2);
	}
}
