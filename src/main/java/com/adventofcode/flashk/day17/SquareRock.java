package com.adventofcode.flashk.day17;

import com.adventofcode.flashk.common.Collider2D;
import com.adventofcode.flashk.common.Vector2;

public class SquareRock extends Rock {

	public SquareRock(Vector2 initialPosition) {
		
		super(initialPosition);
		
		// Being 'x' the lower-left corner of the rock, these collider are:
		
		// ##
		// x#
	
		// Square lower row collider
		Vector2 start = new Vector2(position);
		Vector2 end = Vector2.transform(start, Vector2.right());
	
		colliders.add(new Collider2D(start,end));
		
		// Square upper row collider
		start = Vector2.transform(start, Vector2.up());
		end = Vector2.transform(start,Vector2.right());
		
		colliders.add(new Collider2D(start, end));
		
	}

	@Override
	public long getMaxY() {
		return this.getPosition().getY() + 1;
	}

}
