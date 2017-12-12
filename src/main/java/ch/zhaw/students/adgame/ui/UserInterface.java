package ch.zhaw.students.adgame.ui;

import java.net.URL;

import ch.zhaw.students.adgame.audio.AudioTrack;

/**
 * Enumeration for all usable userinterfaces.
 */
public enum UserInterface {
	MAIN_MENU("window/MainMenu.fxml", 'n', AudioTrack.MAIN_THEME),
	CHARACTER_SELECTION_MENU("window/CharacterSelectionMenu.fxml", 'n', AudioTrack.MAIN_THEME),
	BOARD("window/Board.fxml", 'n', AudioTrack.MAIN_THEME),
	FIELD_SELECTION("window/FieldSelection.fxml", 'a', AudioTrack.MAIN_THEME),
	SHOP("window/Shop.fxml", 'a', AudioTrack.MAIN_THEME),
	FIGHTABLE_BATTLE("window/FightableBattle.fxml", 'a', AudioTrack.FIGHT_THEME),
	CHARACTER_BATTLE("window/CharacterBattle.fxml", 'a', AudioTrack.FIGHT_THEME);
	
	private static final String PACKAGE = "/ch/zhaw/students/adgame/ui/";
	
	private URL location;
	private char type;
	private AudioTrack backgoundMusic;
	
	private UserInterface(String location, char type, AudioTrack backgroundMusic) {
		this.location = getClass().getResource(PACKAGE + location);
		this.type = type;
		this.backgoundMusic = backgroundMusic;
	}
	
	public URL getLocation() {
		return location;
	}
	
	/**
	 * n = new stack<br>
	 * a = add top of current stack
	 */
	public char getType() {
		return type;
	}
	
	public AudioTrack getBackgoundMusic() {
		return backgoundMusic;
	}
}
