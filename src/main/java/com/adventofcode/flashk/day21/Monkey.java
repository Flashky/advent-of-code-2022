package com.adventofcode.flashk.day21;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Monkey {

	private static final String ADD = "+";
	private static final String SUBSTRACT = "-";
	private static final String MULTIPLY = "*";
	private static final String DIVIDE = "/";
	public static final String EQUALS = "=";
	
	private String name;
	private String operation;
	private long number;
	
	private Monkey left = null;
	private Monkey right = null;
	
	public Monkey(String name) {
		this.name = name;
	}
	
	public Monkey(String name, long number) {
		this.name = name;
		this.number = number;
	}
	
	public Monkey(String name, String operation, Monkey left, Monkey right) {
		
		this.name = name;
		this.operation = operation;
		this.left = left;
		this.right = right;
		
	}
	
	public long operate() {
		
		if(operation == null) {
			return number;
		}
		
		long leftResult = left.operate();
		long rightResult = right.operate();
		
		switch(operation) {
			case ADD: return leftResult+rightResult;
			case SUBSTRACT: return leftResult-rightResult;
			case MULTIPLY: return leftResult*rightResult;
			case DIVIDE: return leftResult/rightResult;
			case EQUALS: return leftResult == rightResult ? 1 : 0;
			default:
				throw new UnsupportedOperationException("Operation "+operation+" is not supported");
		}
	}
}
