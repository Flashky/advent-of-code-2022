package com.adventofcode.flashk.day21;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Monkey {

	// Names
	public static final String HUMAN_NAME = "humn";
	public static final String ROOT_NAME = "root";
	
	// Operations
	public static final String ADD = "+";
	public static final String SUBSTRACT = "-";
	public static final String MULTIPLY = "*";
	public static final String DIVIDE = "/";
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
	
	/**
	 * Traverses monkey expression tree to apply all the operations.
	 * @return the result of all math operations.
	 */
	public long operate() {
		
		if(operation == null) {
			return number;
		}
		
		long leftResult = left.operate();
		long rightResult = right.operate();
		
		switch(operation) {
			case ADD: return leftResult + rightResult;
			case SUBSTRACT: return leftResult - rightResult;
			case MULTIPLY: return leftResult * rightResult;
			case DIVIDE: return leftResult / rightResult;
			default:
				throw new UnsupportedOperationException("Operation "+operation+" is not supported");
		}
	}
	
	/**
	 * Traverses monkey expression tree to solve the operation equation.
	 * @return the solved x value.
	 */
	public long operateEquation() {
		Equation result = this.findNumberToYell();
		return (long) result.getNumberFactor();
	}
	
	private Equation findNumberToYell() {
		
		if(HUMAN_NAME.equals(name)) {
			return new Equation(0,1);
		} else if(operation == null) {
			return new Equation(number,0);
		}
		
		Equation leftEquation = left.findNumberToYell();
		Equation rightEquation = right.findNumberToYell();
	
		switch(operation) {
			case ADD: return Equation.add(leftEquation, rightEquation);
			case SUBSTRACT: return Equation.substract(leftEquation, rightEquation);
			case MULTIPLY: return Equation.multiply(leftEquation, rightEquation);
			case DIVIDE: return Equation.divide(leftEquation, rightEquation);
			case EQUALS: return Equation.solve(leftEquation, rightEquation);
			default:
				throw new UnsupportedOperationException("Operation "+operation+" is not supported");
		}
		
	}

}
