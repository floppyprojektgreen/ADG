package ch.zhaw.students.adgame.domain.event;

import ch.zhaw.students.adgame.configuration.Texture;
import ch.zhaw.students.adgame.domain.entity.Character;

/**
 * This interface is used to trigger various events.
 */
public interface Invokable {
	void invoke(Character character);

	/**
	 * Get the corresponding texture key.
	 */
	public Texture getTextureKey();
}
