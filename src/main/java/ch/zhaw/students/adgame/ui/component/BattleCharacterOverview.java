package ch.zhaw.students.adgame.ui.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

import ch.zhaw.students.adgame.domain.entity.Character;
import ch.zhaw.students.adgame.domain.item.Weapon;
import ch.zhaw.students.adgame.logging.LoggingHandler;
import ch.zhaw.students.adgame.resource.TextureLoader;
import ch.zhaw.students.adgame.ui.event.AdditionalInfoMouseEvent;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Overview pane for a character in a battle situation.
 */
public class BattleCharacterOverview extends AnchorPane {
	private static final double BORDER_OFFSET_LR = 0.05;
	private static final double BORDER_OFFSET_TB = 0.1;
	private static final double SPACING_VERTICAL = 0.05;
	private static final double HEALTH_WIDTH = 0.9;
	private static final double HEALTH_HEIGHT = 0.1;
	private static final double IMAGE_SIZE = 0.5;
	private static final double ATTACK_BUTTON_HEIGHT = 0.2;
	private static final double IMAGE_OFFSET = (1 - BORDER_OFFSET_TB - HEALTH_HEIGHT - IMAGE_SIZE) * 0.5;
	private static final double WEAPON_SELECTION_SIZE = 0.25;
	private static final double WEAPON_SELECTION_SPACING = 0.25;
	
	private static final double LINE_SPACING_FIX = 1.35; //value guessed not accurate, it just works that way
	
	@FXML
	private ImageView characterImg;
	@FXML
	private Rectangle characterRect;
	@FXML
	private WeaponDisplay currentWeapon;
	@FXML
	private Rectangle attackButton;
	@FXML
	private NoPaddingLabel hitLog;
	@FXML
	private Rectangle healthBar;
	@FXML
	private Rectangle currentHealth;
	
	private List<WeaponDisplay> selectableWeapons;
	
	private ObjectProperty<Character> character;
	
	private ObjectProperty<Paint> stroke;
	
	private ObjectProperty<Paint> weaponSelectionBg;
	
	private int maxLines = 0;
	
	/**
	 * Creates a new character overview for a battle situation.
	 */
	public BattleCharacterOverview() {
		character = new SimpleObjectProperty<>();
		stroke = new SimpleObjectProperty<>();
		weaponSelectionBg = new SimpleObjectProperty<>();
		selectableWeapons = new ArrayList<>();
		
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BattleCharacterOverview.fxml"));
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
		
		characterImg.fitHeightProperty().bindBidirectional(characterRect.heightProperty());
		characterImg.fitWidthProperty().bindBidirectional(characterRect.widthProperty());
		characterImg.translateXProperty().bindBidirectional(characterRect.translateXProperty());
		characterImg.translateYProperty().bindBidirectional(characterRect.translateYProperty());
		
		healthBar.heightProperty().bindBidirectional(currentHealth.heightProperty());
		healthBar.translateXProperty().bindBidirectional(currentHealth.translateXProperty());
		healthBar.translateYProperty().bindBidirectional(currentHealth.translateYProperty());
		
		stroke.bindBidirectional(characterRect.strokeProperty());
		stroke.bindBidirectional(currentWeapon.strokeProperty());
		stroke.bindBidirectional(healthBar.strokeProperty());
		
		minHeightProperty().addListener((o, oldVal, newVal) -> changeSizeListener(newVal.doubleValue(), getMinWidth()));
		minWidthProperty().addListener((o, oldVal, newVal) -> changeSizeListener(getMinHeight(), newVal.doubleValue()));
		
		character.addListener((o, oldVal, newVal) -> updateCharacterInfo(newVal));
		
		hitLog.maxHeightProperty().addListener((o, oldVal, newVal) -> maxLines = (int)Math.floor(newVal.doubleValue() / (hitLog.getFontSize() * LINE_SPACING_FIX)));
		hitLog.fontSizeProperty().addListener((o, oldVal, newVal) -> maxLines = (int)Math.floor(hitLog.getMaxHeight() / (newVal.doubleValue() * LINE_SPACING_FIX)));
	}
	
	private void changeSizeListener(double height, double width) {
		double borderWidth = width * BORDER_OFFSET_LR;
		double borderHeight = height * BORDER_OFFSET_TB;
		
		double imgSize = height * IMAGE_SIZE;
		double imgOffset = height * IMAGE_OFFSET;
		
		double spacing = width * SPACING_VERTICAL;
		
		healthBar.setWidth(width * HEALTH_WIDTH);
		healthBar.setHeight(height * HEALTH_HEIGHT);
		healthBar.setTranslateX(borderWidth);
		healthBar.setTranslateY(height - healthBar.getHeight() - borderHeight);
		
		characterImg.setFitWidth(imgSize);
		characterImg.setFitHeight(imgSize);
		characterImg.setTranslateX(borderWidth);
		characterImg.setTranslateY(imgOffset);
		
		currentWeapon.setDisplayWidth(imgSize);
		currentWeapon.setDisplayHeight(imgSize);
		currentWeapon.setTranslateX(borderWidth + imgSize + spacing);
		currentWeapon.setTranslateY(imgOffset);
		
		attackButton.setWidth(imgSize);
		attackButton.setHeight(height * ATTACK_BUTTON_HEIGHT);
		attackButton.setTranslateX(borderWidth + imgSize*2 + spacing*2);
		attackButton.setTranslateY(imgOffset + imgSize/2 - height*ATTACK_BUTTON_HEIGHT/2);
		
		hitLog.setMaxHeight(imgSize);
		hitLog.setTranslateX(borderWidth + imgSize*3 + spacing*3);
		hitLog.setTranslateY(imgOffset);
		
		updateHealthBar();
		updateSelectableWeaponsSize(height, width);
	}
	
	private void updateSelectableWeaponsSize(double height, double width) {
		double size = height * WEAPON_SELECTION_SIZE;
		double spacing = size * WEAPON_SELECTION_SPACING;
		
		double offsetX = spacing*2;
		double offsetY = height/2 - size/2;
		
		for (int i = 0; i < selectableWeapons.size(); i++) {
			WeaponDisplay weaponDisplay = selectableWeapons.get(i);
			weaponDisplay.setStroke(stroke.get());
			weaponDisplay.setFill(weaponSelectionBg.get());
			weaponDisplay.setDisplayWidth(size);
			weaponDisplay.setDisplayHeight(size);
			weaponDisplay.setTranslateX(offsetX + (size+spacing)*i);
			weaponDisplay.setTranslateY(offsetY);
		}
	}
	
	private void updateCharacterInfo(Character character) {
		characterImg.setImage(TextureLoader.loadImage(character.getTextureKey()));
		selectableWeapons.clear();
		addSelectableWeapons(character.getWeapons());
		updateSelectedWeapon(character.getWeapon());
	}

	private void updateSelectedWeapon(Weapon weapon) {
		currentWeapon.setWeapon(weapon);
	}
	
	private void addSelectableWeapons(List<Weapon> weapons) {
		for (Weapon weapon : weapons) {
			WeaponDisplay weaponDisplay = new WeaponDisplay(weapon);
			weaponDisplay.setOnAction(this::selectWeapon);
			selectableWeapons.add(weaponDisplay);
		}
		updateSelectableWeaponsSize(getWidth(), getHeight());
	}
	
	private void selectWeapon(AdditionalInfoMouseEvent<Optional<Weapon>> me) {
		me.getAdditionalInformation().ifPresent(w -> character.get().equipWeapon(w));
		updateSelectedWeapon(character.get().getWeapon());
		closeWeaponSelection();
	}
	
	@FXML
	private void showWeaponSelection(MouseEvent me) {
		getChildren().addAll(selectableWeapons);
	}
	
	private void closeWeaponSelection() {
		getChildren().removeAll(selectableWeapons);
	}

	/**
	 * Updates the status of the health bar of the character set in this overview.
	 */
	public void updateHealthBar() {
		if (character.get() != null) {
			double fullWidth = healthBar.getWidth();
			double fullHealth = character.get().getMaxHitPoints();
			double health = character.get().getHitPoints();
			
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

	public ObjectProperty<Character> characterProperty() {
		return character;
	}
	
	public Character getCharacter() {
		return characterProperty().get();
	}
	
	public void setCharacter(Character character) {
		characterProperty().set(character);
	}
	
	public ObjectProperty<EventHandler<? super MouseEvent>> onAttackProperty() {
		return attackButton.onMouseClickedProperty();
	}
	
	public EventHandler<? super MouseEvent> getOnAttack() {
		return onAttackProperty().get();
	}
	
	public void setOnAttack(EventHandler<? super MouseEvent> onAttack) {
		onAttackProperty().set(onAttack);
	}
	
	public ObjectProperty<Paint> strokeProperty() {
		return stroke;
	}
	
	public Paint getStroke() {
		return strokeProperty().get();
	}
	
	public void setStroke(Paint stroke) {
		strokeProperty().set(stroke);
	}
	
	public ObjectProperty<Paint> weaponSelectionBgProperty() {
		return weaponSelectionBg;
	}
	
	public Paint getWeaponSelectionBg() {
		return weaponSelectionBgProperty().get();
	}
	
	public void setWeaponSelectionBg(Paint weaponSelectionBg) {
		weaponSelectionBgProperty().set(weaponSelectionBg);
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
	
	public ObjectProperty<Paint> attackButtonFillProperty() {
		return attackButton.fillProperty();
	}
	
	public Paint getAttackButtonFill() {
		return attackButtonFillProperty().get();
	}
	
	public void setAttackButtonFill(Paint stroke) {
		attackButtonFillProperty().set(stroke);
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