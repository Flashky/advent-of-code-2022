package com.adventofcode.flashk.day09;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.adventofcode.flashk.common.Vector2;

import lombok.Getter;

@Getter
public class Movement {
	
	private final static String MOVEMENT_REGEX = "(R|L|U|D) ([0-9]*)";
	private final static Pattern MOVEMENT_PATTERN = Pattern.compile(MOVEMENT_REGEX);
	
	private Vector2 direction;
	private int steps;
	
	public Movement(String input) {
		Matcher matcher = MOVEMENT_PATTERN.matcher(input);
		
		matcher.find();
		
		switch(matcher.group(1)) {
			case "R": direction = new Vector2(1,0); break;
			case "L": direction = new Vector2(-1,0); break;
			case "U": direction = new Vector2(0,1); break;
			case "D": direction = new Vector2(0,-1); break;
		}
		
		steps = Integer.parseInt(matcher.group(2));
	}

}
