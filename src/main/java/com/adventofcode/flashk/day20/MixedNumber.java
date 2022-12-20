package com.adventofcode.flashk.day20;

import lombok.Getter;
import lombok.Setter;

@Getter
public class MixedNumber {
	
	@Getter
	public static int totalNumbers = 0;
	
	private long number;
	private int order;
	
	@Setter
	private boolean hasMoved = false;
	
	public MixedNumber(long number) {
		this.number = number;
		this.order = totalNumbers++;
	}
	
	public void applyDecryptionKey(long decryptionKey) {
		this.number *= decryptionKey;
	}
	
	public long getMovements() {
		return Math.abs(number);
	}

	public static void resetTotalNumbers() {
		totalNumbers = 0;
	}
	
	@Override
	public String toString() {
		return "MixedNumber [number=" + number + "]";
	}
}
