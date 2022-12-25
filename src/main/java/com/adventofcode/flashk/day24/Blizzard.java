package com.adventofcode.flashk.day24;

import com.adventofcode.flashk.common.Vector2;
import com.adventofcode.flashk.day22.Range;

import lombok.Getter;

@Getter
public class Blizzard {

	public static final char LEFT = '<';
	public static final char RIGHT = '>';
	public static final char UP = '^';
	public static final char DOWN = 'v';
	
	private char direction;
	private int row;
	private int col;
	private Range range;
	
	public Blizzard(char direction, int row, int col, Range range) {
		this.direction = direction;
		this.row = row;
		this.col = col;
		this.range = range;
	}
	
	public void move() {
		
		// Modulo operate movement
		if(direction == RIGHT) {
			col = range.mod(col+1);
		} else if(direction == LEFT) {
			col = range.mod(col-1);
		} else if(direction == UP) {
			row = range.mod(row+1);
		} else if(direction == DOWN){
			row = range.mod(row-1);
		}

	}
	
	public Vector2 getPos() {
		return new Vector2(col,row);
	}
}
