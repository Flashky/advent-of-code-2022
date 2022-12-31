package com.adventofcode.flashk.day24;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class BlizzardBasin {

	private Map<Integer, ValleyMap> valleyMaps = new HashMap<>();
	private int maxMaps;
	
	public BlizzardBasin(List<String> inputs) {
		
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
			
		} while(!currentMap.equals(initialMap));
		
		maxMaps = valleyMaps.size();
		
	}
	
	public long solveA() {
		
		ValleyMap initialMap = valleyMaps.get(0);
		
		// Obtain initial cell and target position
		Cell start = initialMap.getCell(0,1);
		int endRow = initialMap.getRows() - 1;
		int endCol = initialMap.getCols() - 2;		
		
		Cell target = bfs(start, endRow, endCol);
		
		if(target != null) {
			return target.getMinutes();
		} 
		
		return 0;
	}
	
	public long solveB() {

		ValleyMap initialMap = valleyMaps.get(0);
		
		// Obtain initial cell and end positions
		int startRow = 0;
		int startCol = 1;
		int endRow = initialMap.getRows() - 1;
		int endCol = initialMap.getCols() - 2;	
		 
		// First pass: start -> end
		Cell start = initialMap.getCell(startRow,startCol);
		Cell end = bfs(start, endRow, endCol);
		resetVisited();
		
		// Second pass: end -> start
		start = bfs(end, startRow, startCol);
		resetVisited();
		
		// Third pass: start -> end
		end = bfs(start, endRow, endCol);
		
		if(end != null) {
			return end.getMinutes();
		}
		
		return 0;
		
	}
	
	/**
	 * Applies BFS to find shortest path to destination. returns the destination cell when reached.
	 * @param root the start cell
	 * @param targetRow the target row index
	 * @param targetCol the target col index
	 * @return the end cell, including all of its time information.
	 */
	private Cell bfs(Cell root, int targetRow, int targetCol) {
		
		// BFS algorithm: 
		// https://en.wikipedia.org/wiki/Breadth-first_search

		Queue<Cell> toVisit = new LinkedList<>();
		root.setVisited(true);
		toVisit.add(root);
		
		while(!toVisit.isEmpty()) {
			
			Cell currentPos = toVisit.poll();
			
			// Check for solution
			if(targetRow == currentPos.getRow() && targetCol == currentPos.getCol()) {
				return currentPos;
			}
			
			Queue<Cell> adjacents = getAdjacents(currentPos);
			for(Cell adjacent : adjacents) {
				if(!adjacent.isVisited()) {
					adjacent.setVisited(true);
					toVisit.add(adjacent);
				}
			}
		}
		
		return null;
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
		
		// Down - Extra verification to avoid out of range issues
		if(currentPos.getRow() < nextMinuteMap.getRows()-1) {
			adjacent = nextMinuteMap.getCell(row+1, col);
			if(adjacent.isPath()) {
				adjacent.setMinutes(nextMinute);
				adjacentCells.add(adjacent);
			}
		}
		
		// Up - Extra verification to avoid out of range issues
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

	private void resetVisited() {
	
		// For each map, mark all cells as not visited
		for(ValleyMap valleyMap : valleyMaps.values()) {
			valleyMap.resetVisited();
		}
	}
	
	public void drawMap(ValleyMap valleyMap, int minutes) {
		if(minutes == 0) {
			System.out.println("Initial map");
		} else {
			System.out.println("Minute "+ minutes);
		}
		
		valleyMap.draw();
	}
		
}
