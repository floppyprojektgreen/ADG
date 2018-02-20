package ch.zhaw.students.adgame.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import ch.zhaw.students.adgame.audio.AudioHandler;
import ch.zhaw.students.adgame.audio.AudioTrack;
import ch.zhaw.students.adgame.configuration.FileExtenstionConfiguration;
import ch.zhaw.students.adgame.configuration.MainConfiguration;
import ch.zhaw.students.adgame.configuration.SystemConfiguration;
import ch.zhaw.students.adgame.domain.ChangeEvent;
import ch.zhaw.students.adgame.domain.GameState;
import ch.zhaw.students.adgame.logging.LoggingHandler;
import ch.zhaw.students.adgame.resource.ResourceCacheOrganizer;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Handler for the whole userinterface.
 * Loads, displays and closes the desired userinterfaces.<br>
 * Singleton for always having the same primarystage.
 */
public class WindowHandler {
	private static WindowHandler instance;
	
	private Stage primaryStage;
	private StackPane stack;
	private AudioTrack currentTrack;
	private Deque<AudioTrack> trackStack;
	private Map<Node, ResizableUI> currentStackController;
	private Map<Node, EventListener> currentStackEventListeners;
	
	private WindowHandler() {}
	
	/**
	 * Getter for the singleton instance.
	 */
	public static WindowHandler get() {
		if (instance == null) {
			instance = new WindowHandler();
		}
		return instance;
	}
	
	/**
	 * Initializes the window handler on the given primarystage.<br>
	 * Can only be called once, will just return false afterwards.
	 */
	public boolean initialize(Stage primaryStage) {
		if (this.primaryStage != null) {
			return false;
		}
		
		this.primaryStage = primaryStage;
		stack = new StackPane();
		currentTrack = AudioTrack.SILENCE;
		trackStack = new ArrayDeque<>();
		currentStackController = new HashMap<>();
		currentStackEventListeners = new HashMap<>();
		
		primaryStage.setScene(new Scene(stack));
//		primaryStage.setFullScreen(true);
		primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		
		primaryStage.getScene().addEventFilter(KeyEvent.KEY_PRESSED, this::keyEventHandler);
		
		primaryStage.widthProperty().addListener(this::resizeListener);
		primaryStage.heightProperty().addListener(this::resizeListener);
		
		GameState.get().registerListener(this::gameChangeListener);
		
		return true;
	}
	
	private void keyEventHandler(KeyEvent keyEvent) {
		switch (keyEvent.getCode()) {
		case F12:
			MainConfiguration.reloadConfigurationFiles();
			ResourceCacheOrganizer.clearCache();
			keyEvent.consume();
			break;
		default:
			break;
		}
	}
	
	private void gameChangeListener(ChangeEvent event) {
		switch (event) {
		case LANDED_ON_EMPTY_FIELD:
			openUI(UserInterface.FIELD_SELECTION);
			break;
		case FIGHTABLE_BATTLE_STARTED:
			openUI(UserInterface.FIGHTABLE_BATTLE);
			break;
		case CHARACTER_BATTLE_STARTED:
			openUI(UserInterface.CHARACTER_BATTLE);
			break;
		case CHARACTER_DIED:
			AudioHandler.get().play(AudioTrack.WILHELM_SCREAM);
			break;
		default:
			break;
		}
		currentStackEventListeners.values().forEach(el -> el.onChangeEvent(event));
	}
	
	private void resizeListener(ObservableValue<? extends Number> o, Number oldVal, Number newVal) {
		currentStackController.values().forEach(c -> c.resize(primaryStage.getWidth(), primaryStage.getHeight()));
	}
	
	/**
	 * Loads the given userinterface and opens it according to the type of the ui,
	 * in a new stack or adding to the existing.
	 */
	public void openUI(UserInterface ui) {
		FXMLLoader loader = new FXMLLoader(ui.getLocation());
		try {
			Parent pane = loader.load();
			
			switch (ui.getType()) {
			case 'n':
				stack.getChildren().clear();
				currentStackController.clear();
				currentStackEventListeners.clear();
				trackStack.clear();
				stack.getChildren().add(pane);
				break;
			case 'a':
				stack.getChildren().add(pane);
				break;
			default:
				break;
			}
			
			trackStack.addFirst(currentTrack);
			currentTrack = ui.getBackgoundMusic();
			AudioHandler.get().play(currentTrack);
			
			//call resize to always have right size
			Object controller = loader.getController();
			if (controller instanceof ResizableUI) {
				ResizableUI c = (ResizableUI)controller;
				c.resize(primaryStage.getWidth(), primaryStage.getHeight());
				currentStackController.put(pane, c);
			}
			if (controller instanceof EventListener) {
				EventListener c = (EventListener)controller;
				currentStackEventListeners.put(pane, c);
			}
		} catch (IOException e) {
			LoggingHandler.log(e, Level.SEVERE);
		}
	}
	
	/**
	 * Closes the highest pane in current stack or
	 * exits the application if the highest pane is
	 * the last one.
	 */
	public void closeHighestInMainStack() {
		List<Node> panes = stack.getChildren();
		if (panes.size() > 1) {
			Node removed = panes.remove(panes.size() - 1);
			currentStackController.remove(removed);
			currentStackEventListeners.remove(removed);
			
			currentTrack = trackStack.removeFirst();
			AudioHandler.get().play(currentTrack);
		} else {
			Platform.exit();
		}
	}
	
	private FileChooser createFileChooser(String title, FileExtenstionConfiguration... filters) {
		FileChooser chooser = new FileChooser();
		chooser.setTitle(title);
		for(FileExtenstionConfiguration filter : filters) {
			chooser.getExtensionFilters().add(filter.getFilter());
		}
		return chooser;
	}
	
	/**
	 * Opens a new file chooser with {@link FileChooser#showOpenDialog(javafx.stage.Window)},
	 * the given title and the given extension configurations.
	 */
	public File showOpenFileDialog(String title, FileExtenstionConfiguration... filters) {
		return SystemConfiguration.isSet(SystemConfiguration.SINGLE_SAVE)
				? new File(SystemConfiguration.SAVE_FILE)
				: createFileChooser(title, filters).showOpenDialog(primaryStage);
	}
	
	/**
	 * Opens a new file chooser with {@link FileChooser#showSaveDialog(javafx.stage.Window)},
	 * the given title and the given extension configurations.
	 */
	public File showSaveFileDialog(String title, FileExtenstionConfiguration... filters) {
		return SystemConfiguration.isSet(SystemConfiguration.SINGLE_SAVE)
				? new File(SystemConfiguration.SAVE_FILE)
				: createFileChooser(title, filters).showSaveDialog(primaryStage);
	}
}
