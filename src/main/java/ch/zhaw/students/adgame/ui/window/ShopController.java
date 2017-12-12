package ch.zhaw.students.adgame.ui.window;

import ch.zhaw.students.adgame.configuration.ColorConfiguration;
import ch.zhaw.students.adgame.domain.GameState;
import ch.zhaw.students.adgame.domain.Shop;
import ch.zhaw.students.adgame.domain.item.Item;
import ch.zhaw.students.adgame.resource.ColorLoader;
import ch.zhaw.students.adgame.ui.ResizableUI;
import ch.zhaw.students.adgame.ui.WindowHandler;
import ch.zhaw.students.adgame.ui.component.ItemListItem;
import ch.zhaw.students.adgame.ui.component.ResourceView;
import ch.zhaw.students.adgame.ui.component.ScrollPane;
import ch.zhaw.students.adgame.ui.event.AdditionalInfoMouseEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

/**
 * JavaFx controller for shop.fxml
 */
public class ShopController implements ResizableUI {
	private static double PLAYER_RESOURCE_SIZE = 50;
	private static double ITEM_IMG_SIZE = 150;
	private static double ITEM_FONT_SIZE = 30;
	
	@FXML
	private Rectangle bg;
	@FXML
	private ResourceView resource;
	@FXML
	private Rectangle closeBtn;
	@FXML
	private ScrollPane itemList;
	@FXML
	private VBox itemContainer;
	
	private Shop shop;

	@FXML
	private void initialize() {
		shop = new Shop();
		
		resource.setText(GameState.get().getCurrentCharacter().getResource() + "");
		
		bg.setFill(ColorLoader.loadColor(ColorConfiguration.SHOP_BG));
		itemList.setBarColor(ColorLoader.loadColor(ColorConfiguration.SHOP_SCROLL_BAR));
		
		closeBtn.setFill(ColorLoader.loadColor(ColorConfiguration.SHOP_EXIT));
		
		shop.getAffordableItems().forEach(this::addNewItem);
	}
	
	private void addNewItem(Item item) {
		ItemListItem listitem = new ItemListItem();
		listitem.setItem(item);
		
		listitem.setOnAction(this::onItemClick);
		listitem.setHoverColor(ColorLoader.loadColor(ColorConfiguration.SHOP_ROW_HOVER));
		if (itemContainer.getChildren().size()%2 == 0) {
			listitem.setBgColor(ColorLoader.loadColor(ColorConfiguration.SHOP_ROW_ODD));
		} else {
			listitem.setBgColor(ColorLoader.loadColor(ColorConfiguration.SHOP_ROW_EVEN));
		}
		
		listitem.setImageSize(ITEM_IMG_SIZE);
		listitem.setFontSize(ITEM_FONT_SIZE);
		itemContainer.getChildren().add(listitem);
	}
	
	private void onItemClick(AdditionalInfoMouseEvent<Item> me) {
		shop.buyItem(me.getAdditionalInformation());
		WindowHandler.get().closeHighestInMainStack();
	}
	
	@FXML
	private void closeShop(MouseEvent me) {
		WindowHandler.get().closeHighestInMainStack();
	}
	
	@Override
	public void resize(double width, double height) {
		bg.setWidth(width);
		bg.setHeight(height);

		resource.setImgWidth(PLAYER_RESOURCE_SIZE);
		resource.setImgHeight(PLAYER_RESOURCE_SIZE);
		
		closeBtn.setWidth(PLAYER_RESOURCE_SIZE);
		closeBtn.setHeight(PLAYER_RESOURCE_SIZE);
		closeBtn.setTranslateX(bg.getWidth() - PLAYER_RESOURCE_SIZE);
		
		itemList.setPaneWidth(width);
		itemList.setPaneHeight(height - PLAYER_RESOURCE_SIZE);
		itemList.setTranslateY(PLAYER_RESOURCE_SIZE);
	}
}
