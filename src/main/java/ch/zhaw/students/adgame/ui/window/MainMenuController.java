package ch.zhaw.students.adgame.ui.window;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import ch.zhaw.students.adgame.configuration.ColorConfiguration;
import ch.zhaw.students.adgame.configuration.FileExtenstionConfiguration;
import ch.zhaw.students.adgame.configuration.Texture;
import ch.zhaw.students.adgame.loader.GameLoader;
import ch.zhaw.students.adgame.logging.LoggingHandler;
import ch.zhaw.students.adgame.resource.ColorLoader;
import ch.zhaw.students.adgame.resource.TextureLoader;
import ch.zhaw.students.adgame.ui.ResizableUI;
import ch.zhaw.students.adgame.ui.UserInterface;
import ch.zhaw.students.adgame.ui.WindowHandler;
import ch.zhaw.students.adgame.ui.component.LabelButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * JavaFx controller for MainMenu.fxml
 */
public class MainMenuController implements ResizableUI {
	private static final double LOGO_HEIGHT = 0.25;
	private static final double MENU_OFFSET_Y = 0.1;
	
	private static final double BUTTON_WIDTH = 0.25;
	private static final double ABSOLUT_BUTTON_HEIGHT = 100;
	private static final double BUTTON_OFFSET_SIZE = 1.1;
	
	@FXML
	private VBox menubox;
	@FXML
	private ImageView logo;
	@FXML
	private LabelButton play;
	@FXML
	private LabelButton load;
	@FXML
	private LabelButton exit;
	
	@FXML
	private void initialize() {
		logo.setImage(TextureLoader.loadImage(Texture.General.LOGO));
		
		play.setFill(ColorLoader.loadColor(ColorConfiguration.MAIN_MENU_PLAY));
		load.setFill(ColorLoader.loadColor(ColorConfiguration.MAIN_MENU_LOAD));
		exit.setFill(ColorLoader.loadColor(ColorConfiguration.MAIN_MENU_EXIT));
	}
	
	@Override
	public void resize(double width, double height) {
		logo.setFitHeight(height*LOGO_HEIGHT);
		menubox.setTranslateY(height*MENU_OFFSET_Y);
		
		double buttonWidth = width*BUTTON_WIDTH;
		double buttonHeight = ABSOLUT_BUTTON_HEIGHT;
		
		play.setWidth(buttonWidth);
		play.setHeight(buttonHeight);
		
		load.setWidth(buttonWidth);
		load.setHeight(buttonHeight);
		load.setTranslateY(buttonHeight*BUTTON_OFFSET_SIZE);
		
		exit.setWidth(buttonWidth);
		exit.setHeight(buttonHeight);
		exit.setTranslateY(buttonHeight*BUTTON_OFFSET_SIZE*2);
	}
	
	@FXML
	private void play(MouseEvent me) {
		WindowHandler.get().openUI(UserInterface.CHARACTER_SELECTION_MENU);
	}
	
	@FXML
	private void load(MouseEvent me) {
		File selectedFile = WindowHandler.get().showOpenFileDialog("Load Game", FileExtenstionConfiguration.ADGAME_SAVE);

		if (selectedFile != null) {
			try {
				GameLoader.loadGame(selectedFile);
				WindowHandler.get().openUI(UserInterface.BOARD);
			} catch (IOException e) {
				//TODO: inform that load did not work.
				LoggingHandler.log(e, Level.WARNING);
			}
		}
	}
	
	@FXML
	private void exit(MouseEvent me) {
		Platform.exit();
	}
}
