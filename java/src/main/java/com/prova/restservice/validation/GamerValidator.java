package com.prova.restservice.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.prova.restservice.model.Gamer;
import com.prova.restservice.model.MoveType;

public class GamerValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Gamer.class.equals(clazz);
	}

	@Override
	public void validate(Object gamerObj, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");
		Gamer gamer = (Gamer) gamerObj;
		if (!MoveType.Rock.equals(gamer.getMove()) && !MoveType.Scissor.equals(gamer.getMove())
				&& !MoveType.Paper.equals(gamer.getMove())) {
			errors.rejectValue("move", "invalid");
		}
	}

}