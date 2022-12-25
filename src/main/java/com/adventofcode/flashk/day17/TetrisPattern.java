package com.adventofcode.flashk.day17;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TetrisPattern {

	
	private char shape;
	private long x;
	private String movements = StringUtils.EMPTY;
	
	public void addMovement(char jet) {
		movements += jet;	
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(movements, shape, x);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TetrisPattern other = (TetrisPattern) obj;
		return Objects.equals(movements, other.movements) && shape == other.shape && x == other.x;
	}

	@Override
	
	public String toString() {
		return Character.toString(shape) + x;
	}


	
	
}
