package com.adventofcode.flashk.day17;

import com.adventofcode.flashk.common.Collider2DL;
import com.adventofcode.flashk.common.Vector2L;

public class SquareRock extends Rock {

	public SquareRock(Vector2L initialPosition) {
		
		super(initialPosition);
		
		// Being 'x' the lower-left corner of the rock, these collider are:
		
		// ##
		// x#
	
		// Square lower row collider
		Vector2L start = new Vector2L(position);
		Vector2L end = Vector2L.transform(start, Vector2L.right());
	
		colliders.add(new Collider2DL(start,end));
		
		// Square upper row collider
		start = Vector2L.transform(start, Vector2L.up());
		end = Vector2L.transform(start,Vector2L.right());
		
		colliders.add(new Collider2DL(start, end));
		
	}

	@Override
	public long getMaxY() {
		return this.getPosition().getY() + 1;
	}

}
