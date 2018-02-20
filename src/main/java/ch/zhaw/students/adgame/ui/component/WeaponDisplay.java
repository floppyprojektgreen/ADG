package ch.zhaw.students.adgame.ui.component;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;

import ch.zhaw.students.adgame.configuration.Texture;
import ch.zhaw.students.adgame.domain.item.Weapon;
import ch.zhaw.students.adgame.logging.LoggingHandler;
import ch.zhaw.students.adgame.resource.TextureLoader;
import ch.zhaw.students.adgame.ui.event.AdditionalInfoMouseEvent;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Display for a given Weapon. Will have a stroke of width 5 by default.
 */
public class WeaponDisplay extends AnchorPane {
	@FXML
	private ImageView weaponImg;
	@FXML
	private Rectangle container;
	
	private ObjectProperty<EventHandler<AdditionalInfoMouseEvent<Optional<Weapon>>>> onAction;
	private ObjectProperty<Weapon> weapon;
	
	/**
	 * Constructor for creating an instance with a already defined weapon.
	 */
	public WeaponDisplay(Weapon weapon) {
		this.onAction = new SimpleObjectProperty<>(e -> {});
		this.weapon = new SimpleObjectProperty<>(weapon);
		
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WeaponDisplay.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
        	LoggingHandler.log(e, Level.SEVERE);
        }
    }
	
	/**
	 * Default constructor with no weapon set.
	 */
	public WeaponDisplay() {
		this(null);
	}
	
	@FXML
	private void initialize() {
		weaponImg.fitWidthProperty().bindBidirectional(container.widthProperty());
		weaponImg.fitHeightProperty().bindBidirectional(container.heightProperty());
		
		container.setOnMouseClicked(me -> onAction.get().handle(new AdditionalInfoMouseEvent<Optional<Weapon>>(me, Optional.ofNullable(weapon.get()))));
		
		weapon.addListener((o, oldVal, newVal) -> changeWeapon(newVal));
		changeWeapon(weapon.get());
	}

	private void changeWeapon(Weapon weapon) {
		weaponImg.setImage(TextureLoader.loadImage(weapon != null
				? weapon.getTextureKey()
				: Texture.Item.Equipment.Weapon.General.NO_WEAPON));
	}

	public ObjectProperty<EventHandler<AdditionalInfoMouseEvent<Optional<Weapon>>>> onActionProperty() {
		return onAction;
	}

	public EventHandler<AdditionalInfoMouseEvent<Optional<Weapon>>> getOnAction() {
		return onActionProperty().get();
	}

	public void setOnAction(EventHandler<AdditionalInfoMouseEvent<Optional<Weapon>>> onAction) {
		onActionProperty().set(onAction);
	}

	public ObjectProperty<Weapon> weaponProperty() {
		return weapon;
	}

	public Weapon getWeapon() {
		return weaponProperty().get();
	}

	public void setWeapon(Weapon weapon) {
		weaponProperty().set(weapon);
	}
	
	public DoubleProperty displayWidthProperty() {
		return weaponImg.fitWidthProperty();
	}
	
	public double getDisplayWidth() {
		return displayWidthProperty().get();
	}
	
	public void setDisplayWidth(double width) {
		displayWidthProperty().set(width);
	}
	
	public DoubleProperty displayHeightProperty() {
		return weaponImg.fitHeightProperty();
	}

	public double getDisplayHeight() {
		return displayHeightProperty().get();
	}
	
	public void setDisplayHeight(double height) {
		displayHeightProperty().set(height);
	}
	
	public DoubleProperty strokeWidthProperty() {
		return container.strokeWidthProperty();
	}
	
	public double getStrokeWidth() {
		return strokeWidthProperty().get();
	}
	
	public void setStrokeWidth(double strokeWidth) {
		strokeWidthProperty().set(strokeWidth);
	}
	
	public ObjectProperty<Paint> strokeProperty() {
		return container.strokeProperty();
	}
	
	public Paint getStroke() {
		return strokeProperty().get();
	}
	
	public void setStroke(Paint stroke) {
		strokeProperty().set(stroke);
	}
	
	public ObjectProperty<Paint> fillProperty() {
		return container.fillProperty();
	}
	
	public Paint getFill() {
		return fillProperty().get();
	}
	
	public void setFill(Paint fill) {
		fillProperty().set(fill);
	}
}