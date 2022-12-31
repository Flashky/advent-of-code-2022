package com.adventofcode.flashk.day24.v2;

import java.util.Objects;

import com.adventofcode.flashk.common.Vector2;
import com.adventofcode.flashk.day22.Range;

import lombok.Getter;

public class Blizzard {
	
	public static final char LEFT = '<';
	public static final char RIGHT = '>';
	public static final char UP = '^';
	public static final char DOWN = 'v';
	
	@Getter
	private final char direction;
	private final Range range;
	
	private int row;
	private int col;

	public Blizzard(char direction, int row, int col, Range range) {
		this.direction = direction;
		this.row = row;
		this.col = col;
		this.range = range;
	}
	
	public Blizzard(Blizzard other) {
		this.direction = other.direction;
		this.row = other.row;
		this.col = other.col;
		this.range = other.range;
	}

	public static boolean isBlizzard(char value) {
		
		switch(value) {
			case LEFT:
			case RIGHT:
			case UP:
			case DOWN: return true;
			default: return false;
		}
	}

	public void move() {
		
		// Modulo operate movement
		if(direction == RIGHT) {
			col = range.mod(col+1);
		} else if(direction == LEFT) {
			col = range.mod(col-1);
		} else if(direction == UP) {
			row = range.mod(row-1);
		} else if(direction == DOWN){
			row = range.mod(row+1);
		}

	}
	
	public void backtrack() {
		
		// Modulo operate movement
		if(direction == RIGHT) {
			col = range.mod(col-1);
		} else if(direction == LEFT) {
			col = range.mod(col+1);
		} else if(direction == UP) {
			row = range.mod(row+1);
		} else if(direction == DOWN){
			row = range.mod(row-1);
		}

	}
	
	public Vector2 getPos() {
		return new Vector2(col,row);
	}

	@Override
	public int hashCode() {
		return Objects.hash(col, direction, row);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Blizzard other = (Blizzard) obj;
		return col == other.col && direction == other.direction && row == other.row;
	}
	
	
}
