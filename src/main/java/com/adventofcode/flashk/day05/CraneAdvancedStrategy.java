package com.adventofcode.flashk.day05;

import java.util.Stack;

public class CraneAdvancedStrategy extends CraneStrategy {

	@Override
	protected void moveCrates(Stack<String>[] stacks, Movement movement) {
		
		Stack<String> stackFrom = stacks[movement.getStackIndexFrom()];
		Stack<String> stackTo = stacks[movement.getStackIndexTo()];
		Stack<String> crateBlock = new Stack<>();
		
		for(int i = 0; i < movement.getNumberOfCrates(); i++) {
			crateBlock.add(stackFrom.pop());
		}
		
		while(!crateBlock.isEmpty()) {
			stackTo.add(crateBlock.pop());
		}
	}
}
