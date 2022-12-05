package com.adventofcode.flashk.day05;

import java.util.Stack;

public class CraneBasicStrategy extends CraneStrategy {

	@Override
	protected void  moveCrates(Stack<String>[] stacks, Movement movement) {
		
		Stack<String> stackFrom = stacks[movement.getStackIndexFrom()];
		Stack<String> stackTo = stacks[movement.getStackIndexTo()];
		
		for(int i = 0; i < movement.getNumberOfCrates(); i++) {
			stackTo.add(stackFrom.pop());
		}
	}

}
