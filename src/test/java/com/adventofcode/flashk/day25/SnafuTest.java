package com.adventofcode.flashk.day25;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SnafuTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testDecValue() {
		
		assertEquals(1, Snafu.decValue("1"));
		assertEquals(2, Snafu.decValue("2"));
		assertEquals(3, Snafu.decValue("1="));
		assertEquals(4, Snafu.decValue("1-"));
		assertEquals(5, Snafu.decValue("10"));
		assertEquals(6, Snafu.decValue("11"));
		assertEquals(7, Snafu.decValue("12"));
		assertEquals(8, Snafu.decValue("2="));
		assertEquals(9, Snafu.decValue("2-"));
		assertEquals(10, Snafu.decValue("20"));
		assertEquals(15, Snafu.decValue("1=0"));
		assertEquals(20, Snafu.decValue("1-0"));
		assertEquals(2022, Snafu.decValue("1=11-2"));
		assertEquals(12345, Snafu.decValue("1-0---0"));
		assertEquals(314159265, Snafu.decValue("1121-1110-1=0"));
		
		System.out.println(Snafu.decValue("2=01"));
		System.out.println(Snafu.decValue("2=00"));
	}
	
	@Test
	void testSnafuValue() {
		
		assertEquals("1", Snafu.snafuValue(1));
		assertEquals("2", Snafu.snafuValue(2));
		assertEquals("1=", Snafu.snafuValue(3));
		assertEquals("1-", Snafu.snafuValue(4));
		assertEquals("10", Snafu.snafuValue(5));
		assertEquals("11", Snafu.snafuValue(6));
		assertEquals("12", Snafu.snafuValue(7));
		assertEquals("2=", Snafu.snafuValue(8));
		assertEquals("2-", Snafu.snafuValue(9));
		assertEquals("20", Snafu.snafuValue(10));
		assertEquals("1=0", Snafu.snafuValue(15));
		assertEquals("1-0", Snafu.snafuValue(20));
		assertEquals("1=11-2", Snafu.snafuValue(2022));
		assertEquals("1-0---0", Snafu.snafuValue(12345));
		assertEquals("1121-1110-1=0", Snafu.snafuValue(314159265));
		

	}

}
