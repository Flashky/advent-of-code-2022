package com.adventofcode.flashk.day18;

import java.util.Objects;

import com.adventofcode.flashk.common.Vector3;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Cube {

	private Vector3 pos;
	private int openSides = 6;
	private boolean lava = true;
	
	public Cube(String input) {
		this.pos = new Vector3(input);
	}
	
	public Cube(int x, int y, int z) {
		this.pos = new Vector3(x,y,z);
	}
	
	public Cube(Vector3 pos, boolean lava) {
		this.pos = new Vector3(pos);
		this.lava = lava;
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
	public int hashCode() {
		return Objects.hash(pos);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cube other = (Cube) obj;
		return Objects.equals(pos, other.pos);
	}
	
	
	@Override
	public String toString() {
		return "Cube [pos=" + pos + "]";
	}
	
}
