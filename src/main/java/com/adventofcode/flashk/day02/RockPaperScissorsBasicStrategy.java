package com.adventofcode.flashk.day02;

import java.util.List;

public class RockPaperScissorsBasicStrategy extends RockPaperScissorsStrategy {

	public RockPaperScissorsBasicStrategy(List<String> inputs) {
		super(inputs);
	}

	@Override
	public int[][] initializeResultMatrix() {
		return new int[][] {{4,8,3},{1,5,9},{7,2,6}};
	}

}
