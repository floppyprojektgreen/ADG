package ch.zhaw.students.adgame.ui.window;

import ch.zhaw.students.adgame.configuration.ColorConfiguration;
import ch.zhaw.students.adgame.domain.GameState;
import ch.zhaw.students.adgame.domain.entity.Character;
import ch.zhaw.students.adgame.domain.event.RoundResults;
import ch.zhaw.students.adgame.resource.ColorLoader;
import ch.zhaw.students.adgame.ui.ResizableUI;
import ch.zhaw.students.adgame.ui.WindowHandler;
import ch.zhaw.students.adgame.ui.component.BattleCharacterOverview;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

/**
 * JavaFx controller for CharacterBattle.fxml
 */
public class CharacterBattleController implements ResizableUI {
	private static final double HEIGHT_FACTOR = 0.95;
	private static final double WIDTH_FACTOR = 0.95;
	
	private static final double CHARACTER_SPACE = 0.39; //used 2 times
	private static final double INBETWEEN_SPACE = 0.01; //used 2 times
	private static final double TURN_SIGN_SPACE = 0.20; //used once
	
	@FXML
	private Rectangle border;
	@FXML
	private Rectangle bg;
	@FXML
	private VBox container;
	@FXML
	private BattleCharacterOverview characterOverviewB;
	@FXML
	private Rectangle spacingLineB;
	@FXML
	private AnchorPane turnSignSpace;
	@FXML
	private Polygon arrowUp;
	@FXML
	private Polygon arrowDown;
	@FXML
	private Rectangle spacingLineA;
	@FXML
	private BattleCharacterOverview characterOverviewA;
	
	private boolean aDisabled; //if false b should be disabled
	
	@FXML
	private void initialize() {
		bg.widthProperty().bindBidirectional(container.maxWidthProperty());
		bg.heightProperty().bindBidirectional(container.maxHeightProperty());
		bg.translateXProperty().bindBidirectional(container.translateXProperty());
		bg.translateYProperty().bindBidirectional(container.translateYProperty());
		
		turnSignSpace.minWidthProperty().bindBidirectional(turnSignSpace.maxWidthProperty());
		turnSignSpace.minHeightProperty().bindBidirectional(turnSignSpace.maxHeightProperty());
		
		border.setFill(ColorLoader.loadColor(ColorConfiguration.BATTLE_CHARACTER_BORDER));
		bg.setFill(ColorLoader.loadColor(ColorConfiguration.BATTLE_CHARACTER_BG));

		characterOverviewB.setStroke(ColorLoader.loadColor(ColorConfiguration.BATTLE_SECOND_CHAR_STROKE));
		characterOverviewB.setWeaponSelectionBg(ColorLoader.loadColor(ColorConfiguration.BATTLE_CHARACTER_BG));
		characterOverviewB.setHealthBarFill(ColorLoader.loadColor(ColorConfiguration.BATTLE_SECOND_CHAR_HEALTH_MISSING));
		characterOverviewB.setCurrentHealthFill(ColorLoader.loadColor(ColorConfiguration.BATTLE_SECOND_CHAR_HEALTH));
		characterOverviewB.setAttackButtonFill(ColorLoader.loadColor(ColorConfiguration.BATTLE_SECOND_CHAR_ATTACK));
		characterOverviewB.setHitLogTextFill(ColorLoader.loadColor(ColorConfiguration.BATTLE_SECOND_CHAR_DMG_LOG));
		
		spacingLineB.setFill(ColorLoader.loadColor(ColorConfiguration.BATTLE_CHARACTER_SPACING));
		
		arrowUp.setFill(ColorLoader.loadColor(ColorConfiguration.BATTLE_CHARACTER_ATTACK_UP));
		arrowDown.setFill(ColorLoader.loadColor(ColorConfiguration.BATTLE_CHARACTER_ATTACK_DOWN));
		
		spacingLineA.setFill(ColorLoader.loadColor(ColorConfiguration.BATTLE_CHARACTER_SPACING));
		
		characterOverviewA.setStroke(ColorLoader.loadColor(ColorConfiguration.BATTLE_MAIN_CHAR_STROKE));
		characterOverviewA.setWeaponSelectionBg(ColorLoader.loadColor(ColorConfiguration.BATTLE_CHARACTER_BG));
		characterOverviewA.setHealthBarFill(ColorLoader.loadColor(ColorConfiguration.BATTLE_MAIN_CHAR_HEALTH_MISSING));
		characterOverviewA.setCurrentHealthFill(ColorLoader.loadColor(ColorConfiguration.BATTLE_MAIN_CHAR_HEALTH));
		characterOverviewA.setAttackButtonFill(ColorLoader.loadColor(ColorConfiguration.BATTLE_MAIN_CHAR_ATTACK));
		characterOverviewA.setHitLogTextFill(ColorLoader.loadColor(ColorConfiguration.BATTLE_MAIN_CHAR_DMG_LOG));
		
		characterOverviewB.setCharacter((Character) GameState.get().getCurrentBattle().getFightable());
		characterOverviewA.setCharacter(GameState.get().getCurrentBattle().getCharacter());
		
		aDisabled = false;
		arrowUp.setVisible(!aDisabled);
		arrowDown.setVisible(aDisabled);
		
		if (GameState.get().getCurrentBattle().getFightable() == GameState.get().getCurrentBattle().getFirstFighterToRoll()) {
			changeTurn();
		}
	}
	
	private String getDmgLine(int dmg) {
		if (dmg == 0) {
			return "Miss!";
		} else {
			return "Hit for " + dmg + " Damage!";
		}
	}
	
	private void changeTurn() {
		aDisabled = !aDisabled;
		arrowUp.setVisible(!aDisabled);
		arrowDown.setVisible(aDisabled);
	}
	
	@FXML
	private void attackByA(MouseEvent me) {
		if (!aDisabled) {
			RoundResults rRes = GameState.get().getCurrentBattle().characterAttacksFightable();
			characterOverviewA.addNewLineToHitLog(getDmgLine(rRes.getInflictedDamage()));
			if (rRes.isBattleFinished()) {
				WindowHandler.get().closeHighestInMainStack();
			}
			characterOverviewB.updateHealthBar();
			
			changeTurn();
		}
	}
	
	@FXML
	private void attackByB(MouseEvent me) {
		if (aDisabled) {
			RoundResults rRes = GameState.get().getCurrentBattle().fightableAttacksCharacter();
			characterOverviewB.addNewLineToHitLog(getDmgLine(rRes.getInflictedDamage()));
			if (rRes.isBattleFinished()) {
				WindowHandler.get().closeHighestInMainStack();
			}
			characterOverviewA.updateHealthBar();

			changeTurn();
		}
	}

	@Override
	public void resize(double width, double height) {
		double containerWidth = width * WIDTH_FACTOR;
		double containerHeight = height * HEIGHT_FACTOR;
		
		border.setWidth(width);
		border.setHeight(height);
		
		bg.setWidth(containerWidth);
		bg.setHeight(containerHeight);
		bg.setTranslateX((width - containerWidth)/2);
		bg.setTranslateY((height - containerHeight)/2);
		
		characterOverviewB.setMaxWidth(containerWidth);
		characterOverviewB.setMaxHeight(containerHeight*CHARACTER_SPACE);
		spacingLineB.setWidth(containerWidth);
		spacingLineB.setHeight(containerHeight*INBETWEEN_SPACE);
		resizeTurnSignSpace(containerWidth, containerHeight*TURN_SIGN_SPACE);
		spacingLineA.setWidth(containerWidth);
		spacingLineA.setHeight(containerHeight*INBETWEEN_SPACE);
		characterOverviewA.setMaxWidth(containerWidth);
		characterOverviewA.setMaxHeight(containerHeight*CHARACTER_SPACE);
	}

	private void resizeTurnSignSpace(double turnsignSpaceWidth, double turnsignSpaceHeight) {
		turnSignSpace.setMinWidth(turnsignSpaceWidth);
		turnSignSpace.setMinHeight(turnsignSpaceHeight);
		
		arrowUp.setScaleX(turnsignSpaceWidth);
		arrowUp.setScaleY(turnsignSpaceHeight);
		arrowUp.setTranslateX(turnsignSpaceWidth/2);
		arrowUp.setTranslateY(turnsignSpaceHeight/2);
		
		arrowDown.setScaleX(turnsignSpaceWidth);
		arrowDown.setScaleY(turnsignSpaceHeight);
		arrowDown.setTranslateX(turnsignSpaceWidth/2);
		arrowDown.setTranslateY(turnsignSpaceHeight/2);
	}
}
