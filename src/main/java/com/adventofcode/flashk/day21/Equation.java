package com.adventofcode.flashk.day21;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Equation {

	private double numberFactor;
	private double xFactor;
	
	public boolean hasX() {
		return xFactor != 0;
	}
	
	public static Equation add(Equation left, Equation right) {
		
		double numberFactor = left.getNumberFactor() + right.getNumberFactor();
		double xFactor = left.getXFactor() + right.getXFactor();
		
		return new Equation(numberFactor, xFactor);
	}
	
	public static Equation substract(Equation left, Equation right) {
		
		double numberFactor = left.getNumberFactor() - right.getNumberFactor();
		double xFactor = left.getXFactor() - right.getXFactor();
		
		return new Equation(numberFactor, xFactor);
	}
	
	public static Equation multiply(Equation left, Equation right) {
		
		double numberFactor = left.getNumberFactor() * right.getNumberFactor();
		double xFactor;
		
		// At most, only one of the equation will have X.
		if(left.hasX()) {
			xFactor = left.getXFactor() * right.getNumberFactor();
		} else if(right.hasX()){
			xFactor = right.getXFactor() * left.getNumberFactor();
		} else {
			xFactor = 0;
		}
		
		return new Equation(numberFactor, xFactor);
	}
	
	public static Equation divide(Equation left, Equation right) {
		
		double numberFactor = left.getNumberFactor() / right.getNumberFactor();
		double xFactor = 0;
		if(left.hasX()) {
			xFactor = left.getXFactor() / right.getNumberFactor();
		} else if (right.hasX()) {
			throw new UnsupportedOperationException("Cannot solve equation with x on divisor");
		}
		
		return new Equation(numberFactor, xFactor);
		
	}
	
	public static Equation solve(Equation left, Equation right) {
		
		if(left.hasX()) {
			double numberFactor = right.getNumberFactor() - left.getNumberFactor();
			double divisor = left.getXFactor();
			double result = numberFactor / divisor;
			return new Equation(result, 0);
		} else {
			double numberFactor = left.getNumberFactor() - right.getNumberFactor();
			double divisor = right.getXFactor();
			double result = numberFactor / divisor;
			return new Equation(result, 0);
		}
	}
}

