package com.adventofcode.flashk.day22;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RangeTest {

	@Test
	void testRangeNormalized() {
		Range range = new Range(0,3);
		
		assertEquals(4, range.getSize());
		assertEquals(0, range.getStart());
		assertEquals(3, range.getEnd());
	}

	@Test
	void testRange() {
		Range range = new Range(5,8);
		
		assertEquals(4, range.getSize());
		assertEquals(5, range.getStart());
		assertEquals(8, range.getEnd());
	}
	
	@Test
	void testModNormalizedPossitive() {
		
		Range range = new Range(0,3);
		
		assertEquals(0, range.mod(4));
		assertEquals(1, range.mod(5));
		assertEquals(2, range.mod(6));
		assertEquals(3, range.mod(7));
		assertEquals(0, range.mod(8));
		assertEquals(1, range.mod(9));
		assertEquals(2, range.mod(10));
		assertEquals(3, range.mod(11));
		
	}
	
	@Test
	void testModNormalizedZero() {
		
		Range range = new Range(0,3);
		
		assertEquals(0, range.mod(0));
		
	}
	
	@Test
	void testModNormalizedNegative() {
		
		Range range = new Range(0,3);
		
		assertEquals(3, range.mod(-1));
		assertEquals(2, range.mod(-2));
		assertEquals(1, range.mod(-3));
		assertEquals(0, range.mod(-4));
		assertEquals(3, range.mod(-5));
		assertEquals(2, range.mod(-6));
		assertEquals(1, range.mod(-7));
		assertEquals(0, range.mod(-8));
		
	}
	
	@Test
	void testModPossitiveZero() {
		
		Range range = new Range(5,8);
		
		assertEquals(5, range.mod(5));
	}
	
	@Test
	void testModPossitive() {
		
		Range range = new Range(5,8);
		
		assertEquals(5, range.mod(5));
		assertEquals(5, range.mod(9));
		assertEquals(6, range.mod(10));
		assertEquals(7, range.mod(11));
		assertEquals(8, range.mod(12));
		assertEquals(5, range.mod(13));
		assertEquals(6, range.mod(14));
		assertEquals(7, range.mod(15));
		assertEquals(8, range.mod(16));
		
	}
	
	@Test
	void testModNegative() {
		
		Range range = new Range(5,8);
		
		assertEquals(8, range.mod(4));
		assertEquals(7, range.mod(3));
		assertEquals(6, range.mod(2));
		assertEquals(5, range.mod(1));
		assertEquals(8, range.mod(0));
		assertEquals(7, range.mod(-1));
		assertEquals(6, range.mod(-2));
		assertEquals(5, range.mod(-3));
		
	}

}
