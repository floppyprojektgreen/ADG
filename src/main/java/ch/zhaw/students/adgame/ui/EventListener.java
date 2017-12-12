package ch.zhaw.students.adgame.ui;

import ch.zhaw.students.adgame.domain.ChangeEvent;

/**
 * Interface for UI that wants to listen to {@link ChangeEvent} that
 * are not already processed by the window handler.
 */
@FunctionalInterface
public interface EventListener {
	/**
	 * Method that should be called on the event
	 */
	public void onChangeEvent(ChangeEvent event);
}
