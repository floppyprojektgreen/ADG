package ch.zhaw.students.adgame.ui.component;

import java.io.IOException;
import java.util.logging.Level;

import ch.zhaw.students.adgame.domain.item.Item;
import ch.zhaw.students.adgame.logging.LoggingHandler;
import ch.zhaw.students.adgame.resource.TextureLoader;
import ch.zhaw.students.adgame.ui.event.AdditionalInfoMouseEvent;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Class for displaying a single item in a list-item like manor.<br>
 * For setting the item use {@link ItemListItem#setItem(Item)}.<br>
 * <br>
 * Item list-item.
 */
public class ItemListItem extends AnchorPane {
	private static final double TITLE_FONT_SIZE_FACTOR = 1.2;
	
	@FXML
	private Rectangle bg;
	@FXML
	private ImageView image;
	@FXML
	private NoPaddingLabel name;
	@FXML
	private NoPaddingLabel description; //not used at the moment, except for layouting
	@FXML
	private ResourceView cost;
	
	private DoubleProperty imageSize;
	private DoubleProperty fontSize;
	
	private ObjectProperty<EventHandler<AdditionalInfoMouseEvent<Item>>> onAction;
	private ObjectProperty<Item> item;
	
	private ObjectProperty<Paint> hoverColor;
	
	/**
	 * Creates a new Item list-item.<br>
	 * For setting the item use {@link ItemListItem#setItem(Item)}.
	 */
	public ItemListItem() {
		imageSize = new SimpleDoubleProperty(0);
		fontSize = new SimpleDoubleProperty(0);
		
		onAction = new SimpleObjectProperty<>((e) -> {});
		item = new SimpleObjectProperty<>();
		
		hoverColor = new SimpleObjectProperty<Paint>();
		
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ItemListItem.fxml"));
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
		name.setFont(Font.font(name.getFont().getFamily(), FontWeight.BOLD, name.getFontSize()));
		
		bg.widthProperty().bind(widthProperty());
		bg.heightProperty().bind(heightProperty());
		image.fitWidthProperty().bind(imageSize);
		image.fitHeightProperty().bind(imageSize);
		
		fontSize.addListener(this::fontResizeListener);
		item.addListener(this::itemChangeListener);
		
		setOnMouseClicked(me -> onAction.get().handle(new AdditionalInfoMouseEvent<Item>(me, item.get())));
		setOnMouseEntered(me -> swapColor());
		setOnMouseExited(me -> swapColor());
	}
	
	private void swapColor() {
		if (hoverColor.get() != null) {
			Paint old = bg.getFill();
			bg.setFill(hoverColor.get());
			hoverColor.set(old);
		}
	}
	
	private void fontResizeListener(ObservableValue<? extends Number> o, Number oldVal, Number newVal) {
		name.setFontSize(newVal.doubleValue() * TITLE_FONT_SIZE_FACTOR);
		description.setFontSize(newVal.doubleValue());
		cost.setImgWidth(newVal.doubleValue());
		cost.setImgHeight(newVal.doubleValue());
	}
	
	private void itemChangeListener(ObservableValue<? extends Item> o, Item oldVal, Item newVal) {
		image.setImage(TextureLoader.loadImage(newVal.getTextureKey()));
		name.setText(newVal.getName());
		description.setText(""); // leave empty, only for layouting atm
		cost.setText(newVal.getCost() + "");
	}

	public DoubleProperty imageSizeProperty() {
		return imageSize;
	}
	
	public double getImageSize() {
		return imageSizeProperty().get();
	}

	public void setImageSize(double imageSize) {
		imageSizeProperty().set(imageSize);
	}
	
	public DoubleProperty fontSizeProperty() {
		return fontSize;
	}
	
	public double getFontSize() {
		return fontSizeProperty().get();
	}

	public void setFontSize(final double fontSize) {
		fontSizeProperty().set(fontSize);
	}

	public ObjectProperty<EventHandler<AdditionalInfoMouseEvent<Item>>> onActionProperty() {
		return onAction;
	}

	public EventHandler<AdditionalInfoMouseEvent<Item>> getOnAction() {
		return onActionProperty().get();
	}

	public void setOnAction(EventHandler<AdditionalInfoMouseEvent<Item>> onAction) {
		onActionProperty().set(onAction);
	}

	public ObjectProperty<Item> itemProperty() {
		return item;
	}
	
	public Item getItem() {
		return itemProperty().get();
	}
	
	public void setItem(Item item) {
		itemProperty().set(item);
	}
	
	public ObjectProperty<Paint> bgColorProperty() {
		return bg.fillProperty();
	}
	
	public Paint getBgColor() {
		return bgColorProperty().get();
	}
	
	public void setBgColor(Paint bgColor) {
		bgColorProperty().set(bgColor);
	}

	public ObjectProperty<Paint> hoverColorProperty() {
		return hoverColor;
	}

	public Paint getHoverColor() {
		return hoverColorProperty().get();
	}

	public void setHoverColor(Paint hoverColor) {
		hoverColorProperty().set(hoverColor);
	}
}
