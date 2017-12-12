package ch.zhaw.students.adgame.test.loader;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.zhaw.students.adgame.loader.GameSaver;
import ch.zhaw.students.adgame.loader.SaveStateData;

public class GameSaverTest {
	private static File file;	
	

	@BeforeClass
	public static void buildUp() {
		file = new File("testState.txt");
	}

	@Test
	public void intergrationTestSaveGame() {
		try {
			GameSaver.saveGame(file);
		} catch (IOException e) {
			fail("Could not Save Game (IO Exception): " + e.getMessage());
		}

		FileInputStream fis = null;
		ObjectInputStream in = null;
		SaveStateData saveState = null;
		
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			fail("Could not open FileInputStream" + e1.getMessage());
		}
		try {
			in = new ObjectInputStream(fis);
		} catch (IOException e1) {
			fail("Could not open ObjectInputStream" + e1.getMessage());
		}
		try {
			try {
				saveState = (SaveStateData) in.readObject();
			} catch (IOException e) {
				fail("Could not read from Stream" + e.getMessage());
			}
		} catch (ClassNotFoundException e) {
			fail("Could not find Class from Save-File" + e.getMessage());
		}
		
		try {
			in.close();
		} catch (IOException e) {
			fail("Could not close Inputstream" + e.getMessage());
		}
		try {
			fis.close();
		} catch (IOException e) {
			fail("Could not close File" + e.getMessage());
		}
		
		assertTrue(file.exists());
		assertTrue(saveState != null);
	}
	
	@AfterClass
	public static void tearDown() {
		file.delete();
	}
}



