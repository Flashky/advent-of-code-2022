package com.adventofcode.flashk.day05;

import java.util.Queue;
import java.util.Stack;

public abstract class CraneStrategy {

	public String solve(Stack<String>[] stacks, Queue<Movement> movements) {
		
		while(!movements.isEmpty()) {
			Movement movement = movements.poll();
			moveCrates(stacks, movement);
		}
		
		StringBuilder resultBuilder = new StringBuilder();
		for(int i = 0; i < stacks.length; i++) {
			resultBuilder.append(stacks[i].peek());
		}
		
		return resultBuilder.toString();
	}

	protected abstract void moveCrates(Stack<String>[] stacks, Movement movement);
}
