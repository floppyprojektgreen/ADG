package ch.zhaw.students.adgame.domain.board;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class is the representation of the playing field.
 */
public class Board implements Serializable {
	private static final long serialVersionUID = 8027809333450841530L;
	private Field[][] fields;

	public Board(int x, int y) {
		fields = new Field[x][y];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				fields[i][j] = new Field();
			}
		}
	}

	public Field[][] getFields() {
		return this.fields;
	}

	public Field getField(int x, int y) {
		return this.fields[x][y];
	}

	/**
	 * This method returns a randomly selected field
	 */
	public Field getRandomField() {
		int x = ThreadLocalRandom.current().nextInt(0, getWidth());
		int y = ThreadLocalRandom.current().nextInt(0, getHeight());
		return this.getField(x, y);
	}

	public int getWidth() {
		return fields.length;
	}

	public int getHeight() {
		return fields[0].length;
	}
}