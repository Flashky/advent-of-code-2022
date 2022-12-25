package com.adventofcode.flashk.day02;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Round {
	
	public final static String ROUND_PATTERN = "(A|B|C) (X|Y|Z)";
	private final static Pattern PATTERN = Pattern.compile(ROUND_PATTERN);

	private static final Map<String, Integer> elvePlayIndex = 
			Map.of( "A", 0,
					"B", 1,
					"C", 2);
	
	private static final Map<String, Integer> playerPlayIndex =
			Map.of("X", 0,
					"Y", 1,
					"Z", 2);
	
	private int elve;
	private int player;
	
	public Round(String input) {
		Matcher matcher = PATTERN.matcher(input);
		matcher.find();
		
		elve = elvePlayIndex.get(matcher.group(1));
		player = playerPlayIndex.get(matcher.group(2));
		
	}
	
}
