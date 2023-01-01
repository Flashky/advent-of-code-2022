package com.adventofcode.flashk.day22;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.adventofcode.flashk.common.Vector2;

public class MonkeyMapCube {

	private static final String MOVEMENT_REGEX = "(\\d*)";
	private static final Pattern MOVEMENT_PATTERN = Pattern.compile(MOVEMENT_REGEX);
	
	private static final String ROTATION_REGEX = "(R|L)";
	private static final Pattern ROTATION_PATTERN = Pattern.compile(ROTATION_REGEX);
	
	// Instructions
	private Queue<Integer> movements = new LinkedList<>();	
	private Queue<Character> rotations = new LinkedList<>();
	
	

	
	private Cube mapCube;
	
	public MonkeyMapCube(List<String> inputs, boolean isSample) {
		
		String instructions = inputs.get(inputs.size()-1);
		initializeMovements(instructions);
		initializeRotations(instructions);
		
		mapCube = new Cube(inputs, isSample);
	}

	public long solveA() {
		
		long result = 0;

		while(!movements.isEmpty()) {
			int distance = movements.poll();
			
			// Move and then rotate
			mapCube.move(distance);
			
			if(!rotations.isEmpty()) {
				Character rotation = rotations.poll();
				mapCube.rotate(rotation);
			}
			
		}
		
		return mapCube.password();
		
	}

	

	private void initializeMovements(String instructions) {
		Matcher matcher = MOVEMENT_PATTERN.matcher(instructions);
		
		while(matcher.find()) {
			String number = matcher.group(1);
			if(StringUtils.isNotBlank(number)) {
				movements.add(Integer.parseInt(number));
			}
		}
	}
	
	private void initializeRotations(String instructions) {
		
		Matcher matcher = ROTATION_PATTERN.matcher(instructions);
		
		while(matcher.find()) {
			String rotation = matcher.group(1);
			if(StringUtils.isNotBlank(rotation)) {
				rotations.add(rotation.charAt(0));
			}
		}
	}


}
