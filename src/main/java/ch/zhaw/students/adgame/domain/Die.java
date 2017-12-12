package ch.zhaw.students.adgame.domain;

import java.util.concurrent.ThreadLocalRandom;

/**
 * This class is the representation of a die. It has methods to roll a number.
 * One can choose which kind of die they want to roll.
 */
public enum Die {
	D4(4),
	D6(6),
	D8(8),
	D10(10),
	D12(12),
	D20(20),
	D100(100);

	private int maxRoll;

	private Die(int maxRoll) {
		this.maxRoll = maxRoll;
	}

	/**
	 * Method to roll a die. Called like Die.roll(Die.D6)
	 */
	public static int roll(Die die) {
		return ThreadLocalRandom.current().nextInt(1, die.maxRoll + 1);
	}

}
