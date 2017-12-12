package ch.zhaw.students.adgame.ui.window;

import ch.zhaw.students.adgame.configuration.ColorConfiguration;
import ch.zhaw.students.adgame.domain.GameState;
import ch.zhaw.students.adgame.domain.event.RoundResults;
import ch.zhaw.students.adgame.resource.ColorLoader;
import ch.zhaw.students.adgame.ui.ResizableUI;
import ch.zhaw.students.adgame.ui.WindowHandler;
import ch.zhaw.students.adgame.ui.component.BattleCharacterOverview;
import ch.zhaw.students.adgame.ui.component.BattleFightableOverview;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

/**
 * JavaFx controller for FightableBattle.fxml
 */
public class FightableBattleController implements ResizableUI {
	private static final double HEIGHT_FACTOR = 0.95;
	private static final double WIDTH_FACTOR = 0.95;
	
	private static final double FIGHTABLE_SPACE = 0.59;
	private static final double INBETWEEN_SPACE = 0.01;
	private static final double CHARACTER_SPACE = 0.40;
	
	@FXML
	private Rectangle border;
	@FXML
	private Rectangle bg;
	@FXML
	private VBox container;
	@FXML
	private BattleFightableOverview fightableOverview;
	@FXML
	private Rectangle spacingLine;
	@FXML
	private BattleCharacterOverview characterOverview;
	
	@FXML
	private void initialize() {
		bg.heightProperty().bindBidirectional(container.maxHeightProperty());
		bg.widthProperty().bindBidirectional(container.maxWidthProperty());
		bg.translateXProperty().bindBidirectional(container.translateXProperty());
		bg.translateYProperty().bindBidirectional(container.translateYProperty());
		
		border.setFill(ColorLoader.loadColor(ColorConfiguration.BATTLE_FIGHTABLE_BORDER));
		bg.setFill(ColorLoader.loadColor(ColorConfiguration.BATTLE_FIGHTABLE_BG));

		fightableOverview.setStroke(ColorLoader.loadColor(ColorConfiguration.BATTLE_ENEMY_STROKE));
		fightableOverview.setHealthBarFill(ColorLoader.loadColor(ColorConfiguration.BATTLE_ENEMY_HEALTH_MISSING));
		fightableOverview.setCurrentHealthFill(ColorLoader.loadColor(ColorConfiguration.BATTLE_ENEMY_HEALTH));
		fightableOverview.setHitLogTextFill(ColorLoader.loadColor(ColorConfiguration.BATTLE_ENEMY_DMG_LOG));
		
		spacingLine.setFill(ColorLoader.loadColor(ColorConfiguration.BATTLE_FIGHTABLE_SPACING));
		
		characterOverview.setStroke(ColorLoader.loadColor(ColorConfiguration.BATTLE_MAIN_CHAR_STROKE));
		characterOverview.setWeaponSelectionBg(ColorLoader.loadColor(ColorConfiguration.BATTLE_FIGHTABLE_BG));
		characterOverview.setHealthBarFill(ColorLoader.loadColor(ColorConfiguration.BATTLE_MAIN_CHAR_HEALTH_MISSING));
		characterOverview.setCurrentHealthFill(ColorLoader.loadColor(ColorConfiguration.BATTLE_MAIN_CHAR_HEALTH));
		characterOverview.setAttackButtonFill(ColorLoader.loadColor(ColorConfiguration.BATTLE_MAIN_CHAR_ATTACK));
		characterOverview.setHitLogTextFill(ColorLoader.loadColor(ColorConfiguration.BATTLE_MAIN_CHAR_DMG_LOG));
		
		fightableOverview.setFightable(GameState.get().getCurrentBattle().getFightable());
		characterOverview.setCharacter(GameState.get().getCurrentBattle().getCharacter());
		
		if (GameState.get().getCurrentBattle().getFightable() == GameState.get().getCurrentBattle().getFirstFighterToRoll()) {
			fightableAttack();
		}
	}
	
	private String getDmgLine(int dmg) {
		if (dmg == 0) {
			return "Miss!";
		} else {
			return "Hit for " + dmg + " Damage!";
		}
	}
	
	private void characterAttack() {
		RoundResults rRes = GameState.get().getCurrentBattle().characterAttacksFightable();
		characterOverview.addNewLineToHitLog(getDmgLine(rRes.getInflictedDamage()));
		if (rRes.isBattleFinished()) {
			WindowHandler.get().closeHighestInMainStack();
		}
		fightableOverview.updateHealthBar();
	}
	
	private void fightableAttack() {
		RoundResults rRes = GameState.get().getCurrentBattle().fightableAttacksCharacter();
		fightableOverview.addNewLineToHitLog(getDmgLine(rRes.getInflictedDamage()));
		if (rRes.isBattleFinished()) {
			WindowHandler.get().closeHighestInMainStack();
		}
		characterOverview.updateHealthBar();
	}
	
	@FXML
	private void attack(MouseEvent me) {
		characterAttack();
		fightableAttack();
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
		fightableOverview.setMaxWidth(containerWidth);
		fightableOverview.setMaxHeight(containerHeight*FIGHTABLE_SPACE);
		spacingLine.setWidth(containerWidth);
		spacingLine.setHeight(containerHeight*INBETWEEN_SPACE);
		characterOverview.setMaxWidth(containerWidth);
		characterOverview.setMaxHeight(containerHeight*CHARACTER_SPACE);
	}
}
