package ch.zhaw.students.adgame.ui.component;

import java.io.IOException;
import java.util.logging.Level;

import ch.zhaw.students.adgame.domain.Fightable;
import ch.zhaw.students.adgame.logging.LoggingHandler;
import ch.zhaw.students.adgame.resource.TextureLoader;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Overview pane for a fightable in a battle situation.
 */
public class BattleFightableOverview extends AnchorPane {
	private static final double BORDER_OFFSET_LR = 0.25;
	private static final double BORDER_OFFSET_TB = 0.1;
	private static final double SPACING_VERTICAL = 0.05;
	private static final double HEALTH_WIDTH = 0.5;
	private static final double HEALTH_HEIGHT = 0.15;
	private static final double IMAGE_SIZE = 0.6;
	private static final double IMAGE_OFFSET = (1 - BORDER_OFFSET_TB - HEALTH_HEIGHT - IMAGE_SIZE) * 0.5;
	
	private static final double FUCKING_LINE_SPACING_FIX = 1.35; //value guessed not accurate, it just works that way
	
	@FXML
	private ImageView fightableImg;
	@FXML
	private NoPaddingLabel hitLog;
	@FXML
	private Rectangle healthBar;
	@FXML
	private Rectangle currentHealth;
	
	private ObjectProperty<Fightable> fightable;
	
	private int maxLines = 0;
	
	/**
	 * Creates a new fightable overview for a battle situation.
	 */
	public BattleFightableOverview() {
		fightable = new SimpleObjectProperty<>();
		
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BattleFightableOverview.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
        	LoggingHandler.log(e, Level.SEVERE);
        }
    }
	
	@FXML
	private void initialize() {
		minHeightProperty().bindBidirectional(maxHeightProperty());
		minWidthProperty().bindBidirectional(maxWidthProperty());
		
		healthBar.heightProperty().bindBidirectional(currentHealth.heightProperty());
		healthBar.translateXProperty().bindBidirectional(currentHealth.translateXProperty());
		healthBar.translateYProperty().bindBidirectional(currentHealth.translateYProperty());
		
		minHeightProperty().addListener((o, oldVal, newVal) -> changeSizeListener(newVal.doubleValue(), getMinWidth()));
		minWidthProperty().addListener((o, oldVal, newVal) -> changeSizeListener(getMinHeight(), newVal.doubleValue()));
		
		fightable.addListener((o, oldVal, newVal) -> fightableImg.setImage(TextureLoader.loadImage(newVal.getTextureKey())));
		
		hitLog.maxHeightProperty().addListener((o, oldVal, newVal) -> maxLines = (int)Math.floor(newVal.doubleValue() / (hitLog.getFontSize() * FUCKING_LINE_SPACING_FIX)));
		hitLog.fontSizeProperty().addListener((o, oldVal, newVal) -> maxLines = (int)Math.floor(hitLog.getMaxHeight() / (newVal.doubleValue() * FUCKING_LINE_SPACING_FIX)));
	}
	
	private void changeSizeListener(double height, double width) {
		double imgSize = height * IMAGE_SIZE;
		double imgOffsetX = (width - imgSize)/2;
		double imgOffsetY = height * IMAGE_OFFSET;
		
		healthBar.setHeight(height * HEALTH_HEIGHT);
		healthBar.setWidth(width * HEALTH_WIDTH);
		healthBar.setTranslateX(width * BORDER_OFFSET_LR);
		healthBar.setTranslateY(height - healthBar.getHeight() - height*BORDER_OFFSET_TB);
		
		fightableImg.setFitHeight(imgSize);
		fightableImg.setFitWidth(imgSize);
		fightableImg.setTranslateX(imgOffsetX);
		fightableImg.setTranslateY(imgOffsetY);
		
		hitLog.setMaxHeight(imgSize);
		hitLog.setTranslateX(imgOffsetX + imgSize + width*SPACING_VERTICAL);
		hitLog.setTranslateY(imgOffsetY);
		
		updateHealthBar();
	}
	
	/**
	 * Updates the status of the health bar of the fightable set in this overview.
	 */
	public void updateHealthBar() {
		if (fightable.get() != null) {
			double fullWidth = healthBar.getWidth();
			double fullHealth = fightable.get().getMaxHitPoints();
			double health = fightable.get().getHitPoints();
			
			currentHealth.setWidth(fullWidth * (health/fullHealth));
		}
	}
	
	/**
	 * Adds a given line to the hit log.<br>
	 * Checks if the there is enough space for an additional line,
	 * otherwise it removes the last one.<br>
	 * <b>Important</b>: adds '&gt;' before each line.
	 */
	public void addNewLineToHitLog(String line) {
		String log = "> " + line;
		
		if (!hitLog.getText().isEmpty()) {
			String old = hitLog.getText();
			int lines = old.split("\n").length;
			if (lines == maxLines) {
				old = old.replaceFirst(".*\n", "");
			}
			log = old + "\n" + log;
		}
		
		hitLog.setText(log);
	}

	public ObjectProperty<Fightable> fightableProperty() {
		return fightable;
	}
	
	public Fightable getFightable() {
		return fightableProperty().get();
	}
	
	public void setFightable(Fightable fightable) {
		fightableProperty().set(fightable);
	}
	
	public ObjectProperty<Paint> strokeProperty() {
		return healthBar.strokeProperty();
	}
	
	public Paint getStroke() {
		return strokeProperty().get();
	}
	
	public void setStroke(Paint stroke) {
		strokeProperty().set(stroke);
	}
	
	public ObjectProperty<Paint> healthBarFillProperty() {
		return healthBar.fillProperty();
	}
	
	public Paint getHealthBarFill() {
		return healthBarFillProperty().get();
	}
	
	public void setHealthBarFill(Paint stroke) {
		healthBarFillProperty().set(stroke);
	}
	
	public ObjectProperty<Paint> currentHealthFillProperty() {
		return currentHealth.fillProperty();
	}
	
	public Paint getCurrentHealthFill() {
		return currentHealthFillProperty().get();
	}
	
	public void setCurrentHealthFill(Paint stroke) {
		currentHealthFillProperty().set(stroke);
	}
	
	public ObjectProperty<Paint> hitLogTextFillProperty() {
		return hitLog.textFillProperty();
	}
	
	public Paint getHitLogTextFill() {
		return hitLogTextFillProperty().get();
	}
	
	public void setHitLogTextFill(Paint stroke) {
		hitLogTextFillProperty().set(stroke);
	}
}