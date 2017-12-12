package ch.zhaw.students.adgame.domain.board;

import java.io.Serializable;

import ch.zhaw.students.adgame.domain.event.Invokable;

/**
 * This class represents a field type. It has an event and a cost.
 */
public class FieldType implements Serializable {
	private static final long serialVersionUID = 1937251467625629988L;
	private Invokable event;
	private int cost;
	private String name;

	public FieldType(Invokable event, String name, int cost) {
		this.event = event;
		this.name = name;
		this.cost = cost;
	}

	public Invokable getEvent() {
		return event;
	}

	public String getName() {
		return name;
	}

	public int getCost() {
		return cost;
	}
}
