package com.adventofcode.flashk.day11;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.adventofcode.flashk.common.Pair;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Monkey implements Comparable<Monkey> {

	private static final String ITEMS_REGEX = "(\\d+)";
	private final static Pattern ITEMS_PATTERN  = Pattern.compile(ITEMS_REGEX);
	
	private static final String OPERATION_REGEX = "Operation: new = old (\\+|\\*) (\\d*)|(old)";
	private final static Pattern OPERATION_PATTERN  = Pattern.compile(OPERATION_REGEX);
	
	private static final String TEST_DIVISIBLE_REGEX = "Test: divisible by (\\d+)";
	private final static Pattern TEST_DIVISIBLE_PATTERN  = Pattern.compile(TEST_DIVISIBLE_REGEX);
	
	private static final String MONKEY_REGEX = "(\\d+)";
	private final static Pattern MONKEY_PATTERN  = Pattern.compile(MONKEY_REGEX);
	
	private static final char MULTIPLY = '*';
	
	private Queue<Integer> items = new LinkedList<>();

	private char operation;
	private boolean operandOld;
	private int operand;
	
	private int divisor;
	private int trueMonkey;
	private int falseMonkey;
	
	private int countedItems;
	
	@Setter
	private int lcm;
	
	public Monkey(List<String> inputs) {		
		
		// Starting items: 1
		// Operation: 2
		// Test: 3
		// True monkey: 4
		// False monkey: 5
		
		initializeItems(inputs.get(1));
		initializeOperation(inputs.get(2));
		initializeTest(inputs);
		
		System.out.println("test");

	}

	private void initializeItems(String inputItems) {
		
		Matcher matcher = ITEMS_PATTERN.matcher(inputItems);
		
		while(matcher.find()) {
			items.add(Integer.parseInt(matcher.group(1)));
		}
		
	}

	private void initializeOperation(String inputOperation) {
		Matcher matcher = OPERATION_PATTERN.matcher(inputOperation);
		matcher.find();
		
		operation = matcher.group(1).toCharArray()[0];
		String operandValue = matcher.group(2);
		
		if(StringUtils.isNotBlank(operandValue)) {
			operandOld = false;
			operand = Integer.parseInt(operandValue);
		} else {
			operandOld = true;
		}

	}
	
	private void initializeTest(List<String> inputs) {
		
		Matcher matcher = TEST_DIVISIBLE_PATTERN.matcher(inputs.get(3));
		matcher.find();
		
		divisor = Integer.parseInt(matcher.group(1));
		trueMonkey = getMonkeyValue(inputs.get(4));
		falseMonkey = getMonkeyValue(inputs.get(5));

	}
	
	private int getMonkeyValue(String monkeyInput) {
		Matcher matcher = MONKEY_PATTERN.matcher(monkeyInput);
		matcher.find();
		
		return Integer.parseInt(matcher.group(1));
	}
	
	public boolean hasItems() {
		return !this.items.isEmpty();
	}
	
	public Pair<Integer,Integer> throwItem(boolean useRelief) {
		
		// Inspect item
		Integer worryLevel = items.poll();
		countedItems++;
		
		// Calculate worry level
		if(!operandOld) {
			if(operation == MULTIPLY) {
				worryLevel *= operand;
			} else {
				worryLevel += operand;
			}
		} else {
			if(operation == MULTIPLY) {
				worryLevel *= worryLevel;
			} else {
				worryLevel += worryLevel;
			}
		}
		
		// Relief
		 // TODO Atención, debe redondearse al entero más cercano
		// 1501 -> 1501 / 3 = 500
		// 60 -> 60 / 3  = 20
		if(useRelief) {
			worryLevel = worryLevel / 3;
		}
		
		// Test
		long test = worryLevel % divisor;
		if(test == 0) {
			return new Pair<Integer,Integer>(trueMonkey, worryLevel);
		} else {
			return new Pair<Integer,Integer>(falseMonkey, worryLevel);
		}

	}

	@Override
	public int compareTo(Monkey another) {
		return Integer.compare(this.countedItems, another.countedItems);
	}
}
