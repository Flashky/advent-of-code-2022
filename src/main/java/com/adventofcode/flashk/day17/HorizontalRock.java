package com.adventofcode.flashk.day17;

import com.adventofcode.flashk.common.Collider2DL;
import com.adventofcode.flashk.common.Vector2L;

public class HorizontalRock extends Rock {

	public HorizontalRock(Vector2L initialPosition) {
		super(initialPosition);
		
		// Being 'x' the lower-left corner of the rock, this collider is:
		
		// x###
		
		Vector2L start = new Vector2L(position);
		Vector2L end = new Vector2L(position);
		end.transformX(3);
		
		colliders.add(new Collider2DL(start,end));
		
	}

	@Override
	public long getMaxY() {
		return this.getPosition().getY();
	}

}
