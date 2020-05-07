package com.prova.restservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GamerNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(GamerNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String gamerNotFoundHandler(GamerNotFoundException ex) {
		return ex.getMessage();
	}

}