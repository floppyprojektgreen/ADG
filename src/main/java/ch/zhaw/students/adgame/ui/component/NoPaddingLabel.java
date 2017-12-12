package ch.zhaw.students.adgame.ui.component;

import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * Label with no padding.
 */
public class NoPaddingLabel extends Label {
	private DoubleProperty fontSize;
	
	/**
	 * Creates a new Label with no padding.
	 */
	public NoPaddingLabel() {
		fontSize = new SimpleDoubleProperty(getFont().getSize());
		fontSize.addListener((o, oldVal, newVal) -> changeFontSizeing(getFont(), newVal.doubleValue()));
		fontProperty().addListener((o, oldVal, newVal) -> changeFontSizeing(newVal, getFontSize()));
		heightProperty().addListener((o, oldVal, newVal) -> changeFontSizeing(getFont(), getFontSize()));
	}
	
	private void changeFontSizeing(Font font, double size) {
		FontWeight weight = null;
		FontPosture posture = null;
		
		for (String s : font.getStyle().split(" ")) {
			weight = (weight != null ? weight : FontWeight.findByName(s));
			posture = (posture != null ? posture : FontPosture.findByName(s));
		}
		
		setFont(Font.font(font.getFamily(), weight, posture, size));
		FontMetrics metrics = Toolkit.getToolkit().getFontLoader().getFontMetrics(getFont());
		setPadding(new Insets(-metrics.getDescent(), 0, 0, 0));
	}

	public DoubleProperty fontSizeProperty() {
		return fontSize;
	}

	public double getFontSize() {
		return fontSizeProperty().get();
	}

	public void setFontSize(double fontSize) {
		fontSizeProperty().set(fontSize);
	}
	
}
