package com.adventofcode.flashk.day20;

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
import com.adventofcode.flashk.common.test.utils.Input;

@DisplayName(TestDisplayName.DAY_20)
@TestMethodOrder(OrderAnnotation.class)
public class Day20Test extends PuzzleTest {

	private final static String INPUT_FOLDER = TestFolder.DAY_20;

	@BeforeAll
	public static void beforeAll() {
		Timer.printHeader(TestDisplayName.DAY_20);
	}

	
	@Test
	@Order(1)
	@Tag(TestTag.PART_ONE)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_ONE_SAMPLE)
	public void testSolvePart1Sample() {
		
		System.out.print("1 | sample | ");
		
		// Read input file
		List<Integer> inputs = Input.readIntegerLines(INPUT_FOLDER, TestFilename.INPUT_FILE_SAMPLE);
		
		GrovePositioningSystem positioningSystem = new GrovePositioningSystem(inputs, 1L);
		long result = positioningSystem.solveA();
		
		assertEquals(3, result);
		
	}
	
	@Test
	@Order(2)
	@Tag(TestTag.PART_ONE)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_ONE_INPUT)
	public void testSolvePart1Input() {
		
		System.out.print("1 | input  | ");
		
		// Read input file
		List<Integer> inputs = Input.readIntegerLines(INPUT_FOLDER, TestFilename.INPUT_FILE);

		GrovePositioningSystem positioningSystem = new GrovePositioningSystem(inputs, 1L);
		long result = positioningSystem.solveA();

		assertEquals(18257, result);
	}
	
	@Test
	@Order(3)
	@Tag(TestTag.PART_TWO)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_TWO_SAMPLE)
	public void testSolvePart2Sample() {
		
		System.out.print("2 | sample | ");
		
		// Read input file
		List<Integer> inputs = Input.readIntegerLines(INPUT_FOLDER, TestFilename.INPUT_FILE_SAMPLE);
	
		GrovePositioningSystem positioningSystem = new GrovePositioningSystem(inputs, 811589153L);
		long result = positioningSystem.solveB();

		assertEquals(1623178306L, result);
	}
	
	@Test
	@Order(4)
	@Tag(TestTag.PART_TWO)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_TWO_INPUT)
	public void testSolvePart2Input() {
		
		System.out.print("2 | input  | ");
		
		// Read input file
		List<Integer> inputs = Input.readIntegerLines(INPUT_FOLDER, TestFilename.INPUT_FILE);
		
		GrovePositioningSystem positioningSystem = new GrovePositioningSystem(inputs, 811589153L);
		long result = positioningSystem.solveB();
		
		assertEquals(4148032160983L, result);
	}

}
