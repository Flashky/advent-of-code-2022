package com.adventofcode.flashk.day18;

import com.adventofcode.flashk.common.Vector3;

import lombok.Getter;

@Getter
public class Cube {

	private Vector3 pos;
	private int openSides = 6;
	
	public Cube(String input) {
		pos = new Vector3(input);
	}
	
	public void decrementOpenSides() {
		openSides--;
	}

	public boolean isAdjacent(Cube other) {
	
		int dx = Math.abs(pos.getX() - other.pos.getX());
		int dy = Math.abs(pos.getY() - other.pos.getY());
		int dz = Math.abs(pos.getZ() - other.pos.getZ());
		
		return dx+dy+dz == 1;
		
	}
	
	public boolean isAdjacentX(Cube other) {
		
		int dx = Math.abs(pos.getX() - other.pos.getX());
		
		return dx == 1;
	}
	
	public boolean isAdjacentY(Cube other) {
		
		int dy = Math.abs(pos.getY() - other.pos.getY());
		
		return dy == 1;
	}
	
	public boolean isAdjacentZ(Cube other) {
		
		int dz = Math.abs(pos.getZ() - other.pos.getZ());
		
		return dz == 1;
	}
	
	@Override
	public String toString() {
		return "Cube [pos=" + pos + "]";
	}
	
	
	
}
