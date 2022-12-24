package com.adventofcode.flashk.day23;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.adventofcode.flashk.common.Collider2D;
import com.adventofcode.flashk.common.Vector2;

public class UnstableDiffusion {

	private static final char ELVE = '#';
	private static final char NORTH = 'N';
	private static final char SOUTH = 'S';
	private static final char WEST = 'W';
	private static final char EAST = 'E';
	
	private Set<Elve> elves = new HashSet<>();
	private Set<Collider2D> elvesColliders = new HashSet<>();
	
	public UnstableDiffusion(List<String> inputs) {
		
		// Bottom-left corner of the map will be (0,0) using this method
		int rowIndex = inputs.size() - 1; 
		int y = 0;
		for(rowIndex = inputs.size() - 1; rowIndex >= 0 ; rowIndex--) {
			String input = inputs.get(rowIndex);
			char[] row = input.toCharArray();
			for(int colIndex = 0 ; colIndex < row.length; colIndex++) {
				if(row[colIndex] == ELVE) {
					Elve elve = new Elve(colIndex, y);
					elves.add(elve);
					elvesColliders.add(elve.getCollider());
				}
			}
			y++;
		}
			
	}
	
	public long solveA(int numberOfMoves) {

		
		// Algoritmo
		
		Deque<Character> directionsOrder = new LinkedList<>();
		directionsOrder.add(NORTH);
		directionsOrder.add(SOUTH);
		directionsOrder.add(WEST);
		directionsOrder.add(EAST);
		
		// 1. Chequeamos si el elfo está aislado (no tiene colliders adyacentes)
		for(int round = 0; round < numberOfMoves; round++) {
			round(directionsOrder);
		}
		
		// Result = number of empty tiles
		// Number of empty tiles = map size - number of elves
		return calculateMapSize() - elves.size();
	}

	
	public long solve() {

		
		// Algoritmo
		
		Deque<Character> directionsOrder = new LinkedList<>();
		directionsOrder.add(NORTH);
		directionsOrder.add(SOUTH);
		directionsOrder.add(WEST);
		directionsOrder.add(EAST);
		
		// 1. Chequeamos si el elfo está aislado (no tiene colliders adyacentes)
		boolean hasAnyMovements = false; 
		int round = 0;
		do {
			hasAnyMovements = round(directionsOrder);
			round++;
		}while(hasAnyMovements);
		
		// Result = number of empty tiles
		// Number of empty tiles = map size - number of elves
		return round;
	}
	
	private boolean round(Deque<Character> directionsOrder) {
		
		Map<Vector2, List<Elve>> proposedMovements = new HashMap<>();
		
		for(Elve elve : elves) {
			// Propose a new position for each elve
			Optional<Vector2> proposedPosition = elve.evaluate(elves, directionsOrder);
			
			if(proposedPosition.isPresent()) {
				List<Elve> proposedElves = proposedMovements.getOrDefault(proposedPosition.get(), new ArrayList<>());
				proposedElves.add(elve);
				proposedMovements.put(proposedPosition.get(), proposedElves);
			}
					
		}
		
		// Commit valid positions.
		// A valid position is a position where at most 1 elve attempts to reach it.
		boolean hasAnyMovements = false;
		for(Vector2 stagedPosition : proposedMovements.keySet()) {
			List<Elve> stagedElves = proposedMovements.get(stagedPosition);
			if(stagedElves.size() == 1) {
				Elve commitElve = stagedElves.get(0);
				commitElve.move();
				hasAnyMovements = true;
			}
		}
		
		// Rotate directions
		directionsOrder.add(directionsOrder.poll());
		
		return hasAnyMovements;
	}

	private long calculateMapSize() {
		
		// Calculates the rectangle size
		long minX = elves.stream().map(v -> v.getPosition().getX()).sorted().findFirst().get();
		long maxX = elves.stream().map(v -> v.getPosition().getX()).sorted(Comparator.reverseOrder()).findFirst().get();
		long minY = elves.stream().map(v -> v.getPosition().getY()).sorted().findFirst().get();
		long maxY = elves.stream().map(v -> v.getPosition().getY()).sorted(Comparator.reverseOrder()).findFirst().get();
		long sizeX = maxX - minX + 1;
		long sizeY = maxY - minY +1;
		long size = sizeX * sizeY;
		return size;
	}
	
}
