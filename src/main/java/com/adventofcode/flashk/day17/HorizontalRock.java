package com.adventofcode.flashk.day17;

import com.adventofcode.flashk.common.Collider2D;
import com.adventofcode.flashk.common.Vector2;

public class HorizontalRock extends Rock {

	public HorizontalRock(Vector2 initialPosition) {
		super(initialPosition);
		
		// Being 'x' the lower-left corner of the rock, this collider is:
		
		// x###
		
		Vector2 start = new Vector2(position);
		Vector2 end = new Vector2(position);
		end.transformX(3);
		
		colliders.add(new Collider2D(start,end));
		
	}

	@Override
	public int getMaxY() {
		return this.getPosition().getY();
	}

}
