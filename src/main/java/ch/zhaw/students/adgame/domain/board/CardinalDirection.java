package ch.zhaw.students.adgame.domain.board;

/**
 * This class maps cardinal directions to a hexagon.
 */
public enum CardinalDirection {
	NORTH(1),
	NORTH_EAST(2),
	SOUTH_EAST(3),
	SOUTH(4),
	SOUTH_WEST(5),
	NORTH_WEST(6),
	NO_DIRECTION(-1);

	private int rolledNumber;

	private CardinalDirection(int rolledNumber) {
		this.rolledNumber = rolledNumber;
	}

	/**
	 * This method returns a cardinal direction based on the number passed as a parameter.
	 * If the number is not from 1 to 6, this method will return null.
	 * 1: North
	 * 2: North East
	 * 3: South East
	 * 4: South
	 * 5: South West
	 * 6: North West
	 */
	public static CardinalDirection getDirection(int rolledNumber) {
		for (CardinalDirection cardinalDirection : values()) {
			if (cardinalDirection.rolledNumber == rolledNumber) {
				return cardinalDirection;
			}
		}
		return null;
	}
	
	/**
	 * This method returns the opposite direction of the direction passed as a parameter.
	 */
	public static CardinalDirection getOppositeDirection(CardinalDirection direction) {
		CardinalDirection oppositeDirection = NO_DIRECTION;
		switch (direction) {
		
		case NO_DIRECTION:
			break;
		default:
			break;
			
		case NORTH:
			oppositeDirection = SOUTH;
			break;
			
		case NORTH_EAST:
			oppositeDirection = SOUTH_WEST;
			break;
			
		case SOUTH_EAST:
			oppositeDirection = NORTH_WEST;
			break;
		
		case SOUTH:
			oppositeDirection = NORTH;
			break;
		
		case SOUTH_WEST:
			oppositeDirection = NORTH_EAST;
			break;
			
		case NORTH_WEST:
			oppositeDirection = SOUTH_EAST;
			break;
		}
		
		return oppositeDirection;
	}
}
