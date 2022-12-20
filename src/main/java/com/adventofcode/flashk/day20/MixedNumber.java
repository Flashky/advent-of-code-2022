package com.adventofcode.flashk.day20;

import lombok.Getter;
import lombok.Setter;

@Getter
public class MixedNumber {
	
	@Getter
	public static int totalNumbers = 0;
	
	private int number;
	private int order;
	
	@Setter
	private boolean hasMoved = false;
	
	public MixedNumber(int number) {
		this.number = number;
		this.order = totalNumbers++;
	}
	
	public int getMovements() {
		return Math.abs(number);
	}

	@Override
	public String toString() {
		return "MixedNumber [number=" + number + "]";
	}
}
