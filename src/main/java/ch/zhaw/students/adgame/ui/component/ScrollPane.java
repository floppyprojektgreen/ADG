package ch.zhaw.students.adgame.ui.component;

import java.io.IOException;
import java.util.logging.Level;

import ch.zhaw.students.adgame.logging.LoggingHandler;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Fully costumized ScrollPane.<br>
 * Only scrollable in y-direction. Scrollbar is always visible<br>
 * Width of child will be smaller the set width by {@link ScrollPane#SCROLL_BAR_WIDTH}.
 */
public class ScrollPane extends HBox {
	/** width of the displayed scroll bar */
	public static final double SCROLL_BAR_WIDTH = 15;

	private static final double SCROLL_BAR_HEIGHT_MIN = 20;
	
	private static final double SCROLL_SPEED_MOD = 3;
	
	@FXML
	private AnchorPane childContainer;
	@FXML
	private Rectangle scrollBar;
	@FXML
	private Rectangle clip;
	
	private ObjectProperty<Node> child;
	
	private DoubleProperty paneWidth;
	private DoubleProperty paneHeight;
	
	private double oldMouseY;
	
	/**
	 * Construct a new {@link ScrollPane}.
	 */
	public ScrollPane() {
		child = new SimpleObjectProperty<>();
		paneWidth = new SimpleDoubleProperty(SCROLL_BAR_WIDTH);
		paneHeight = new SimpleDoubleProperty(0);
		
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ScrollPane.fxml"));
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
		child.addListener(this::changeChild);
		
		paneWidth.addListener((o, oldVal, newVal) -> clip.setWidth(newVal.doubleValue() - SCROLL_BAR_WIDTH));
		paneHeight.addListener((o, oldVal, newVal) -> clip.setHeight(newVal.doubleValue()));
		
		paneWidth.addListener((o, oldVal, newVal) -> setMinWidth(newVal.doubleValue()));
		paneWidth.addListener((o, oldVal, newVal) -> setMaxWidth(newVal.doubleValue()));
		paneHeight.addListener((o, oldVal, newVal) -> setMinHeight(newVal.doubleValue()));
		paneHeight.addListener((o, oldVal, newVal) -> setMaxHeight(newVal.doubleValue()));
		
		paneHeight.addListener((o, oldVal, newVal) -> recalcHeightListener(newVal.doubleValue(), childContainer.getHeight()));
		childContainer.heightProperty().addListener((o, oldVal, newVal) -> recalcHeightListener(getPaneHeight(), newVal.doubleValue()));
		
		childContainer.minWidthProperty().bindBidirectional(clip.widthProperty());
		childContainer.maxWidthProperty().bindBidirectional(clip.widthProperty());
		
		setClip(new Rectangle(1920, 1080));
		
		scrollBar.setWidth(SCROLL_BAR_WIDTH);
		scrollBar.setHeight(100);
		
		setOnScroll((se) -> scrollContentBy(se.getDeltaY() * SCROLL_SPEED_MOD));
		
		scrollBar.setOnMousePressed((me) -> oldMouseY = me.getSceneY());
		scrollBar.setOnMouseDragged(this::scrollBarDrag);
	}
	
	private void changeChild(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
		childContainer.getChildren().clear();
		childContainer.getChildren().add(newValue);
	}
	
	private void recalcHeightListener(double paneHeight, double childHeight) {
		if (getPaneHeight() >= childContainer.getHeight()) {
			scrollBar.setHeight(getPaneHeight());
		} else {
			double percent = getPaneHeight()/childContainer.getHeight();
			scrollBar.setHeight(getPaneHeight() * percent);
			
			if (scrollBar.getHeight() < SCROLL_BAR_HEIGHT_MIN) {
				scrollBar.setHeight(SCROLL_BAR_HEIGHT_MIN);
			}
		}
	}
	
	private void scrollBarDrag(MouseEvent me) {
		scrollScrollBarBy(oldMouseY - me.getSceneY());
		oldMouseY = me.getSceneY();
	}
	
	private void scrollScrollBarBy(double deltaY) {
		if (getPaneHeight() > scrollBar.getHeight()) {
			double scrollVal = scrollBar.getTranslateY() - deltaY;
			double scrollHeight = getPaneHeight() - scrollBar.getHeight();
			if (scrollVal < 0) {
				scrollVal = 0;
			} else if (scrollVal > (getPaneHeight() - scrollBar.getHeight())) {
				scrollVal = scrollHeight;
			}
			scrollBar.setTranslateY(scrollVal);
			
			double percent = scrollVal/scrollHeight;
			double childShift = (childContainer.getHeight() - getPaneHeight()) * percent;
			clip.setTranslateY(childShift);
			childContainer.setTranslateY(-childShift);
		}
	}
	
	private void scrollContentBy(double deltaY) {
		if (childContainer.getHeight() > getPaneHeight()) {
			double scrollVal = childContainer.getTranslateY() + deltaY;
			double scrollHeight = childContainer.getHeight() - getPaneHeight();
			if (scrollVal < -scrollHeight) {
				scrollVal = -scrollHeight;
			} else if (scrollVal > 0) {
				scrollVal = 0;
			}
	
			clip.setTranslateY(-scrollVal);
			childContainer.setTranslateY(scrollVal);
			
			double percent = scrollVal/scrollHeight;
			double barShift = (getPaneHeight() - scrollBar.getHeight()) * percent;
			scrollBar.setTranslateY(-barShift);
		}
	}
	
	public Node getChild() {
		return childProperty().get();
	}
	
	public void setChild(Node child) {
		childProperty().set(child);
	}
	
	public ObjectProperty<Node> childProperty() {
		return child;
	}
	
	public Paint getBarColor() {
		return barColorProperty().get();
	}
	
	public void setBarColor(Paint barColor) {
		barColorProperty().set(barColor);
	}
	
	public ObjectProperty<Paint> barColorProperty() {
		return scrollBar.fillProperty();
	}
	
	public double getPaneWidth() {
		return paneWidthProperty().get();
	}
	
	public void setPaneWidth(double viewWidth) {
		paneWidthProperty().set(viewWidth);
	}
	
	public DoubleProperty paneWidthProperty() {
		return paneWidth;
	}
	
	public double getPaneHeight() {
		return paneHeightProperty().get();
	}
	
	public void setPaneHeight(double viewHeight) {
		paneHeightProperty().set(viewHeight);
	}
	
	public DoubleProperty paneHeightProperty() {
		return paneHeight;
	}
}
