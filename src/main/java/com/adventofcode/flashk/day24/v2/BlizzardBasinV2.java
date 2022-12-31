package com.adventofcode.flashk.day24.v2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.adventofcode.flashk.common.Vector2;

public class BlizzardBasinV2 {

	private Map<Integer, ValleyMap> valleyMaps = new HashMap<>(); // Podría orientarse como una cola también
	private int maxMaps; // Sobre este calcularemos el módulo
	private int targetRow;
	private int targetCol;
	
	public BlizzardBasinV2(List<String> inputs) {
		
		ValleyMap currentMap = new ValleyMap(inputs);
		ValleyMap initialMap = currentMap;

		int minutes = 0;
		valleyMaps.put(minutes, currentMap);
		
		do {
			//drawMap(currentMap, minutes);
			valleyMaps.put(minutes, currentMap);
			
			minutes++;
			ValleyMap previousMap = currentMap;
			currentMap = previousMap.afterOneMinute();
			
		} while(!currentMap.equals(initialMap)); // Hasta que el mapa actual sea similar al primer mapa
		
		maxMaps = valleyMaps.size();

		targetRow = initialMap.getRows() - 1;
		targetCol = initialMap.getCols() - 2;
		//target = new Vector2(initialMap.getRows()-1, initialMap.getCols()-2);
		
	}
	
	public long solveABFS() {
		
		// Implementar como BFS:
		// https://en.wikipedia.org/wiki/Breadth-first_search
	
		// Initialize queue with root node
		Queue<Cell> toVisit = new LinkedList<>();
		Cell root = valleyMaps.get(0).getCell(0,1);
		root.setVisited(true);
		toVisit.add(root);
		
		while(!toVisit.isEmpty()) {
			
			Cell currentPos = toVisit.poll();
			
			// Check for solution
			if(targetRow == currentPos.getRow() && targetCol == currentPos.getCol()) {
				return currentPos.getMinutes();
			}
			
			Queue<Cell> adjacents = getAdjacents(currentPos);
			for(Cell adjacent : adjacents) {
				if(!adjacent.isVisited()) {
					adjacent.setVisited(true);
					toVisit.add(adjacent);
				}
			}
		}
		
		return 0;
	}
	
	private Queue<Cell> getAdjacents(Cell currentPos) {

		// Check adjacents against next map
		int nextMinute = currentPos.getMinutes() + 1;
		int minuteKey = nextMinute % maxMaps;
		ValleyMap nextMinuteMap = valleyMaps.get(minuteKey);
		
		Queue<Cell> adjacentCells = new LinkedList<>();
		int row = currentPos.getRow();
		int col = currentPos.getCol();
		
		// Left
		Cell adjacent = nextMinuteMap.getCell(row, col-1);
		if(adjacent.isPath()) {
			adjacent.setMinutes(nextMinute);
			adjacentCells.add(adjacent);
		}
		
		// Right
		adjacent = nextMinuteMap.getCell(row, col+1);
		if(adjacent.isPath()) {
			adjacent.setMinutes(nextMinute);
			adjacentCells.add(adjacent);
		}
		
		// Down
		adjacent = nextMinuteMap.getCell(row+1, col);
		if(adjacent.isPath()) {
			adjacent.setMinutes(nextMinute);
			adjacentCells.add(adjacent);
		}
		
		// Up - Extra verification to avoid start position issues
		if(currentPos.getRow() > 0) {
			adjacent = nextMinuteMap.getCell(row-1, col);
			if(adjacent.isPath()) {
				adjacent.setMinutes(nextMinute);
				adjacentCells.add(adjacent);
			}
		}
		
		// Wait
		if(!currentPos.hasBlizzard()) {
			adjacent = nextMinuteMap.getCell(row, col);
			if(adjacent.isPath()) {
				adjacent.setMinutes(nextMinute);
				adjacentCells.add(adjacent);
			}
		}
		
		return adjacentCells;
	}

	private void drawMap(ValleyMap valleyMap, int minutes) {
		if(minutes == 0) {
			System.out.println("Initial map");
		} else {
			System.out.println("Minute "+ minutes);
		}
		
		valleyMap.draw();
	}
		
}
