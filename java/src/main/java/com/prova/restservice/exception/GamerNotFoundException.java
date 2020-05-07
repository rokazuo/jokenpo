package com.prova.restservice.exception;

public class GamerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3200587156179943878L;

	public GamerNotFoundException(String name) {
		super("Gamer not found for " + name);
	}

}