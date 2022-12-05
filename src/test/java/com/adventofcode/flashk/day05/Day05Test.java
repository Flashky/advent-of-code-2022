package com.adventofcode.flashk.day05;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

@DisplayName(TestDisplayName.DAY_05)
@TestMethodOrder(OrderAnnotation.class)
public class Day05Test extends PuzzleTest {

	private final static String INPUT_FOLDER = TestFolder.DAY_05;

	private CraneStrategy basicStrategy = new CraneBasicStrategy();
	private CraneStrategy advancedStrategy = new CraneAdvancedStrategy();
	
	@BeforeAll
	public static void beforeAll() {
		Timer.printHeader(TestDisplayName.DAY_05);
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
		SupplyStacks supplyStacks = new SupplyStacks(inputs);
		String result = supplyStacks.solve(basicStrategy);
		assertEquals("CMZ", result);
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
		SupplyStacks supplyStacks = new SupplyStacks(inputs);
		String result = supplyStacks.solve(basicStrategy);
		
		assertEquals("QGTHFZBHV", result);
		
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
		SupplyStacks supplyStacks = new SupplyStacks(inputs);
		String result = supplyStacks.solve(advancedStrategy);
		assertEquals("MCD", result);
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
		SupplyStacks supplyStacks = new SupplyStacks(inputs);
		String result = supplyStacks.solve(advancedStrategy);
		assertEquals("MGDMPSZTM", result);
	}

}
