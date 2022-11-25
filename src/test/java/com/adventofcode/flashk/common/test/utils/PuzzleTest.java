package com.adventofcode.flashk.common.test.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class PuzzleTest {

	private Timer timer = new Timer();
	
	@BeforeEach
	public void before() {
		timer.start();
	}
	
	@AfterEach
	public void after() {
		timer.stop();
	}
	

	public abstract void testSolvePart1Sample();
	public abstract void testSolvePart1Input();
	public abstract void testSolvePart2Sample();
	public abstract void testSolvePart2Input();
	
}
