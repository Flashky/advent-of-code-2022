package com.adventofcode.flashk.common;

public final class MathUtil {

	private MathUtil() {}
	
	public static long summation(long value) {
		
		if(value == 0) {
			return 0;
		}
		
		if(value == 1) {
			return 1;
		}
		
		return value + summation(value-1);
	}
}
