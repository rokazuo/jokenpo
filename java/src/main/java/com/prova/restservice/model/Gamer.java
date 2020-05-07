package com.prova.restservice.model;

public class Gamer implements Comparable<Gamer> {

	private String name;
	private MoveType move;

	public Gamer() {
	}

	public Gamer(String name, MoveType move) {
		this.name = name;
		this.move = move;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MoveType getMove() {
		return move;
	}

	public void setMove(MoveType move) {
		this.move = move;
	}

	@Override
	public boolean equals(Object otherObject) {
		if (otherObject == this) {
			return true;
		}
		if (!(otherObject instanceof Gamer)) {
			return false;
		}
		Gamer otherGamer = (Gamer) otherObject;

		return this.name.equals(otherGamer.getName()) && this.move.equals(otherGamer.getMove());
	}

	@Override
	public int compareTo(Gamer otherGamer) {
		int returnValue = 0;
		switch (this.move) {
		case Paper:
			if (otherGamer.getMove().equals(MoveType.Paper)) {
				returnValue = 0;
			} else if (otherGamer.getMove().equals(MoveType.Scissor)) {
				returnValue = -1;
			} else if (otherGamer.getMove().equals(MoveType.Rock)) {
				returnValue = 1;
			}
			break;
		case Scissor:
			if (otherGamer.getMove().equals(MoveType.Scissor)) {
				returnValue = 0;
			} else if (otherGamer.getMove().equals(MoveType.Paper)) {
				returnValue = 1;
			} else if (otherGamer.getMove().equals(MoveType.Rock)) {
				returnValue = -1;
			}
			break;
		case Rock:
			if (otherGamer.getMove().equals(MoveType.Rock)) {
				returnValue = 0;
			} else if (otherGamer.getMove().equals(MoveType.Scissor)) {
				returnValue = 1;
			} else if (otherGamer.getMove().equals(MoveType.Paper)) {
				returnValue = -1;
			}
			break;
		case Invalid:
			break;
		default:
			break;
		}
		return returnValue;
	}

}