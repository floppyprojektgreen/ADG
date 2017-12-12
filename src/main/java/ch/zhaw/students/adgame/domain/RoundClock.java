package ch.zhaw.students.adgame.domain;


import ch.zhaw.students.adgame.domain.board.FieldType;
import ch.zhaw.students.adgame.domain.event.RandomBenefit;

/**
 * public class RoundClock A singleton. Only instantiated once.
 */
public class RoundClock {
	private static RoundClock instance = null;
	private int rounds = 1;

	private RoundClock() {
	}

	public static RoundClock get() {
		if (instance == null) {
			instance = new RoundClock();
		}
		return instance;
	}

	/**
	 * This methods increases the number of rounds by 1 and places a random benefit every three rounds.
	 */
	public void incrementRounds() {
		rounds++;
		placeRandomBenefit();
	}

	public int getRounds() {
		return rounds;
	}
	
	public void setRounds(int rounds) {
		this.rounds = rounds;
	}

	/**
	 * Places a random benefit on a field every three rounds.
	 */
	public void placeRandomBenefit() {
		if (this.rounds % 3 == 0) {
			GameState.get().placeRandomField(createRandomBenefit());
		}
	}

	private FieldType createRandomBenefit() {
		return new FieldType(new RandomBenefit(), "Field chest", 0);
	}
}
