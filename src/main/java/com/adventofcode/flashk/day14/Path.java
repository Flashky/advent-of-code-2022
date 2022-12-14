package com.adventofcode.flashk.day14;

import java.util.ArrayList;
import java.util.List;

import com.adventofcode.flashk.common.Vector2;

import lombok.Getter;

public class Path {
	
	private final static String POS_SEPARATOR = " -> ";
	private final static String COORDINATE_SEPARATOR = ",";
	
	@Getter
	private List<Vector2> points = new ArrayList<>();
	
	public Path(String input) {
		
		String[] positions = input.split(POS_SEPARATOR);
		
		for(int i = 0; i < positions.length; i++) {
			
			String[] coordinates = positions[i].split(COORDINATE_SEPARATOR);
			
			int x = Integer.parseInt(coordinates[0]);
			int y = Integer.parseInt(coordinates[1]);
			
			points.add(new Vector2(x,y));
			
		}

	}
}
