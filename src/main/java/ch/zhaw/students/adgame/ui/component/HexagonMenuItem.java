package ch.zhaw.students.adgame.ui.component;

import java.util.Optional;

import ch.zhaw.students.adgame.ui.event.AdditionalInfoMouseEvent;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;

/**
 * Wrapper class for a hexagon menu item, used in {@link HexagonalRadialMenu}.
 * Can also hold an object for later use in action method.
 */
public class HexagonMenuItem<T> {
	private Polygon item;
	private ImageView image;
	private ObjectProperty<EventHandler<AdditionalInfoMouseEvent<Optional<T>>>> onAction;
	private BooleanProperty disabled;
	private Optional<T> obj;
	
	/**
	 * Creates a new hexagon menu item with a polygon as button.
	 */
	public HexagonMenuItem(Polygon item, ImageView img) {
		this.item = item;
		this.image = img;
		this.onAction = new SimpleObjectProperty<EventHandler<AdditionalInfoMouseEvent<Optional<T>>>>(e -> {});
		this.disabled = new SimpleBooleanProperty(false);
		this.obj = Optional.empty();
		
		this.item.setOnMouseClicked(this::onAction);
		
		this.item.setFill(Color.TRANSPARENT);
	}
	
	private void onAction(MouseEvent me) {
		if(!disabled.get()) {
			onAction.get().handle(new AdditionalInfoMouseEvent<Optional<T>>(me, obj));
		}
	}
	
	public EventHandler<AdditionalInfoMouseEvent<Optional<T>>> getOnAction() {
		return onActionProperty().get();
	}
	
	public void setOnAction(EventHandler<AdditionalInfoMouseEvent<Optional<T>>> value) {
		onActionProperty().set(value);
	}
	
	public ObjectProperty<EventHandler<AdditionalInfoMouseEvent<Optional<T>>>> onActionProperty() {
		return onAction;
	}
	
	public Paint getFill() {
		return fillProperty().get();
	}
	
	public void setFill(Paint value) {
		fillProperty().set(value);
	}
	
	public ObjectProperty<Paint> fillProperty() {
		return item.fillProperty();
	}
	
	public boolean isDisabled() {
		return disabledProperty().get();
	}
	
	public void setDisabled(boolean value) {
		disabledProperty().set(value);
	}
	
	public BooleanProperty disabledProperty() {
		return disabled;
	}
	
	public Image getImage() {
		return imageProperty().get();
	}
	
	public void setImage(Image value) {
		imageProperty().set(value);
	}
	
	public ObjectProperty<Image> imageProperty() {
		return image.imageProperty();
	}

	public Optional<T> getObj() {
		return obj;
	}

	public void setObj(Optional<T> obj) {
		this.obj = obj;
	}
}