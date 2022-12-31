package com.adventofcode.flashk.day24;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cell {

	private boolean wall = false;
	private Queue<Blizzard> blizzards = new LinkedList<>();
	private int row;
	private int col;
	private boolean visited = false;
	private int minutes = 0;
	private boolean expedition = false;
	
	public Cell(boolean wall, int row, int col) {
		this.wall = wall;
		this.col = col;
		this.row = row;
	}
	
	public Cell(Blizzard blizzard, int row, int col) {
		this.blizzards.add(blizzard);
		this.col = col;
		this.row = row;
	}
	
	public Cell(Cell cell) {
		this.wall = cell.wall;
		this.row = cell.row;
		this.col = cell.col;
		this.visited = cell.visited;
		this.minutes = cell.minutes;
	}

	public void addBlizzard(Blizzard blizzard) {
		blizzards.add(blizzard);
	}
	
	public boolean hasBlizzards() {
		return !blizzards.isEmpty();
	}
	
	public Blizzard nextBlizzard() {
		return blizzards.poll();
	}
	
	public boolean isEmpty() {
		return !wall && blizzards.isEmpty();
	}

	public void incrementTime() {
		minutes++;
	}
	
	public void decrementTime() {
		minutes--;
	}
	
	
	@Override
	public String toString() {
		
		if(wall) {
			return "#";
		} else if(!blizzards.isEmpty()) {
			if(blizzards.size() > 1) {
				return String.valueOf(blizzards.size());
			} else {
				return String.valueOf(blizzards.peek().getDirection());
			}
		} else if(expedition) {
			return "E";
		} else {
			return ".";
		}
		
		
		//return "Cell [wall=" + wall + ", blizzards=" + blizzards.size() + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(col, row);
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
		return col == other.col && row == other.row;
	}
	
	
}
