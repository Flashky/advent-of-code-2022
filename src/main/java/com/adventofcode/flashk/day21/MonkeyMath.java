package com.adventofcode.flashk.day21;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MonkeyMath {

	private static final String MONKEY_NUMBER_REGEX = "([a-z]*): (\\d*)";
	private static final String MONKEY_OPERATION_REGEX = "([a-z]*): ([a-z]*) ([-+*\\/]) ([a-z]*)";
	private static final Pattern MONKEY_NUMBER_PATTERN = Pattern.compile(MONKEY_NUMBER_REGEX);
	private static final Pattern MONKEY_OPERATION_PATTERN = Pattern.compile(MONKEY_OPERATION_REGEX);
	
	private Monkey root;
	
	public MonkeyMath(List<String> inputs) {
		
		Map<String, Monkey> monkeys = new HashMap<>();
		
		// Create all monkeys
		for(String input : inputs) {
			Matcher numberMatcher = MONKEY_NUMBER_PATTERN.matcher(input);
			Matcher operationMatcher = MONKEY_OPERATION_PATTERN.matcher(input);
			
			if(numberMatcher.matches()) {
				createMonkeyNumber(monkeys, numberMatcher);
			} else if(operationMatcher.matches()) {
				createMonkeyOperation(monkeys, operationMatcher);
			}
			
		}
		
		// Assign root monkey
		root = monkeys.get("root");
		
	}

	public long solveA() {
		return root.operate();
	}
	
	public long solveB() {
		// Override root operation
		root.setOperation(Monkey.EQUALS);
		return root.operate();
	}
	private void createMonkeyNumber(Map<String, Monkey> monkeys, Matcher numberMatcher) {
		// Monkey number
		String name = numberMatcher.group(1);
		long number = Long.parseLong(numberMatcher.group(2));
		
		// The monkey might have been already created
		if(monkeys.containsKey(name)) {
			monkeys.get(name).setNumber(number);
		} else {
			monkeys.put(name, new Monkey(name, number));
		}
	}
	
	private void createMonkeyOperation(Map<String, Monkey> monkeys, Matcher operationMatcher) {
		
		String name = operationMatcher.group(1);
		String leftName = operationMatcher.group(2);
		String operation = operationMatcher.group(3);
		String rightName = operationMatcher.group(4);
		
		Monkey leftMonkey = null;
		Monkey rightMonkey = null;
		
		// Left monkey
		if(monkeys.containsKey(leftName)) {
			leftMonkey = monkeys.get(leftName);
		} else {
			leftMonkey = new Monkey(leftName);
			monkeys.put(leftName, leftMonkey);
		}
		
		// Right monkey	
		if(monkeys.containsKey(rightName)) {
			rightMonkey = monkeys.get(rightName);
		} else {
			rightMonkey = new Monkey(rightName);
			monkeys.put(rightName, rightMonkey);
		}
		
		// Now create current monkey
		
		// Monkey might have been already created
		if(monkeys.containsKey(name)) {
			Monkey monkey = monkeys.get(name);
			monkey.setLeft(leftMonkey);
			monkey.setRight(rightMonkey);
			monkey.setOperation(operation);
		} else {
			Monkey monkey = new Monkey(name, operation, leftMonkey, rightMonkey);
			monkeys.put(name, monkey);
		}
	}


}
