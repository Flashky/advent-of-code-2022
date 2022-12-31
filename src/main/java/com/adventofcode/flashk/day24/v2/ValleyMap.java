package com.adventofcode.flashk.day24.v2;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.adventofcode.flashk.day22.Range;

import lombok.Getter;

public class ValleyMap {

	private Cell[][] tiles;
	private List<Blizzard> blizzards = new LinkedList<>();
	
	@Getter
	private int rows;
	@Getter
	private int cols;
	
	/**
	 * Initializes a basic valley map based on minute 0 input.
	 */
	public ValleyMap(List<String> inputs) {
		
		rows = inputs.size();
		cols = inputs.get(0).length();
		
		Range horizontalRange = new Range(1,cols-2);
		Range verticalRange = new Range(1, rows-2);
		
		tiles = new Cell[rows][cols];
		
		for(int row = 0; row < rows; row++) {
			tiles[row] = new Cell[cols];
			char[] valleyRow = inputs.get(row).toCharArray();
			
			for(int col = 0; col < cols; col++) {
				char valleyRowValue = valleyRow[col];
				tiles[row][col] = new Cell(row, col, valleyRowValue);
				
				// Check if it is blizzard
				switch(valleyRowValue) {
					case Blizzard.LEFT: 
					case Blizzard.RIGHT: blizzards.add(new Blizzard(valleyRowValue, row, col, horizontalRange)); break;
					case Blizzard.UP:
					case Blizzard.DOWN: blizzards.add(new Blizzard(valleyRowValue, row, col, verticalRange)); break;
					default: // No blizzard
				}
			}
		}
		
		System.out.println("Test");
	}
	
	/**
	 * Creates a valley map as a copy from another valley map.
	 * @param other
	 */
	public ValleyMap(ValleyMap other) {
		
		rows = other.rows;
		cols = other.cols;
		tiles = new Cell[rows][cols];
		
		// Copy all tiles
		for(int row = 0; row < rows; row++) {
			
			tiles[row] = new Cell[cols];
			
			for(int col = 0; col < cols; col++) {
				tiles[row][col] = new Cell(other.tiles[row][col]);
			}
		}
		
		// Initialize blizzards
		blizzards = other.blizzards.stream().map(Blizzard::new).collect(Collectors.toList());
		
	}

	/**
	 * Generates a new map repesenting a new map after one minute has passed.
	 * @return
	 */
	public ValleyMap afterOneMinute() {
		
		ValleyMap newValleyMap = new ValleyMap(this);
		newValleyMap.moveBlizzards();

		return newValleyMap;
	}
	
	public Cell getCell(int row, int col) {
		return tiles[row][col];
	}

	private void moveBlizzards() {
		
		// Remove old blizzards positions
		for(Blizzard blizzard : blizzards) {
			tiles[blizzard.getPos().getY()][blizzard.getPos().getX()]
						.clearBlizzards();
		}
		
		// Update blizzard positions on new map
		for(Blizzard blizzard : blizzards) {
			blizzard.move();
			Cell tile = tiles[blizzard.getPos().getY()][blizzard.getPos().getX()];
			tile.addBlizzard(blizzard.getDirection());
		}
		
	}
	
	public void resetVisited() {
		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < cols; col++) {
				tiles[row][col].setVisited(false);
			}
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(tiles);
		result = prime * result + Objects.hash(blizzards, cols, rows);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ValleyMap other = (ValleyMap) obj;
		return Objects.equals(blizzards, other.blizzards) && cols == other.cols && rows == other.rows
				&& Arrays.deepEquals(tiles, other.tiles);
	}

	
	public void draw() {
		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < cols; col++) {
				System.out.print(tiles[row][col]);
			}
			System.out.println();
		}
	}

	
	
	
	
}
