package com.adventofcode.flashk.day11;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;


import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class Monkey implements Comparable<Monkey> {

	private static final String NUMBER_REGEX = "(\\d+)";
	private final static Pattern NUMBER_PATTERN  = Pattern.compile(NUMBER_REGEX);
	
	private static final String OPERATION_REGEX = "Operation: new = old (\\+|\\*) (\\d*)|(old)";
	private final static Pattern OPERATION_PATTERN  = Pattern.compile(OPERATION_REGEX);
	
	private static final char MULTIPLY = '*';
	private static final int BASIC_RELIEF = 3;
	
	@Getter
	private Queue<Long> items = new LinkedList<>();
	
	@Getter
	private long countedItems;
	
	@Getter
	private int divisor;
	
	@Setter
	private long lcm;
	
	private char operation;
	private boolean selfOperand;
	private long operand;
	private int trueMonkey;
	private int falseMonkey;
	

	
	public Monkey(List<String> inputs) {		
		
		initializeItems(inputs.get(1));
		initializeOperation(inputs.get(2));
		initializeTest(inputs);

	}
	
	public boolean hasItems() {
		return !this.items.isEmpty();
	}
	
	public Pair<Integer,Long> throwItem(boolean useRelief) {
		
		// Inspect item
		long worryLevel = items.poll();
		countedItems++;
		
		// Calculate worry level
		if(selfOperand) {
			operand = worryLevel;
		}
		
		if(operation == MULTIPLY) {
			worryLevel *= operand;
		} else {
			worryLevel += operand;
		}

		// Apply relief
		if(useRelief) {
			worryLevel = worryLevel / BASIC_RELIEF;
		} else {
			worryLevel = worryLevel % lcm;
		}
		
		// Test
		long test = worryLevel % divisor;
		
		if(test == 0) {
			return ImmutablePair.of(trueMonkey, worryLevel);
		} else {
			return ImmutablePair.of(falseMonkey, worryLevel);
		}

	}

	@Override
	public int compareTo(Monkey another) {
		return Long.compare(this.countedItems, another.countedItems);
	}
	
	// Initialization functions
	
	private void initializeItems(String inputItems) {
		
		Matcher matcher = NUMBER_PATTERN.matcher(inputItems);
		
		while(matcher.find()) {
			items.add(Long.parseLong(matcher.group(1)));
		}
		
	}

	private void initializeOperation(String inputOperation) {
		Matcher matcher = OPERATION_PATTERN.matcher(inputOperation);
		matcher.find();
		
		operation = matcher.group(1).toCharArray()[0];
		String operandValue = matcher.group(2);
		
		if(StringUtils.isNotBlank(operandValue)) {
			selfOperand = false;
			operand = Integer.parseInt(operandValue);
		} else {
			selfOperand = true;
		}

	}
	
	private void initializeTest(List<String> inputs) {
		
		Matcher matcher = NUMBER_PATTERN.matcher(inputs.get(3));
		matcher.find();
		
		divisor = Integer.parseInt(matcher.group(1));
		trueMonkey = getMonkeyValue(inputs.get(4));
		falseMonkey = getMonkeyValue(inputs.get(5));

	}
	
	private int getMonkeyValue(String monkeyInput) {
		Matcher matcher = NUMBER_PATTERN.matcher(monkeyInput);
		matcher.find();
		
		return Integer.parseInt(matcher.group(1));
	}
}
