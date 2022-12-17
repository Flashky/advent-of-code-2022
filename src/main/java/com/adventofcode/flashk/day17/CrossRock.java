package com.adventofcode.flashk.day17;

import com.adventofcode.flashk.common.Collider2D;
import com.adventofcode.flashk.common.Vector2L;

public class CrossRock extends Rock {

	public CrossRock(Vector2L initialPosition) {
		
		super(initialPosition);
		
		// Being 'x' the lower-left corner of the rock, these collider are:
		
		// .#.
		// ###
		// x#.
	
		
		// Vertical collider
		Vector2L start = Vector2L.transform(position, Vector2L.right());
		Vector2L end = new Vector2L(start);
		end.transformY(2);
		
		colliders.add(new Collider2D(start,end));

		// Horizontal Collider
		start = Vector2L.transform(position, Vector2L.up());
		end = new Vector2L(start);
		end.transformX(2);
		
		colliders.add(new Collider2D(start,end));
		
	}

	@Override
	public long getMaxY() {
		return this.getPosition().getY() + 2;
	}

	
}
