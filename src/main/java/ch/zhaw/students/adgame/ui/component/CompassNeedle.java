package ch.zhaw.students.adgame.ui.component;

import ch.zhaw.students.adgame.domain.board.CardinalDirection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.util.Duration;

/**
 * Animation for a spinning compass needle.
 */
public class CompassNeedle {
	private DoubleProperty angle;
	private int wholeSpins;
	private Timeline timeline;
	
	private int spinsDone;
	private CardinalDirection stopAt;
	private Runnable finishAction;
	
	/**
	 * Creates a new timeline for the animation, based on duration and number of spins to do before stoping.
	 */
	public CompassNeedle(long durationOneSpin, int wholeSpins) {
		this.wholeSpins = wholeSpins;
		angle = new SimpleDoubleProperty(0);
		
		timeline = new Timeline(
				new KeyFrame(Duration.ZERO, ae -> spinsDone++, new KeyValue(angle, 0)),
				new KeyFrame(Duration.millis(durationOneSpin/6), ae -> checkStop(CardinalDirection.NORTH_EAST), new KeyValue(angle, 60)),
				new KeyFrame(Duration.millis(durationOneSpin/3), ae -> checkStop(CardinalDirection.SOUTH_EAST), new KeyValue(angle, 120)),
				new KeyFrame(Duration.millis(durationOneSpin/2), ae -> checkStop(CardinalDirection.SOUTH), new KeyValue(angle, 180)),
				new KeyFrame(Duration.millis(durationOneSpin/6*4), ae -> checkStop(CardinalDirection.SOUTH_WEST), new KeyValue(angle, 240)),
				new KeyFrame(Duration.millis(durationOneSpin/6*5), ae -> checkStop(CardinalDirection.NORTH_WEST), new KeyValue(angle, 300)),
				new KeyFrame(Duration.millis(durationOneSpin), ae -> checkStop(CardinalDirection.NORTH), new KeyValue(angle, 360)));
		timeline.setCycleCount(Animation.INDEFINITE);
	}
	
	private void checkStop(CardinalDirection dir) {
		if (spinsDone == wholeSpins && stopAt == dir) {
			timeline.pause();
			finishAction.run();
			finishAction = null;
		}
	}
	
	/**
	 * If not currently running, starts the spinning process,
	 * sets the direction to stop in and the action to be executed on finishing.
	 */
	public boolean startAndStopAt(CardinalDirection direction, Runnable finish) {
		if (finishAction != null) {
			return false;
		}
		finishAction = finish;
		
		stopAt = direction;
		spinsDone = 0;
		
		timeline.play();
		return true;
	}
	
	public double getAngle() {
		return angle.get();
	}
}
