package ch.zhaw.students.adgame.ui.event;

import javafx.scene.input.MouseEvent;

/**
 * MouseEvent with additional information stored.
 */
public class AdditionalInfoMouseEvent<T> extends MouseEvent {
	private static final long serialVersionUID = 1L;

	private T additionalInformation;
	
	/**
	 * Creates a new {@link MouseEvent} with the same information as the given original.
	 * Also stores some additional information.<br>
	 * Makes a copy of the original, does not consist of the same references.<br>
	 * <br>
	 * <b>Attention</b>: does not preserve information of subclasses of {@link MouseEvent}
	 */
	public AdditionalInfoMouseEvent(MouseEvent originalMouseEvent, T additionalInformation) {
		super(originalMouseEvent.getSource(), originalMouseEvent.getTarget(), originalMouseEvent.getEventType(), originalMouseEvent.getX(), originalMouseEvent.getY(), originalMouseEvent.getScreenX(), originalMouseEvent.getScreenY(), originalMouseEvent.getButton(), originalMouseEvent.getClickCount(), originalMouseEvent.isShiftDown(), originalMouseEvent.isControlDown(), originalMouseEvent.isAltDown(), originalMouseEvent.isMetaDown(), originalMouseEvent.isPrimaryButtonDown(), originalMouseEvent.isMiddleButtonDown(), originalMouseEvent.isSecondaryButtonDown(), originalMouseEvent.isSynthesized(), originalMouseEvent.isPopupTrigger(), originalMouseEvent.isStillSincePress(), originalMouseEvent.getPickResult());
		this.additionalInformation = additionalInformation;
	}
	
	public T getAdditionalInformation() {
		return additionalInformation;
	}
	
	public void setAdditionalInformation(T additionalInformation) {
		this.additionalInformation = additionalInformation;
	}
}
