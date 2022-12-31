package com.adventofcode.flashk.day24;

import java.util.ArrayList;
import java.util.List;

public class ValleyMap {
	
	private Cell[][] tiles;
	private int rows;
	private int cols;
	
	public ValleyMap(Cell[][] initialTiles, int minutes) {
		this.rows = initialTiles.length;
		this.cols = initialTiles[0].length;
		this.tiles = new Cell[rows][cols];
		
		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < cols; col++) {
				this.tiles[row][col] = new Cell(initialTiles[row][col]);
			}
		}
	}
	 
	 
	private void initializeBlizzard(int minutes) {
		List<Blizzard> nextBlizzardPositions = new ArrayList<>();
		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < cols; col++) {
				while(tiles[row][col].hasBlizzards()) {
					Blizzard blizzard = tiles[row][col].nextBlizzard();
					blizzard.move();
					nextBlizzardPositions.add(blizzard);
				}
			}
		}
		
		nextBlizzardPositions.stream()
							.forEach(blizzard -> tiles[blizzard.getRow()][blizzard.getCol()].addBlizzard(blizzard));
	}
}
