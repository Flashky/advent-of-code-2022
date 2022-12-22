package com.adventofcode.flashk.day22;

import lombok.Getter;

@Getter
public class Range {

	private int start;
	private int end;
	private int size;
	
	public Range(int start, int end) {
		
		if(end < start) {
			throw new IllegalArgumentException("End index must be greater than start.");
		}
		
		this.start = start;
		this.end = end;
		this.size = end - start + 1;
	}
	
	/**
	 * Calculates <code>number modulo range size</code>.
	 * <p>Works both with negative and possitive numbers.</p>
	 * @param number the number to apply modulo range size.
	 * @return a number between {@link #getStart()} and {@link #getEnd()}
	 */
	public int mod(int number) {
		
		int normalizedNumber = number - start;
		
		// % gives remainder on negative numbers in java:
		// (-1) mod 4 should be 3, but instead, java returns -1

		// Use Math.floorMod instead %:
		int normalizedPos = Math.floorMod(normalizedNumber, size);
		
		return normalizedPos + start;
	}

	@Override
	public String toString() {
		return "Range [start=" + start + ", end=" + end + ", size=" + size + "]";
	}
	
	
}
