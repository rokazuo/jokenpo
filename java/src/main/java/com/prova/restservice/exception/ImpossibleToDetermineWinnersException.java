package com.prova.restservice.exception;

public class ImpossibleToDetermineWinnersException extends RuntimeException {

	private static final long serialVersionUID = 5708061201637269881L;

	public ImpossibleToDetermineWinnersException() {
		super("Impossible to determine the winner");
	}

}
