package com.prova.restservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.prova.restservice.model.Gamer;
import com.prova.restservice.model.MoveType;

public class PlayServiceTests {

	@Test
	public void impossibleToDetermineTheWinner() {
		List<Gamer> gamerList = new ArrayList<>();
		gamerList.add(new Gamer("Joao", MoveType.Paper));
		gamerList.add(new Gamer("Maria", MoveType.Scissor));
		gamerList.add(new Gamer("Pedro", MoveType.Rock));
		assertEquals(0, PlayService.determineWinners(gamerList).size());
	}

	@Test
	public void scissorWinner() {
		List<Gamer> gamerList = new ArrayList<>();
		List<Gamer> expectedWinnerList = new ArrayList<>();
		Gamer winner = null;
		gamerList.add(new Gamer("Joao", MoveType.Paper));
		winner = new Gamer("Maria", MoveType.Scissor);
		gamerList.add(winner);
		expectedWinnerList.add(winner);
		gamerList.add(new Gamer("Joao", MoveType.Paper));
		winner = new Gamer("Antonio", MoveType.Scissor);
		gamerList.add(winner);
		expectedWinnerList.add(winner);
		assertEquals(expectedWinnerList, PlayService.determineWinners(gamerList));
	}

	@Test
	public void paperWinner() {
		List<Gamer> gamerList = new ArrayList<>();
		List<Gamer> expectedWinnerList = new ArrayList<>();
		Gamer winner = null;
		gamerList.add(new Gamer("Joao", MoveType.Rock));
		winner = new Gamer("Maria", MoveType.Paper);
		gamerList.add(winner);
		expectedWinnerList.add(winner);
		gamerList.add(new Gamer("Joao", MoveType.Rock));
		winner = new Gamer("Antonio", MoveType.Paper);
		gamerList.add(winner);
		expectedWinnerList.add(winner);
		assertEquals(expectedWinnerList, PlayService.determineWinners(gamerList));
	}

	@Test
	public void rockWinner() {
		List<Gamer> gamerList = new ArrayList<>();
		List<Gamer> expectedWinnerList = new ArrayList<>();
		Gamer winner = null;
		gamerList.add(new Gamer("Joao", MoveType.Scissor));
		winner = new Gamer("Maria", MoveType.Rock);
		gamerList.add(winner);
		expectedWinnerList.add(winner);
		gamerList.add(new Gamer("Joao", MoveType.Scissor));
		winner = new Gamer("Antonio", MoveType.Rock);
		gamerList.add(winner);
		expectedWinnerList.add(winner);
		assertEquals(expectedWinnerList, PlayService.determineWinners(gamerList));
	}

}
