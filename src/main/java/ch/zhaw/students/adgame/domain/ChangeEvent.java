package ch.zhaw.students.adgame.domain;

/**
 * Game changing events. Listeners on {@link GameState} should be informed of these.
 */
public enum ChangeEvent {
	FIGHTABLE_BATTLE_STARTED,
	CHARACTER_BATTLE_STARTED,
	LANDED_ON_EMPTY_FIELD,
	FIELD_CHEST,
	TELEPORTER,
	CHARACTER_DIED;
}
