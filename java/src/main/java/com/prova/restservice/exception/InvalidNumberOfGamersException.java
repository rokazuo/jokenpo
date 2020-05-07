package com.prova.restservice.exception;

public class InvalidNumberOfGamersException extends RuntimeException {

	private static final long serialVersionUID = 6970157759088980095L;

	public InvalidNumberOfGamersException() {
		super("Invalid number of gamers");
	}

}