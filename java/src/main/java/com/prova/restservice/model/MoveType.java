package com.prova.restservice.model;

public enum MoveType {
	Rock("Rock"), Scissor("Scissor"), Paper("Papel"), Invalid("Invalid");

	private String moveType;

	private MoveType(String moveType) {
		this.setMoveType(moveType);
	}

	public String getMoveType() {
		return moveType;
	}

	public void setMoveType(String moveType) {
		this.moveType = moveType;
	}

}