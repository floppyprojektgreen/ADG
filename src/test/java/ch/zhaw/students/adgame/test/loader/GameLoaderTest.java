package ch.zhaw.students.adgame.test.loader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import java.io.File;
import java.io.IOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.zhaw.students.adgame.configuration.ResourcesConfiguration;
import ch.zhaw.students.adgame.domain.GameState;
import ch.zhaw.students.adgame.domain.entity.Character;
import ch.zhaw.students.adgame.loader.GameLoader;
import ch.zhaw.students.adgame.loader.GameSaver;

public class GameLoaderTest {
	private static File file;
	private static GameState state;
	
	
	@BeforeClass
	public static void buildUp() {
		state = GameState.get();
		state.init(10, 7, new Character("test", 120, 0, 0, 0, 50, 2, 1, ResourcesConfiguration.PLAYER_PINK), new Character("test2", 120, 0, 0, 0, 100, 7, 5, ResourcesConfiguration.PLAYER_BLUE));
		file = new File("testState.txt");
	}

	@Test
	public void intergrationTestLoadGame() {
		try {
			GameSaver.saveGame(file);
		} catch (IOException e) {
			fail("Could not save file");
		}
		state = null;
		try {
			GameLoader.loadGame(file);
		} catch (IOException e) {
			fail("Could not load file: " + e.getMessage());
		}
		state = GameState.get();
		assertEquals("test", state.getCharacters().get(0).getName());
		assertEquals("test2", state.getCharacters().get(1).getName());
	}
	
	@AfterClass
	public static void tearDown() {
		file.delete();
	}
}



