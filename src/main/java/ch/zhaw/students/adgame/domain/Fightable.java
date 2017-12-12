package ch.zhaw.students.adgame.domain;

import ch.zhaw.students.adgame.configuration.ResourcesConfiguration;

/**
 * This interface is used for the fighting system. The participants of a battle
 * have the type fightable. It has getter-methods for important battle stats and
 * a method to attack as well as to reduce hit points of itself.
 */
public interface Fightable {
	public void resetHitPoints();

	public int getMaxHitPoints();

	public int getHitPoints();

	public int getDefense();

	public int getStrength();

	public int getAccuracy();

	public int attack(Fightable enemy);

	public ResourcesConfiguration getTextureKey();

	public void reduceHitPoints(int damage);
}
