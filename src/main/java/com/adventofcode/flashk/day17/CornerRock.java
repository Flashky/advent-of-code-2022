package com.adventofcode.flashk.day17;

import com.adventofcode.flashk.common.Collider2D;
import com.adventofcode.flashk.common.Vector2L;

public class CornerRock extends Rock {

	public CornerRock(Vector2L initialPosition) {
		super(initialPosition);
		
		// Being 'x' the lower-left corner of the rock, these collider are:
		
		// ..#
		// ..#
		// x##
		
		// Horizontal collider
		Vector2L start = new Vector2L(position);
		Vector2L end = new Vector2L(position);
		end.transformX(2);
		
		colliders.add(new Collider2D(start,end));
		
		// Vertical collider
		start = new Vector2L(end);
		end = new Vector2L(position);
		end.transform(2);
		
		colliders.add(new Collider2D(start, end));
		
	}

	@Override
	public long getMaxY() {
		return this.getPosition().getY() + 2;
	}

}
