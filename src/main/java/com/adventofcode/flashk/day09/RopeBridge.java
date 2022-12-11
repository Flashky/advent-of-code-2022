package com.adventofcode.flashk.day09;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.adventofcode.flashk.common.Vector2;

public class RopeBridge {

	private Set<Vector2> passedCoordinates = new HashSet<>();
	private List<Movement> movements = new ArrayList<>();

	public RopeBridge(List<String> inputs) {
		
		for(String input : inputs) {
			movements.add(new Movement(input));
		}
		
		passedCoordinates.add(new Vector2(0,0));
		
	}

	public long solve(int numberOfKnots) {
		
		Rope rope = new Rope(numberOfKnots);
		for(Movement movement : movements) {
			Set<Vector2> tailPositions = rope.move(movement);
			passedCoordinates.addAll(tailPositions);
		}
		
		return passedCoordinates.size();
	}
	

}
