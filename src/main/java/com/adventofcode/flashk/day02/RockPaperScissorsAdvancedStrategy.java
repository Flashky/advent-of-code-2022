package com.adventofcode.flashk.day02;

import java.util.List;

public class RockPaperScissorsAdvancedStrategy extends RockPaperScissorsStrategy {

	public RockPaperScissorsAdvancedStrategy(List<String> inputs) {
		super(inputs);
	}

	@Override
	public int[][] initializeResultMatrix() {
		return new int[][] {{3,4,8},{1,5,9},{2,6,7}};
	}

}
