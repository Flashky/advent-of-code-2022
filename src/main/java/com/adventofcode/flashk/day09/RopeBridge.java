package com.adventofcode.flashk.day09;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.adventofcode.flashk.common.Vector2;

public class RopeBridge {

	private List<Movement> movements;

	public RopeBridge(List<String> inputs) {
		
		// Initialize list of movements
		movements = inputs.stream().map(Movement::new).collect(Collectors.toList());
		
	}

	public long solve(int numberOfKnots) {
		
		Set<Vector2> passedCoordinates = new HashSet<>();
		Rope rope = new Rope(numberOfKnots);
		
		// Apply all movements and recollect all tail coordinates
		movements.stream().map(rope::move).forEach(passedCoordinates::addAll);

		return passedCoordinates.size();
	}
	

}
