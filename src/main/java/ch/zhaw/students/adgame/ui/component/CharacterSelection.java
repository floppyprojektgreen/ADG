package ch.zhaw.students.adgame.ui.component;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Optional;

import ch.zhaw.students.adgame.configuration.ResourcesConfiguration;
import ch.zhaw.students.adgame.domain.item.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Class for selecting and entering data of one character.
 */
public class CharacterSelection extends AnchorPane {
	private static final double NAME_WIDTH = 0.75;
	private static final double SELECTION_WIDTH = 0.25;
	
	@FXML
	private TextField name;
	@FXML
	private ComboBox<String> colorSelection;
	
	private ObservableMap<String, Optional<ResourcesConfiguration>> colorSelectionModel;
	
	/**
	 * Creates a selection option for one character.
	 */
	public CharacterSelection() {
		colorSelectionModel = FXCollections.observableMap(new LinkedHashMap<>());
		
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CharacterSelection.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
        	throw new RuntimeException(exception);
        }
    }
	
	@FXML
	private void initialize() {
		name.minWidthProperty().bindBidirectional(name.maxWidthProperty());
		name.minHeightProperty().bindBidirectional(name.maxHeightProperty());
		colorSelection.minWidthProperty().bindBidirectional(colorSelection.maxWidthProperty());
		colorSelection.minHeightProperty().bindBidirectional(colorSelection.maxHeightProperty());
		
		String defaultValue = "Not Selected";
		colorSelectionModel.put(defaultValue, Optional.empty());
		
		colorSelectionModel.put("Blue", Optional.ofNullable(ResourcesConfiguration.PLAYER_BLUE));
		colorSelectionModel.put("Green", Optional.ofNullable(ResourcesConfiguration.PLAYER_GREEN));
		colorSelectionModel.put("Lime", Optional.ofNullable(ResourcesConfiguration.PLAYER_LIME));
		colorSelectionModel.put("Orange", Optional.ofNullable(ResourcesConfiguration.PLAYER_ORANGE));
		colorSelectionModel.put("Pink", Optional.ofNullable(ResourcesConfiguration.PLAYER_PINK));
		colorSelectionModel.put("Purple", Optional.ofNullable(ResourcesConfiguration.PLAYER_PURPLE));
		colorSelectionModel.put("Red", Optional.ofNullable(ResourcesConfiguration.PLAYER_RED));
		colorSelectionModel.put("Turquoise", Optional.ofNullable(ResourcesConfiguration.PLAYER_TURQUOISE));
		
		colorSelection.getItems().addAll(colorSelectionModel.keySet());
		colorSelection.setValue(defaultValue);
	}
	
	/**
	 * Resizes the character selection to the given width and height.
	 */
	public void resizeModel(double width, double height) {
		name.setMinWidth(width * NAME_WIDTH);
		name.setMinHeight(height);
		
		colorSelection.setMinWidth(width * SELECTION_WIDTH);
		colorSelection.setMinHeight(height);
		colorSelection.setTranslateX(width * NAME_WIDTH);
	}
	
	public String getName() {
		return name.getText();
	}
	
	public Optional<ResourcesConfiguration> getSelectedColor() {
		return colorSelectionModel.get(colorSelection.getValue());
	}
}