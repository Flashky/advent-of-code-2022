package com.adventofcode.flashk.day09;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RopeBridge {

	private List<Movement> movements;

	public RopeBridge(List<String> inputs) {
		
		// Initialize list of movements
		movements = inputs.stream().map(Movement::new).collect(Collectors.toList());
		
	}

	public long solve(int numberOfKnots) {
		
		// Apply all movements and recollect all tail coordinates
		Rope rope = new Rope(numberOfKnots);
		return movements.stream().map(rope::move).flatMap(Set::stream).collect(Collectors.toSet()).size();
		
	}
}
