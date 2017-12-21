package ch.zhaw.students.adgame.ui.window;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import ch.zhaw.students.adgame.audio.AudioHandler;
import ch.zhaw.students.adgame.audio.AudioTrack;
import ch.zhaw.students.adgame.configuration.ColorConfiguration;
import ch.zhaw.students.adgame.configuration.FileExtenstionConfiguration;
import ch.zhaw.students.adgame.configuration.Texture;
import ch.zhaw.students.adgame.domain.ChangeEvent;
import ch.zhaw.students.adgame.domain.GameState;
import ch.zhaw.students.adgame.domain.board.CardinalDirection;
import ch.zhaw.students.adgame.loader.GameSaver;
import ch.zhaw.students.adgame.logging.LoggingHandler;
import ch.zhaw.students.adgame.resource.ColorLoader;
import ch.zhaw.students.adgame.resource.TextureLoader;
import ch.zhaw.students.adgame.ui.EventListener;
import ch.zhaw.students.adgame.ui.ResizableUI;
import ch.zhaw.students.adgame.ui.UserInterface;
import ch.zhaw.students.adgame.ui.WindowHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

/**
 * JavaFx controller for board.fxml
 */
public class BoardController implements ResizableUI, EventListener {
	public static final double ABSOLUTE_MENU_BUTTON_SIZE = 100;
	
	@FXML
	private Canvas boardCanvas;
	@FXML
	private Rectangle move;
	@FXML
	private ImageView moveImg;
	@FXML
	private Rectangle openShop;
	@FXML
	private ImageView openShopImg;
	@FXML
	private Rectangle endTurn;
	@FXML
	private ImageView endTurnImg;
	@FXML
	private Rectangle save;
	@FXML
	private ImageView saveImg;
	@FXML
	private Rectangle exit;
	@FXML
	private ImageView exitImg;
	
	private BoardPainter painter;
	
	private boolean moving;
	
	@FXML
	private void initialize() {
		moving = true;
		
		createBinding();
		
		move.setFill(ColorLoader.loadColor(ColorConfiguration.BOARD_MENU_MOVE));
		moveImg.setImage(TextureLoader.loadImage(Texture.Hud.MENU_MOVE));
		
		openShop.setFill(ColorLoader.loadColor(ColorConfiguration.BOARD_MENU_SHOP));
		openShopImg.setImage(TextureLoader.loadImage(Texture.Hud.MENU_SHOP));
		
		endTurn.setFill(ColorLoader.loadColor(ColorConfiguration.BOARD_MENU_ENDTURN));
		endTurnImg.setImage(TextureLoader.loadImage(Texture.Hud.MENU_ENDTURN));
		
		save.setFill(ColorLoader.loadColor(ColorConfiguration.BOARD_MENU_SAVE));
		saveImg.setImage(TextureLoader.loadImage(Texture.Hud.MENU_SAVE));
		
		exit.setFill(ColorLoader.loadColor(ColorConfiguration.BOARD_MENU_EXIT));
		exitImg.setImage(TextureLoader.loadImage(Texture.Hud.MENU_EXIT));
		
		painter = new BoardPainter(boardCanvas.getGraphicsContext2D(), GameState.get().getBoard(), boardCanvas.getWidth(), boardCanvas.getHeight());
		painter.start();
	}
	
	private void createBinding() {
		move.widthProperty().bindBidirectional(moveImg.fitWidthProperty());
		move.heightProperty().bindBidirectional(moveImg.fitHeightProperty());
		move.translateXProperty().bindBidirectional(moveImg.translateXProperty());
		move.translateYProperty().bindBidirectional(moveImg.translateYProperty());
		
		openShop.widthProperty().bindBidirectional(openShopImg.fitWidthProperty());
		openShop.heightProperty().bindBidirectional(openShopImg.fitHeightProperty());
		openShop.translateXProperty().bindBidirectional(openShopImg.translateXProperty());
		openShop.translateYProperty().bindBidirectional(openShopImg.translateYProperty());
		
		endTurn.widthProperty().bindBidirectional(endTurnImg.fitWidthProperty());
		endTurn.heightProperty().bindBidirectional(endTurnImg.fitHeightProperty());
		endTurn.translateXProperty().bindBidirectional(endTurnImg.translateXProperty());
		endTurn.translateYProperty().bindBidirectional(endTurnImg.translateYProperty());
		
		save.widthProperty().bindBidirectional(saveImg.fitWidthProperty());
		save.heightProperty().bindBidirectional(saveImg.fitHeightProperty());
		save.translateXProperty().bindBidirectional(saveImg.translateXProperty());
		save.translateYProperty().bindBidirectional(saveImg.translateYProperty());
		
		exit.widthProperty().bindBidirectional(exitImg.fitWidthProperty());
		exit.heightProperty().bindBidirectional(exitImg.fitHeightProperty());
		exit.translateXProperty().bindBidirectional(exitImg.translateXProperty());
		exit.translateYProperty().bindBidirectional(exitImg.translateYProperty());
	}
	
	@Override
	public void resize(double width, double height) {
		boardCanvas.setWidth(width - ABSOLUTE_MENU_BUTTON_SIZE);
		boardCanvas.setHeight(height);
		boardCanvas.setTranslateX(ABSOLUTE_MENU_BUTTON_SIZE);
		painter.resize(width - ABSOLUTE_MENU_BUTTON_SIZE, height);
		
		move.setWidth(ABSOLUTE_MENU_BUTTON_SIZE);
		move.setHeight(ABSOLUTE_MENU_BUTTON_SIZE);
		
		openShop.setWidth(ABSOLUTE_MENU_BUTTON_SIZE);
		openShop.setHeight(ABSOLUTE_MENU_BUTTON_SIZE);
		openShop.setTranslateY(ABSOLUTE_MENU_BUTTON_SIZE);
		
		endTurn.setWidth(ABSOLUTE_MENU_BUTTON_SIZE);
		endTurn.setHeight(ABSOLUTE_MENU_BUTTON_SIZE);
		endTurn.setTranslateY(ABSOLUTE_MENU_BUTTON_SIZE*2);
		
		save.setWidth(ABSOLUTE_MENU_BUTTON_SIZE);
		save.setHeight(ABSOLUTE_MENU_BUTTON_SIZE);
		save.setTranslateY(height - ABSOLUTE_MENU_BUTTON_SIZE*2);
		
		exit.setWidth(ABSOLUTE_MENU_BUTTON_SIZE);
		exit.setHeight(ABSOLUTE_MENU_BUTTON_SIZE);
		exit.setTranslateY(height - ABSOLUTE_MENU_BUTTON_SIZE);
	}
	
	@FXML
	private void move() {
		if (moving) {
			CardinalDirection dir = GameState.get().determineCharacterDirection();
			AudioHandler.get().play(AudioTrack.DICE_ROLL);
			painter.rotateCompass(dir, () -> {
				GameState.get().moveCharacter(dir);
				changeDisabledState();
			});
		}
	}
	
	@FXML
	private void openShop() {
		if (!moving) {
			WindowHandler.get().openUI(UserInterface.SHOP);
		}
	}
	
	@FXML
	private void endTurn() {
		if (!moving) {
			GameState.get().endTurn();
			changeDisabledState();
		}
	}
	
	@FXML
	private void save() {
		File selectedFile = WindowHandler.get().showSaveFileDialog("save game", FileExtenstionConfiguration.ADGAME_SAVE);
		
		if (selectedFile != null) {
			try {
				GameSaver.saveGame(selectedFile);
			} catch (IOException e) {
				//TODO: inform user that save did not work
				LoggingHandler.log(e, Level.WARNING);
			}
		}
	}
	
	@FXML
	private void exit() {
		WindowHandler.get().openUI(UserInterface.MAIN_MENU);
	}
	
	private void changeDisabledState() {
		moving = !moving;
	}

	@Override
	public void onChangeEvent(ChangeEvent event) {
		if (event == ChangeEvent.CHARACTER_DIED) {
			moving = true;
		}
	}
}
