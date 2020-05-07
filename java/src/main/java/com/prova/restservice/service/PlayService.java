package com.prova.restservice.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.prova.restservice.model.Gamer;
import com.prova.restservice.model.MoveType;

public class PlayService {

	public static List<Gamer> determineWinners(List<Gamer> gamerList) {
		List<Gamer> winnerList = new ArrayList<>();
		List<MoveType> moveList = new ArrayList<>();
		MoveType winnerMove = MoveType.Invalid;
		for (Gamer gamer : gamerList) {
			if (!moveList.contains(gamer.getMove())) {
				moveList.add(gamer.getMove());
			}
		}
		if (moveList.size() < 3) {
			Collections.sort(gamerList, Comparator.reverseOrder());
			winnerMove = gamerList.get(0).getMove();
			for (Gamer gamer : gamerList) {
				if (winnerMove.equals(gamer.getMove())) {
					winnerList.add(gamer);
				}
			}
		}
		return winnerList;
	}

}