package ch.zhaw.students.adgame.ui.window;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

import ch.zhaw.students.adgame.configuration.ColorConfiguration;
import ch.zhaw.students.adgame.domain.GameState;
import ch.zhaw.students.adgame.domain.board.FieldType;
import ch.zhaw.students.adgame.domain.entity.Character;
import ch.zhaw.students.adgame.resource.ColorLoader;
import ch.zhaw.students.adgame.resource.TextureLoader;
import ch.zhaw.students.adgame.ui.ResizableUI;
import ch.zhaw.students.adgame.ui.WindowHandler;
import ch.zhaw.students.adgame.ui.component.HexagonMenuItem;
import ch.zhaw.students.adgame.ui.component.HexagonalRadialMenu;
import ch.zhaw.students.adgame.ui.component.ResourceView;
import ch.zhaw.students.adgame.ui.event.AdditionalInfoMouseEvent;
import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;

/**
 * JavaFx controller for FieldSelection.fxml
 */
public class FieldSelectionController implements ResizableUI {
	@FXML
	private Rectangle bg;
	@FXML
	private ResourceView resource;
	@FXML
	private HexagonalRadialMenu<FieldType> menu;
	
	private Queue<HexagonMenuItem<FieldType>> menuElements;
	
	private double charX;
	private double charY;
	
	@FXML
	private void initialize() {
		Character currChar = GameState.get().getCurrentCharacter();
		resource.setText(currChar.getResource() + "");
		
		charX = currChar.getXPosition();
		charY = currChar.getYPosition();
		
		menuElements = new LinkedList<>();
		menuElements.add(menu.getNorth());
		menuElements.add(menu.getNorthEast());
		menuElements.add(menu.getSouthEast());
		menuElements.add(menu.getSouth());
		menuElements.add(menu.getSouthWest());
		menuElements.add(menu.getNorthWest());
		
		bg.setFill(ColorLoader.loadColor(ColorConfiguration.FIELD_SELECTION_BG));
		menuElements.stream().forEach(i -> i.setFill(ColorLoader.loadColor(ColorConfiguration.FIELD_SELECTION_EMPTY)));
		
		GameState.get().getAffordableFieldTypes().forEach(this::addFieldToSelection);
	}
	
	private void addFieldToSelection(FieldType fieldtype) {
		if (!menuElements.isEmpty()) {
			HexagonMenuItem<FieldType> item = menuElements.poll();
			item.setImage(TextureLoader.loadImage(fieldtype.getEvent().getTextureKey()));
			item.setObj(Optional.ofNullable(fieldtype));
			item.setFill(ColorLoader.loadColor(ColorConfiguration.FIELD_SELECTION_FIELD));
		}
	}
	
	@Override
	public void resize(double width, double height) {
		bg.setWidth(width);
		bg.setHeight(height);
		
		double hexagonHeight = height / (GameState.get().getBoard().getHeight() + BoardConstants.FIELD_SPACING_BORDER);
		menu.setHexagonHeight(hexagonHeight);
		
		double shift = BoardController.ABSOLUTE_MENU_BUTTON_SIZE + (width - BoardController.ABSOLUTE_MENU_BUTTON_SIZE - (GameState.get().getBoard().getWidth()*0.75+0.25)*menu.getHexagonWidth()) / 2;
		menu.setCenterX(menu.getHexagonWidth() * (0.5 + 0.75*charX) + shift);
		menu.setCenterY(menu.getHexagonHeight() * (2 + charY - 0.5*(charX%2)));
	}
	
	@FXML
	private void place(AdditionalInfoMouseEvent<Optional<FieldType>> me) {
		Optional<FieldType> fieldType = me.getAdditionalInformation();
		if (fieldType.isPresent()) {
			GameState.get().placeField(fieldType.get());
			WindowHandler.get().closeHighestInMainStack();
		}
	}
}