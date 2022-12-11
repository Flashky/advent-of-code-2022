package com.adventofcode.flashk.day09;

import com.adventofcode.flashk.common.Vector2;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Knot {

	private Vector2 pos = new Vector2();
	private Knot next;
	private Knot prev;

	/** 
	 * Recursively applies a movement on the current knot and its sucessors.
	 * @param direction the direction where the head must move
	 * @return The position where the tail has passed.
	 */
	public Vector2 move(Vector2 direction) {
		
		// Head knot
		if(prev == null) {
			pos.transform(direction);
		}
		
		// Tail knots
		// There are no cases for just 1 knot at the puzzle, so there will be always at least one next.
		return next.move();
	}
	
	private Vector2 move() {
	
		Vector2 previousKnotPos = prev.getPos();
		int distance = (int) Vector2.distance(previousKnotPos, pos);

		if(distance > 1) {
			Vector2 direction = Vector2.substract(previousKnotPos, pos);
			direction.normalize();
			pos.transform(direction);
		}
		
		if(next == null) {
			return new Vector2(pos); // Finished movement
		} else {
			return next.move();
		}
	}
}
