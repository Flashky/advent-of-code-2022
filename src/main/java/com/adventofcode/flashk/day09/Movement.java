package com.adventofcode.flashk.day09;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.adventofcode.flashk.common.Vector2;

import lombok.Getter;

@Getter
public class Movement {
	
	private final static String MOVEMENT_REGEX = "(R|L|U|D) ([0-9]*)";
	private final static Pattern MOVEMENT_PATTERN = Pattern.compile(MOVEMENT_REGEX);
	
	private final static String RIGHT = "R";
	private final static String LEFT = "L";
	private final static String UP = "U";
	private final static String DOWN = "D";
	
	private Vector2 direction;
	private int steps;
	
	public Movement(String input) {
		Matcher matcher = MOVEMENT_PATTERN.matcher(input);
		
		matcher.find();
		
		switch(matcher.group(1)) {
			case RIGHT: direction = Vector2.right(); break;
			case LEFT: direction = Vector2.left(); break;
			case UP: direction = Vector2.up(); break;
			case DOWN: direction = Vector2.down(); break;
			default:
				throw new UnsupportedOperationException("Unsupported movement type: "+matcher.group(1));
		}
		
		steps = Integer.parseInt(matcher.group(2));
	}

}
