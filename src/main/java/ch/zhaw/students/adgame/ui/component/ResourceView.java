package ch.zhaw.students.adgame.ui.component;

import java.io.IOException;
import java.util.logging.Level;

import ch.zhaw.students.adgame.configuration.Texture;
import ch.zhaw.students.adgame.logging.LoggingHandler;
import ch.zhaw.students.adgame.resource.TextureLoader;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Component to display the resource of a player. Layout in ResourceView.fxml
 */
public class ResourceView extends HBox {
	@FXML
	private ImageView resourceImage;
	@FXML
	private NoPaddingLabel resourceAmount;
	
	/**
	 * Creates a new ResourceView. Loads the corresponding fxml and adds itself as controller.
	 */
	public ResourceView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ResourceView.fxml"));
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
		resourceImage.setImage(TextureLoader.loadImage(Texture.Hud.SOUL));
		
		resourceImage.fitHeightProperty().addListener((val, oldVal, newVal) -> resourceAmount.setFontSize((double) newVal * 0.9));
	}
	
	public String getText() {
		return textProperty().get();
	}

	public void setText(String text) {
		textProperty().set(text);
	}

	public StringProperty textProperty() {
		return resourceAmount.textProperty();
	}
	
	public Double getImgWidth() {
		return imgWidthProperty().get();
	}

	public void setImgWidth(Double width) {
		imgWidthProperty().set(width);
	}

	public DoubleProperty imgWidthProperty() {
		return resourceImage.fitWidthProperty();
	}
	
	public Double getImgHeight() {
		return imgHeightProperty().get();
	}

	public void setImgHeight(Double height) {
		imgHeightProperty().set(height);
	}

	public DoubleProperty imgHeightProperty() {
		return resourceImage.fitHeightProperty();
	}
}
