package ch.zhaw.students.adgame.domain.event;

/**
 * This class is used to provide stats of a single round from a battle. It
 * stores the amount of inflicted damage and if the battle is finished.
 */
public class RoundResults {
	private int inflictedDamage;
	private boolean isBattleFinished;

	public RoundResults(int inflictedDamage, boolean isBattleFinished) {
		this.inflictedDamage = inflictedDamage;
		this.isBattleFinished = isBattleFinished;
	}

	public int getInflictedDamage() {
		return inflictedDamage;
	}

	public boolean isBattleFinished() {
		return isBattleFinished;
	}
}
