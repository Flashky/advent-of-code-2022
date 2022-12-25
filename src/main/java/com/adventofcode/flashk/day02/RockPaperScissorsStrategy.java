package com.adventofcode.flashk.day02;

import java.util.List;
import java.util.stream.Collectors;

public abstract class RockPaperScissorsStrategy {


	private List<Round> rounds;
	
	public RockPaperScissorsStrategy(List<String> inputs) {
		rounds = inputs.stream().map(Round::new).collect(Collectors.toList());
	}
	
	public int solve() {
		
		int[][] resultMatrix = initializeResultMatrix();
		
		int score = 0;

		for(Round round : rounds) {
			score += resultMatrix[round.getElve()][round.getPlayer()];
		}

		return score;
		
	}
	
	abstract int[][] initializeResultMatrix();
}
