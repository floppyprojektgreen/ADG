package ch.zhaw.students.adgame.ui.window;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import ch.zhaw.students.adgame.configuration.CharacterConfiguration;
import ch.zhaw.students.adgame.configuration.ColorConfiguration;
import ch.zhaw.students.adgame.configuration.ResourcesConfiguration;
import ch.zhaw.students.adgame.domain.GameState;
import ch.zhaw.students.adgame.domain.entity.Character;
import ch.zhaw.students.adgame.resource.CharacterLoader;
import ch.zhaw.students.adgame.resource.ColorLoader;
import ch.zhaw.students.adgame.ui.ResizableUI;
import ch.zhaw.students.adgame.ui.UserInterface;
import ch.zhaw.students.adgame.ui.WindowHandler;
import ch.zhaw.students.adgame.ui.component.CharacterSelection;
import ch.zhaw.students.adgame.ui.component.LabelButton;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/**
 * JavaFx controller for MainMenu.fxml
 */
public class CharacterSelectionMenuController implements ResizableUI {
	private static final double BORDER_SIZE = 0.05;
	private static final double BUTTON_WIDTH = 0.25;
	private static final double BUTTON_HEIGHT = 0.1;
	private static final double SELECTION_HEIGHT = 0.6;
	private static final double SELECTION_SPACING = 0.01;
	
	@FXML
	private VBox characterSelectionsBox;
	@FXML
	private LabelButton play;
	@FXML
	private LabelButton back;
	
	@FXML
	private void initialize() {
		characterSelectionsBox.minWidthProperty().bindBidirectional(characterSelectionsBox.maxWidthProperty());
		characterSelectionsBox.minHeightProperty().bindBidirectional(characterSelectionsBox.maxHeightProperty());
		
		play.setFill(ColorLoader.loadColor(ColorConfiguration.CHARACTER_SELECTION_MENU_PLAY));
		back.setFill(ColorLoader.loadColor(ColorConfiguration.CHARACTER_SELECTION_MENU_BACK));
	}
	
	@Override
	public void resize(double width, double height) {
		double borderX = width*BORDER_SIZE;
		double borderY = height*BORDER_SIZE;
		
		double buttonWidth = width * BUTTON_WIDTH;
		double buttonHeight = height * BUTTON_HEIGHT;
		
		double selectionHeight = height * SELECTION_HEIGHT;
		double selectionSpacing = height * SELECTION_SPACING;
		int numOfSelections = characterSelectionsBox.getChildren().size();
		double selectionItemHeight = (selectionHeight - (numOfSelections - 1)*selectionSpacing) / numOfSelections;
		
		back.setWidth(buttonWidth);
		back.setHeight(buttonHeight);
		back.setTranslateX(borderX);
		back.setTranslateY(borderY);
		
		characterSelectionsBox.setMinWidth(width - 2*borderX);
		characterSelectionsBox.setMinHeight(selectionHeight);
		characterSelectionsBox.setTranslateX(borderX);
		characterSelectionsBox.setTranslateY((height - selectionHeight) / 2);
		characterSelectionsBox.setSpacing(selectionSpacing);
		
		getCharacterSelections()
			.forEach(cs -> cs.resizeModel(characterSelectionsBox.getMinWidth(), selectionItemHeight));

		play.setWidth(buttonWidth);
		play.setHeight(buttonHeight);
		play.setTranslateX(width - buttonWidth - borderX);
		play.setTranslateY(height - buttonHeight - borderY);
	}
	
	private Stream<CharacterSelection> getCharacterSelections() {
		return characterSelectionsBox.getChildren().stream().map(CharacterSelection.class::cast);
	}
	
	private long countSelectedCharacters() {
		return getCharacterSelections().filter(cs -> cs.getSelectedColor().isPresent()).count();
	}
	
	private Character createCharacter(String name, ResourcesConfiguration color, int charIndex) {
		return new Character(name,
				CharacterLoader.loadProperty(CharacterConfiguration.HEALTH),
				CharacterLoader.loadProperty(CharacterConfiguration.BASE_STRENGTH),
				CharacterLoader.loadProperty(CharacterConfiguration.BASE_DEFENSE),
				CharacterLoader.loadProperty(CharacterConfiguration.BASE_ACCURACY),
				name.equalsIgnoreCase("uuddlrlrba") ? Integer.MAX_VALUE/2 : CharacterLoader.loadProperty(CharacterConfiguration.RESOURCE),
				CharacterLoader.loadProperty(CharacterConfiguration.X_POSITIONS, charIndex),
				CharacterLoader.loadProperty(CharacterConfiguration.Y_POSITIONS, charIndex),
				color);
	}
	
	@FXML
	private void play(MouseEvent me) {
		if (countSelectedCharacters() >= CharacterLoader.loadProperty(CharacterConfiguration.CHARACTER_MIN)) {
			AtomicInteger charIndex = new AtomicInteger(0);
			
			GameState.get().init(10, 7,
					getCharacterSelections()
						.filter(cs -> cs.getSelectedColor().isPresent())
						.map(cs -> createCharacter(cs.getName(), cs.getSelectedColor().get(), charIndex.getAndIncrement()))
						.toArray(i -> new Character[i]));
			WindowHandler.get().openUI(UserInterface.BOARD);
		}
	}
	
	@FXML
	private void back(MouseEvent me) {
		WindowHandler.get().openUI(UserInterface.MAIN_MENU);
	}
}
