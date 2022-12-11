package com.adventofcode.flashk.day09;

import java.util.HashSet;
import java.util.Set;

import com.adventofcode.flashk.common.Vector2;

public class Rope {

	private Knot head;
	private Knot tail;
	
	public Rope(int numberOfKnots) {
	
		for(int i = 0; i < numberOfKnots; i++) {
			
			if(i == 0) {
				// Create head knot
				head = new Knot();
				tail = head;
			} else {
				// Create tail knots
				Knot newTail = new Knot();
				newTail.setPrev(tail);
				tail.setNext(newTail);
				tail = newTail;
			}

		}
	}
	
	/**
	 * Moves the rope.
	 * @param direction the direction where the head should move.
	 * @return a set of positions where the tail has passed
	 */
	public Set<Vector2> move(Movement movement) {
		
		Set<Vector2> tailPositions = new HashSet<>();
		
		for(int steps = 0; steps < movement.getSteps(); steps++) {
			Vector2 tailPos = head.move(movement.getDirection());
			tailPositions.add(tailPos);
		}
		
		return tailPositions;
	}

}
