package ch.zhaw.students.adgame.ui.component;

import java.io.IOException;
import java.util.logging.Level;

import ch.zhaw.students.adgame.logging.LoggingHandler;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class LabelButton extends Group {
	private static final double HOVER_INCREASE_FACTOR = 0.05;
	private static final double BUTTON_FONT_SIZE = 0.6;
	
	@FXML
	private Group container;
	@FXML
	private Rectangle box;
	@FXML
	private HBox textBox;
	@FXML
	private Label label;
	
	public LabelButton() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LabelButton.fxml"));
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
		textBox.minWidthProperty().bindBidirectional(box.widthProperty());
		textBox.maxWidthProperty().bindBidirectional(box.widthProperty());
		textBox.minHeightProperty().bindBidirectional(box.heightProperty());
		textBox.maxHeightProperty().bindBidirectional(box.heightProperty());
		textBox.translateXProperty().bindBidirectional(box.translateXProperty());
		textBox.translateYProperty().bindBidirectional(box.translateYProperty());
		
		box.heightProperty().addListener((o, oldVal, newVal) -> label.setFont(Font.font(newVal.doubleValue()*BUTTON_FONT_SIZE)));
		
		box.heightProperty().addListener((o, oldVal, newVal) -> box.setTranslateY(newVal.doubleValue()*HOVER_INCREASE_FACTOR/2));
		
	}
	
	@FXML
	private void increaseByHover(MouseEvent me) {
		double scaleFactor = HOVER_INCREASE_FACTOR + 1;
		container.setScaleX(container.getScaleX() * scaleFactor);
		container.setScaleY(container.getScaleY() * scaleFactor);
		container.toFront();
	}
	
	@FXML
	private void decreaseByHover(MouseEvent me) {
		double scaleFactor = 1/(HOVER_INCREASE_FACTOR + 1);
		container.setScaleX(container.getScaleX() * scaleFactor);
		container.setScaleY(container.getScaleY() * scaleFactor);
	}
	
	public ObjectProperty<Paint> fillProperty() {
		return box.fillProperty();
	}
	
	public Paint getFill() {
		return fillProperty().get();
	}
	
	public void setFill(Paint fill) {
		fillProperty().set(fill);
	}
	
	public DoubleProperty widthProperty() {
		return box.widthProperty();
	}
	
	public double getWidth() {
		return widthProperty().get();
	}
	
	public void setWidth(double width) {
		widthProperty().set(width);
	}
	
	public DoubleProperty heightProperty() {
		return box.heightProperty();
	}
	
	public double getHeight() {
		return heightProperty().get();
	}
	
	public void setHeight(double width) {
		heightProperty().set(width);
	}
	
	public StringProperty textProperty() {
		return label.textProperty();
	}
	
	public String getText() {
		return textProperty().get();
	}
	
	public void setText(String text) {
		textProperty().set(text);
	}
	
	public ObjectProperty<EventHandler<? super MouseEvent>> onActionProperty() {
		return box.onMouseClickedProperty();
	}
	
	public EventHandler<? super MouseEvent> getOnAction() {
		return onActionProperty().get();
	}
	
	public void setOnAction(EventHandler<? super MouseEvent> onAction) {
		onActionProperty().set(onAction);
	}
}
