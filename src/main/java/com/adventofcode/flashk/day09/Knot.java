package com.adventofcode.flashk.day09;

import com.adventofcode.flashk.common.Vector2;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class Knot {

	private Vector2 pos = new Vector2(0,0);
	private Knot next;
	private boolean isLast = true;
	
	public Knot(Knot next) {
		this.next = next;
		next.isLast = false;
	}
	
}
