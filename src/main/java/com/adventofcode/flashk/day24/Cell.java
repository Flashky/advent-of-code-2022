package com.adventofcode.flashk.day24;

import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

public class Cell {

	public static final char WALL = '#';
	public static final char PATH = '.';
	
	@Getter
	private int row;
	@Getter
	private int col;
	private char value;
	private int blizzardCount = 0;
	
	@Getter
	@Setter
	private int minutes = 0;
	
	@Getter
	@Setter
	private boolean visited = false;
	
	public Cell(int row, int col, char value) {
		this.row = row;
		this.col = col;
		this.value = value;
		
		if(Blizzard.isBlizzard(value)) {
			
			// Single blizzard
			this.blizzardCount = 1;
			
		} else if(Character.isDigit(value)) {
			
			// Multiple blizzards
			String stringValue = String.valueOf(value);
			this.value = stringValue.charAt(0);
			this.blizzardCount = Integer.valueOf(stringValue);
			
		}
		
	}
	
	public Cell(Cell other) {
		this.row = other.row;
		this.col = other.col;
		this.value = other.value;
		this.blizzardCount = other.blizzardCount;
		this.visited = other.visited;
	}
	
	public boolean isPath() {
		return this.value == PATH;
	}
	
	public boolean hasBlizzard() {
		return blizzardCount > 0;
	}
	
	public void clearBlizzards() {
		this.value = PATH;
		this.blizzardCount = 0;
	}

	public void addBlizzard(char value) {
		
		if(this.value == WALL) {
			throw new UnsupportedOperationException("Cannot add a blizzard to a wall cell.");
		}
		
		blizzardCount++;
		
		if(blizzardCount == 1) {
			this.value = value;
		} else {
			this.value = String.valueOf(blizzardCount).charAt(0);
		}
	

	}
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(col, row, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		return col == other.col && row == other.row && value == other.value;
	}
	
}
