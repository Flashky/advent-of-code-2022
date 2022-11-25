package com.adventofcode.flashk.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MathUtilTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testSummation0() {
		assertEquals(0, MathUtil.summation(0));
	}

	@Test
	void testSummation1() {
		assertEquals(1, MathUtil.summation(1));
	}
	
	@Test
	void testSummation2() {
		assertEquals(3, MathUtil.summation(2));
	}
	
	@Test
	void testSummation10() {
		assertEquals(55, MathUtil.summation(10));
	}
}
