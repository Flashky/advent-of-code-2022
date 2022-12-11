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
	
	public Knot(Knot next) {
		this.next = next;
	}

	/** 
	 * Recursively applies a movement on the current knot and its sucessors.
	 * @param direction
	 * @return
	 */
	public Vector2 move(Vector2 direction) {
		
		if(prev == null) {
			// Head knot
			pos.transform(direction);
		} else {
			// Any tail knots
			Vector2 head = prev.getPos();
			Vector2 tail = this.getPos();
			int distance = (int) Vector2.distance(head, tail);

			if(distance > 1) {
				
				// Override head direction with tail direction
				direction = Vector2.substract(head, tail);
				direction.normalize();
				tail.transform(direction);
			}

		}
		
		if(next == null) {
			// Tail - Finished movement
			return new Vector2(pos);
		} else {
			return next.move(direction);
		}
	}
}
