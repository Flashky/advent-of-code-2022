package com.adventofcode.flashk.day02;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class RockPaperScissorsStrategy {

	public final static String ROUND_PATTERN = "(A|B|C) (X|Y|Z)";
	private final static Pattern PATTERN = Pattern.compile(ROUND_PATTERN);

	private List<Round> rounds = new ArrayList<>();
	
	public RockPaperScissorsStrategy(List<String> inputs) {
		
		Map<String, Integer> elvePlayIndex = new HashMap<>();
		Map<String, Integer> playerPlayIndex = new HashMap<>();
		
		// Round hands to index translation
		elvePlayIndex.put("A", 0);
		elvePlayIndex.put("B", 1);
		elvePlayIndex.put("C", 2);
		playerPlayIndex.put("X", 0);
		playerPlayIndex.put("Y", 1);
		playerPlayIndex.put("Z", 2);
		
		for(String round : inputs) {
			
			Matcher matcher = PATTERN.matcher(round);
			matcher.find();

			rounds.add(new Round(elvePlayIndex.get(matcher.group(1)), 
								playerPlayIndex.get(matcher.group(2))));
			
		}

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
