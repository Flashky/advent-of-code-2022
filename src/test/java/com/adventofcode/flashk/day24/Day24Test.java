package com.adventofcode.flashk.day24;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.adventofcode.flashk.common.test.constants.TestDisplayName;
import com.adventofcode.flashk.common.test.constants.TestFilename;
import com.adventofcode.flashk.common.test.constants.TestFolder;
import com.adventofcode.flashk.common.test.constants.TestTag;
import com.adventofcode.flashk.common.test.utils.PuzzleTest;
import com.adventofcode.flashk.common.test.utils.Timer;
import com.adventofcode.flashk.common.test.utils.Util;

@DisplayName(TestDisplayName.DAY_24)
@TestMethodOrder(OrderAnnotation.class)
public class Day24Test extends PuzzleTest {

	private final static String INPUT_FOLDER = TestFolder.DAY_24;

	@BeforeAll
	public static void beforeAll() {
		Timer.printHeader(TestDisplayName.DAY_24);
	}

	@Test
	@Order(0)
	@Tag(TestTag.PART_ONE)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_ONE_SINGLE_SAMPLE)
	public void testSolvePart1SingleSample() {
		
		System.out.print("1 | single sample | ");
		
		// Read input file
		List<String> inputs = Util.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE_SINGLE_SAMPLE);
		
		BlizzardBasin blizzardBasin = new BlizzardBasin(inputs);
		
		// This test use is only for debugging blizzard movements
		assertNotNull(blizzardBasin);
		assertEquals(0,0);
		
	}
	
	@Test
	@Order(1)
	@Tag(TestTag.PART_ONE)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_ONE_SAMPLE)
	public void testSolvePart1Sample() {
		
		System.out.print("1 | sample | ");
		
		// Read input file
		List<String> inputs = Util.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE_SAMPLE);
		
		BlizzardBasin blizzardBasin = new BlizzardBasin(inputs);
		long result = blizzardBasin.solveA();
		
		assertEquals(18, result);
	}
	
	@Test
	@Order(2)
	@Tag(TestTag.PART_ONE)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_ONE_INPUT)
	public void testSolvePart1Input() {
		
		System.out.print("1 | input  | ");
		
		// Read input file
		List<String> inputs = Util.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE);
		
		BlizzardBasin blizzardBasin = new BlizzardBasin(inputs);
		long result = blizzardBasin.solveA();
		
		assertEquals(238, result);
	}
	
	@Test
	@Order(3)
	@Tag(TestTag.PART_TWO)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_TWO_SAMPLE)
	public void testSolvePart2Sample() {
		
		System.out.print("2 | sample | ");
		
		// Read input file
		List<String> inputs = Util.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE_SAMPLE);
		
		BlizzardBasin blizzardBasin = new BlizzardBasin(inputs);
		long result = blizzardBasin.solveB();
		
		assertEquals(54, result);
		
	}
	
	@Test
	@Order(4)
	@Tag(TestTag.PART_TWO)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_TWO_INPUT)
	public void testSolvePart2Input() {
		
		System.out.print("2 | input  | ");
		
		// Read input file
		List<String> inputs = Util.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE);
		
		BlizzardBasin blizzardBasin = new BlizzardBasin(inputs);
		long result = blizzardBasin.solveB();
		
		assertEquals(751, result);
	}

}
