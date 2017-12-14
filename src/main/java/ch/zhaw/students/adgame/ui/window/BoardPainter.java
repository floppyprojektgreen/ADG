package ch.zhaw.students.adgame.ui.window;

import java.util.concurrent.atomic.AtomicInteger;

import ch.zhaw.students.adgame.configuration.ColorConfiguration;
import ch.zhaw.students.adgame.configuration.Texture;
import ch.zhaw.students.adgame.domain.GameState;
import ch.zhaw.students.adgame.domain.board.Board;
import ch.zhaw.students.adgame.domain.board.CardinalDirection;
import ch.zhaw.students.adgame.domain.board.Field;
import ch.zhaw.students.adgame.domain.entity.Character;
import ch.zhaw.students.adgame.resource.ColorLoader;
import ch.zhaw.students.adgame.resource.TextureLoader;
import ch.zhaw.students.adgame.ui.component.CompassNeedle;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Rotate;

/**
 * Painter for the board.<br>
 * Paints the board periodically after being started based on the current state of the board.
 * @see AnimationTimer
 */
public class BoardPainter extends AnimationTimer {
	private Board board;
	
	private CompassNeedle compassNeedle;
	
	private GraphicsContext gc;
	private double width;
	private double height;
	
	private double sizeX;
	private double sizeY;
	
	private double n;
	private double ht;
	private double shiftX;
	
	private double fieldImgSize;
	
	/**
	 * Creates a BoardPainter.
	 * @see BoardPainter
	 */
	public BoardPainter(GraphicsContext gc, Board board, double width, double height) {
		this.board = board;
		
		compassNeedle = new CompassNeedle(1000, 2);
		
		this.gc = gc;
		this.sizeX = board.getWidth();
		this.sizeY = board.getHeight();
		
		resize(width, height);
	}
	
	/**
	 * Method to inform the {@link BoardPainter} of a resize. Calculates the general size variables.
	 */
	public void resize(double width, double height) {
		this.width = width;
		this.height = height;
		
		ht = height / (sizeY + BoardConstants.FIELD_SPACING_BORDER);
		n = ht / (2 * Math.sin(Math.PI/3));
		shiftX = (width - (sizeX * 1.5 + 0.5) * n) / 2;
		
		fieldImgSize = ht * (Math.sqrt(3) - 1);
	}
	
	private void paintImageAngled(Image image, double angle, double xCenter, double yCenter, double width, double height) {
		gc.save();
		
		Rotate r = new Rotate(angle, xCenter, yCenter);
		gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
		
		gc.drawImage(image,
				xCenter - width/2,
				yCenter - height/2,
				width,
				height);
		
		gc.restore();
	}
	
	/**
	 * Lets the compass spin and stops it in the given direction, before calling a finish action.
	 */
	public void rotateCompass(CardinalDirection stopDirection, Runnable finish) {
		compassNeedle.startAndStopAt(stopDirection, finish);
	}
	
	private void drawBoard() {
		Character currChar = GameState.get().getCurrentCharacter();
		int currCharX = currChar.getXPosition();
		int currCharY = currChar.getYPosition();
		
		gc.setStroke(ColorLoader.loadColor(ColorConfiguration.BOARD_FIELD_OUTLINE));
		gc.setFill(ColorLoader.loadColor(ColorConfiguration.BOARD_FIELD_FILL));
		
		for (int x = 0; x < sizeX; x++) {
			for (int y = 0; y < sizeY; y++) {
				double xt = shiftX + 1.5 * n * x;
				double yt = ht * (1.5 + y - 0.5 * (x % 2));
				
				double x1 = xt + n/2;
				double x2 = xt + 3*n/2;
				double x3 = xt + 2*n;
				
				double y3 = yt + ht/2;
				double y4 = yt + ht;
				
				gc.strokePolygon(
						new double[]{x1, x2, x3, x2, x1, xt},
						new double[]{yt, yt, y3, y4, y4, y3},
						6);
				if (x == currCharX && y == currCharY) {
					gc.setFill(ColorLoader.loadColor(ColorConfiguration.BOARD_FIELD_CURR_CHAR));
				}
				gc.fillPolygon(
						new double[]{x1, x2, x3, x2, x1, xt},
						new double[]{yt, yt, y3, y4, y4, y3},
						6);
				gc.setFill(ColorLoader.loadColor(ColorConfiguration.BOARD_FIELD_FILL));
				
				Field currField = board.getField(x, y);
				if (!currField.isEmpty()) {
					gc.drawImage(TextureLoader.loadImage(currField.getFieldType().getEvent().getTextureKey()),
							xt + n - fieldImgSize/2,
							yt + ht/2 - fieldImgSize/2,
							fieldImgSize,
							fieldImgSize);
				}
				
				AtomicInteger i = new AtomicInteger(0);
				currField.getCharactersOnField().forEach(
						c -> gc.drawImage(
								TextureLoader.loadImage(c.getTextureKey()),
								xt + n - i.getAndIncrement()*fieldImgSize/2,
								yt + ht/2,
								fieldImgSize/2,
								fieldImgSize/2));
			}
		}
	}
	
	private void drawCompass() {
		double size = shiftX * 0.9;
		double space = shiftX * 0.05; //simplified: (shiftX - size) / 2
		double needleCenter = shiftX * 0.5; //simplified: (size/2) + space
		
		gc.drawImage(TextureLoader.loadImage(Texture.General.BOARD_COMPASS), space, space, size, size);
		paintImageAngled(TextureLoader.loadImage(Texture.General.BOARD_COMPASS_NEEDLE), compassNeedle.getAngle(), needleCenter, needleCenter, size, size);
	}
	
	@Override
	public void handle(long now) {
		gc.setFill(ColorLoader.loadColor(ColorConfiguration.BOARD_BG));
		gc.fillRect(0, 0, width, height);
		
		drawBoard();
		drawCompass();
	}
}
