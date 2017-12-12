package ch.zhaw.students.adgame.ui.component;

import java.io.IOException;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

/**
 * Component to display a Radial-Menu with 6 rectangles as its selection-options and
 * one rectangle in the middle for returning. Layout in RectangleRadialMenu.fxml
 * @param <T>
 */
public class HexagonalRadialMenu<T> extends HBox {
	private static double HOVER_INCREASE_FACTOR = 0.05;
	
	@FXML
	private HBox root;
	@FXML
	private Polygon bPoly;
	@FXML
	private Polygon nPoly;
	@FXML
	private Polygon nePoly;
	@FXML
	private Polygon sePoly;
	@FXML
	private Polygon sPoly;
	@FXML
	private Polygon swPoly;
	@FXML
	private Polygon nwPoly;
	@FXML
	private ImageView bImg;
	@FXML
	private ImageView nImg;
	@FXML
	private ImageView neImg;
	@FXML
	private ImageView seImg;
	@FXML
	private ImageView sImg;
	@FXML
	private ImageView swImg;
	@FXML
	private ImageView nwImg;
	@FXML
	private Rectangle layoutfix;
	
	private BooleanProperty regular;
	private DoubleProperty hexagonWidth;
	private DoubleProperty hexagonHeight;
	
	private DoubleProperty centerX;
	private DoubleProperty centerY;
	
	private ObjectProperty<HexagonMenuItem<T>> back;
	private ObjectProperty<HexagonMenuItem<T>> north;
	private ObjectProperty<HexagonMenuItem<T>> northEast;
	private ObjectProperty<HexagonMenuItem<T>> southEast;
	private ObjectProperty<HexagonMenuItem<T>> south;
	private ObjectProperty<HexagonMenuItem<T>> southWest;
	private ObjectProperty<HexagonMenuItem<T>> northWest;
	
	/**
	 * Creates a new {@link HexagonalRadialMenu}. Loads the corresponding fxml and adds itself as controller.
	 */
	public HexagonalRadialMenu() {
		regular = new SimpleBooleanProperty();
		hexagonWidth = new SimpleDoubleProperty();
		hexagonHeight = new SimpleDoubleProperty();
		
		centerX = new SimpleDoubleProperty();
		centerY = new SimpleDoubleProperty();
		
		back = new SimpleObjectProperty<>();
		north = new SimpleObjectProperty<>();
		northEast = new SimpleObjectProperty<>();
		southEast = new SimpleObjectProperty<>();
		south = new SimpleObjectProperty<>();
		southWest = new SimpleObjectProperty<>();
		northWest = new SimpleObjectProperty<>();
		
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HexagonalRadialMenu.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
        	throw new RuntimeException(exception);
        }
    }
	
	@FXML
	private void initialize() {
		regular.set(false);
		hexagonWidth.set(1.0);
		hexagonHeight.set(1.0);
		
		centerX.set(0.0);
		centerY.set(0.0);
		
		back.set(new HexagonMenuItem<>(bPoly, bImg));
		north.set(new HexagonMenuItem<>(nPoly, nImg));
		northEast.set(new HexagonMenuItem<>(nePoly, neImg));
		southEast.set(new HexagonMenuItem<>(sePoly, seImg));
		south.set(new HexagonMenuItem<>(sPoly, sImg));
		southWest.set(new HexagonMenuItem<>(swPoly, swImg));
		northWest.set(new HexagonMenuItem<>(nwPoly, nwImg));
		
		regular.addListener((o, oldVal, newVal) -> size());
		hexagonWidth.addListener((o, oldVal, newVal) -> size());
		hexagonHeight.addListener((o, oldVal, newVal) -> size());
		
		centerX.addListener((o, oldVal, newVal) -> size());
		centerY.addListener((o, oldVal, newVal) -> size());
		
		bindBidirectional(bImg, bPoly);
		bindBidirectional(nImg, nPoly);
		bindBidirectional(neImg, nePoly);
		bindBidirectional(seImg, sePoly);
		bindBidirectional(sImg, sPoly);
		bindBidirectional(swImg, swPoly);
		bindBidirectional(nwImg, nwPoly);
	}
	
	private void bindBidirectional(Node a, Node b) {
		a.scaleXProperty().bindBidirectional(b.scaleXProperty());
		a.scaleYProperty().bindBidirectional(b.scaleYProperty());
		a.translateXProperty().bindBidirectional(b.translateXProperty());
		a.translateYProperty().bindBidirectional(b.translateYProperty());
	}

	private void size() {
		double singleHeight = getHexagonHeight();
		double singleWidth = getHexagonWidth();
		if (isRegular()) {
			singleWidth = singleHeight / (Math.sin(Math.PI/3));
			setHexagonWidth(singleWidth);
		}
		
		root.setTranslateX((double)getCenterX() - (getHexagonWidth()*(1.25 + HOVER_INCREASE_FACTOR/2)));
		root.setTranslateY((double)getCenterY() - (getHexagonHeight()*(1.5 + HOVER_INCREASE_FACTOR/2)));
		
		scaleAndTranslate(layoutfix, singleWidth, singleHeight, -HOVER_INCREASE_FACTOR/2, -HOVER_INCREASE_FACTOR/2);
		
		scaleAndTranslate(bPoly, singleWidth, singleHeight, 0.75, 1);
		scaleAndTranslate(nPoly, singleWidth, singleHeight, 0.75, 0);
		scaleAndTranslate(nePoly, singleWidth, singleHeight, 1.5, 0.5);
		scaleAndTranslate(sePoly, singleWidth, singleHeight, 1.5, 1.5);
		scaleAndTranslate(sPoly, singleWidth, singleHeight, 0.75, 2);
		scaleAndTranslate(swPoly, singleWidth, singleHeight, 0, 1.5);
		scaleAndTranslate(nwPoly, singleWidth, singleHeight, 0, 0.5);
	}
	
	private void scaleAndTranslate(Node node, double width, double height, double transX, double transY) {
		node.setScaleX(width);
		node.setScaleY(height);
		
		node.setTranslateX(width * (transX + HOVER_INCREASE_FACTOR));
		node.setTranslateY(height * (transY + HOVER_INCREASE_FACTOR));
	}
	
	@FXML
	private void increaseByHover(MouseEvent me) {
		Node s = (Node)me.getSource();
		double scaleFactor = HOVER_INCREASE_FACTOR + 1;
		s.setScaleX(s.getScaleX() * scaleFactor);
		s.setScaleY(s.getScaleY() * scaleFactor);
		s.toFront();
	}
	
	@FXML
	private void decreaseByHover(MouseEvent me) {
		Node s = (Node)me.getSource();
		double scaleFactor = 1/(HOVER_INCREASE_FACTOR + 1);
		s.setScaleX(s.getScaleX() * scaleFactor);
		s.setScaleY(s.getScaleY() * scaleFactor);
	}
	
	/**
	 * Get if hexagon are regular.<br>
	 * Default <code>false</code>.
	 */
	public boolean isRegular() {
		return regularProperty().get();
	}
	
	/**
	 * Set if hexagon are regular.
	 */
	public void setRegular(boolean value) {
		regularProperty().set(value);
	}
	
	/**
	 * Property for regularity of hexagons.
	 */
	public BooleanProperty regularProperty() {
		return regular;
	}
	
	/**
	 * Get width of single hexagon.<br>
	 * Default <code>1.0</code>.<br>
	 * <b>Ignored if <code>regular == true</code></b>
	 */
	public double getHexagonWidth() {
		return hexagonWidthProperty().get();
	}
	
	/**
	 * Set width of single hexagon.
	 * <b>Ignored if <code>regular == true</code></b>
	 */
	public void setHexagonWidth(double value) {
		hexagonWidthProperty().set(value);
	}
	
	/**
	 * Width property of single hexagon.
	 * <b>Ignored if <code>regular == true</code></b>
	 */
	public DoubleProperty hexagonWidthProperty() {
		return hexagonWidth;
	}
	
	/**
	 * Get height of single hexagon.<br>
	 * Default <code>1.0</code>.
	 */
	public double getHexagonHeight() {
		return hexagonHeightProperty().get();
	}
	
	/**
	 * Set height of single hexagon.
	 */
	public void setHexagonHeight(double value) {
		hexagonHeightProperty().set(value);
	}
	
	/**
	 * Height property of single hexagon.
	 */
	public DoubleProperty hexagonHeightProperty() {
		return hexagonHeight;
	}
	
	public double getCenterX() {
		return centerXProperty().get();
	}
	
	public void setCenterX(double value) {
		centerXProperty().set(value);
	}
	
	public DoubleProperty centerXProperty() {
		return centerX;
	}
	
	public double getCenterY() {
		return centerYProperty().get();
	}
	
	public void setCenterY(double value) {
		centerYProperty().set(value);
	}
	
	public DoubleProperty centerYProperty() {
		return centerY;
	}
	
	public HexagonMenuItem<T> getBack() {
		return backProperty().get();
	}
	
	public ReadOnlyObjectProperty<HexagonMenuItem<T>> backProperty() {
		return back;
	}
	
	public HexagonMenuItem<T> getNorth() {
		return northProperty().get();
	}
	
	public ReadOnlyObjectProperty<HexagonMenuItem<T>> northProperty() {
		return north;
	}
	
	public HexagonMenuItem<T> getNorthEast() {
		return northEastProperty().get();
	}
	
	public ReadOnlyObjectProperty<HexagonMenuItem<T>> northEastProperty() {
		return northEast;
	}
	
	public HexagonMenuItem<T> getSouthEast() {
		return southEastProperty().get();
	}
	
	public ReadOnlyObjectProperty<HexagonMenuItem<T>> southEastProperty() {
		return southEast;
	}
	
	public HexagonMenuItem<T> getSouth() {
		return southProperty().get();
	}
	
	public ReadOnlyObjectProperty<HexagonMenuItem<T>> southProperty() {
		return south;
	}
	
	public HexagonMenuItem<T> getSouthWest() {
		return southWestProperty().get();
	}
	
	public ReadOnlyObjectProperty<HexagonMenuItem<T>> southWestProperty() {
		return southWest;
	}
	
	public HexagonMenuItem<T> getNorthWest() {
		return northWestProperty().get();
	}
	
	public ReadOnlyObjectProperty<HexagonMenuItem<T>> northWestProperty() {
		return northWest;
	}
}