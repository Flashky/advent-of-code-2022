package com.adventofcode.flashk.day17;

import com.adventofcode.flashk.common.Collider2D;
import com.adventofcode.flashk.common.Vector2;

public class CornerRock extends Rock {

	public CornerRock(Vector2 initialPosition) {
		super(initialPosition);
		
		// Being 'x' the lower-left corner of the rock, these collider are:
		
		// ..#
		// ..#
		// x##
		
		// Horizontal collider
		Vector2 start = new Vector2(position);
		Vector2 end = new Vector2(position);
		end.transformX(2);
		
		colliders.add(new Collider2D(start,end));
		
		// Vertical collider
		start = new Vector2(end);
		end = new Vector2(position);
		end.transform(2);
		
		colliders.add(new Collider2D(start, end));
		
	}

	@Override
	public int getMaxY() {
		return this.getPosition().getY() + 2;
	}

}
