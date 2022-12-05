package com.adventofcode.flashk.day05;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

public class SupplyStacks {

	protected Stack<String>[] stacks;
	protected Queue<Movement> movements = new LinkedList<>();
	
	public SupplyStacks(List<String> inputs) {
		Stack<String> initialCrates = new Stack<>();
		boolean initStacks = false;
		
		for(String input : inputs) {
			
			if(!initStacks && StringUtils.isNotBlank(input)){
				initialCrates.add(input);	
			}
			
			if(StringUtils.isBlank(input)) {
				initStacks = true; 
				initializeStacks(initialCrates);
			} else if(initStacks) {
				movements.add(new Movement(input));
			}

		}
	}
	
	@SuppressWarnings("unchecked")
	private void initializeStacks(Stack<String> initialCrates) {
		
		// Create all the needed stacks
		String header = initialCrates.pop();
		header = RegExUtils.removeAll(header, StringUtils.SPACE);
		stacks = new Stack[header.length()];
		
		// Add initial items to the stacks
		while(!initialCrates.isEmpty()) {
			
			String currentLevel = initialCrates.pop();
			int columnIndex = 1;
			int currentStackIndex = 0;
			while(columnIndex < currentLevel.length()) {
				
				char crate = currentLevel.charAt(columnIndex);
				if(crate != ' ') {
					if(stacks[currentStackIndex] == null) {
						stacks[currentStackIndex] = new Stack<>();
					}
					stacks[currentStackIndex].add(String.valueOf(crate));
				}
				
				columnIndex += 4;
				currentStackIndex++;
			}
		}
	}
	
	public String solve(CraneStrategy strategy) {
		return strategy.solve(stacks, movements);
		

	}

}
