package com.adventofcode.flashk.day17;

import com.adventofcode.flashk.common.Collider2D;
import com.adventofcode.flashk.common.Vector2;

public class CrossRock extends Rock {

	public CrossRock(Vector2 initialPosition) {
		
		super(initialPosition);
		
		// Being 'x' the lower-left corner of the rock, these collider are:
		
		// .#.
		// ###
		// x#.
	
		
		// Vertical collider
		Vector2 start = Vector2.transform(position, Vector2.right());
		Vector2 end = new Vector2(start);
		end.transformY(2);
		
		colliders.add(new Collider2D(start,end));

		// Horizontal Collider
		start = Vector2.transform(position, Vector2.up());
		end = new Vector2(start);
		end.transformX(2);
		
		colliders.add(new Collider2D(start,end));
		
	}

	@Override
	public int getMaxY() {
		return this.getPosition().getY() + 2;
	}

	
}
