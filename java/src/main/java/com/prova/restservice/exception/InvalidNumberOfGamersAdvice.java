package com.prova.restservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InvalidNumberOfGamersAdvice {

	@ResponseBody
	@ExceptionHandler(InvalidNumberOfGamersException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	String invalidNumberOfGamersHandler(InvalidNumberOfGamersException ex) {
		return ex.getMessage();
	}

}