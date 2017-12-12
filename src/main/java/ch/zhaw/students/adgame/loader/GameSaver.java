package ch.zhaw.students.adgame.loader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import ch.zhaw.students.adgame.domain.GameState;

/**
 * This class is used to create a save state of the current standing of the
 * game. 
 */
public class GameSaver {

	/**
	 * Saves a save state into a file.
	 */
	public static void saveGame(File file) throws IOException {
		GameState gameState = GameState.get();
		SaveStateData saveState = new SaveStateData(gameState.getBoard(), gameState.getCharacters(),
				gameState.getActivePlayer(), gameState.getAvailableFieldTypes(), gameState.getRounds());

		PrintWriter writer = new PrintWriter(file);
		writer.print("");
		writer.close();

		try (FileOutputStream fos = new FileOutputStream(file); ObjectOutputStream out = new ObjectOutputStream(fos)) {
			out.writeObject(saveState);
		} catch (IOException e) {
			throw e;
		}
	}

}
