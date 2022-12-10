package com.adventofcode.flashk.day10;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
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

@DisplayName(TestDisplayName.DAY_10)
@TestMethodOrder(OrderAnnotation.class)
public class Day10Test extends PuzzleTest {

	private final static String INPUT_FOLDER = TestFolder.DAY_10;

	@BeforeAll
	public static void beforeAll() {
		Timer.printHeader(TestDisplayName.DAY_10);
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
		
		CathodeRayTube cathodeRayTube = new CathodeRayTube(inputs);
		long result = cathodeRayTube.solve();
		
		assertEquals(13140, result);
		
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
		CathodeRayTube cathodeRayTube = new CathodeRayTube(inputs);
		long result = cathodeRayTube.solve();
		
		assertEquals(14540, result);
		
		/* Should also paint solution: EHZFZHCZ
		 * 
		 * ####.#..#.####.####.####.#..#..##..####.
		 * #....#..#....#.#.......#.#..#.#..#....#.
		 * ###..####...#..###....#..####.#......#..
		 * #....#..#..#...#.....#...#..#.#.....#...
		 * #....#..#.#....#....#....#..#.#..#.#....
		 * ####.#..#.####.#....####.#..#..##..####.
		 * 
		 */
		
	}
	
	@Test
	@Order(3)
	@Tag(TestTag.PART_TWO)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_TWO_SAMPLE)
	@Disabled
	public void testSolvePart2Sample() {
		
		// DOES NOT APPLY FOR THIS PUZZLE
		
	}
	
	@Test
	@Order(4)
	@Tag(TestTag.PART_TWO)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_TWO_INPUT)
	public void testSolvePart2Input() {
		
		// DOES NOT APPLY FOR THIS PUZZLE
		
	}

}
