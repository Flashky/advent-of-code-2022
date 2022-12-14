package com.adventofcode.flashk.day14;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.adventofcode.flashk.common.Vector2;

import lombok.Getter;

public class Path {
	
	private final static String POS_SEPARATOR = " -> ";
	
	@Getter
	private List<Vector2> points;
	
	public Path(String input) {
		
		String[] positions = input.split(POS_SEPARATOR);
		points = Arrays.stream(positions).map(Vector2::new).collect(Collectors.toList());

	}
	
}
