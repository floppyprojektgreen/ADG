package ch.zhaw.students.adgame.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import ch.zhaw.students.adgame.domain.GameState;

/**
 * This class is used to load a save state from a file in the file system.
 */
public class GameLoader {

	/**
	 * Loads a save state from a file.
	 */
	public static void loadGame(File file) throws IOException {
		GameState gameState = GameState.get();
		SaveStateData saveState = null;

		try (FileInputStream fis = new FileInputStream(file); ObjectInputStream in = new ObjectInputStream(fis)) {

			saveState = (SaveStateData) in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		if (saveState != null) {
			gameState.setBoard(saveState.getBoard());
			gameState.setCharacters(saveState.getCharacters());
			gameState.setActivePlayerIndex(saveState.getActivePLayer());
			gameState.setAvailableFieldTypes(saveState.getAvailableFieldTypes());
			gameState.setRounds(saveState.getRounds());
		}
	}

}
