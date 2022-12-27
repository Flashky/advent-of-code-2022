package com.adventofcode.flashk.day17;

import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TetrisState {

	private int shapeIndex;
	private int jetIndex;
	private long maxY;
	
	public void normalizeHeight(long heightBeforeCycle) {
		maxY -= heightBeforeCycle;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(jetIndex, shapeIndex);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TetrisState other = (TetrisState) obj;
		return jetIndex == other.jetIndex && shapeIndex == other.shapeIndex;
	}
	
	@Override
	public String toString() {
		return shapeIndex + "," + jetIndex + " - height = "+maxY;
	}
	
	
}
