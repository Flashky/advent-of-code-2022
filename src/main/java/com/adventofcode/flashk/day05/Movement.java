package com.adventofcode.flashk.day05;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;

@Getter
public class Movement {

	public final static String MOVEMENT_PATTERN = "move ([0-9]*) from ([0-9]) to ([0-9])";
	private final static Pattern PATTERN = Pattern.compile(MOVEMENT_PATTERN);
	
	private int numberOfCrates;
	private int stackIndexFrom;
	private int stackIndexTo;
	
	public Movement(String instruction) {
		Matcher matcher = PATTERN.matcher(instruction);
		matcher.find();
		
		numberOfCrates = Integer.parseInt(matcher.group(1)); 
		stackIndexFrom = Integer.parseInt(matcher.group(2)) - 1;
		stackIndexTo = Integer.parseInt(matcher.group(3)) - 1;

	}
	
}
