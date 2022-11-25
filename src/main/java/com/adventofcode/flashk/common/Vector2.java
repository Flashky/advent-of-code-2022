package com.adventofcode.flashk.common;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Vector2 {

	private int x;
	private int y;
	
	public void transform(Vector2 vector) {
		this.x += vector.x;
		this.y += vector.y;
	}
	
	
	/**
	 * Substracts the right operand vector to the left operand vector, applying absolute value to the result.
	 *  
	 * <p>
	 * Examples <code>(x,y)</code>:
	 * </p>
	 * <pre>
	 * |(0,14) - (0,7)| = |(0,7)| 	= <b>(0,7)</b>
	 * |(0,7) - (0,14)| = |(0,-7)| 	= <b>(0,7)</b>
	 * |(11,0) - (5,0)| = |(6,0)| 	= <b>(6,0)</b>
	 * |(5,0) - (11,0)| = |(-6,0)| 	= <b>(6,0)</b>
	 * </code>
	 *  
	 * </p>
	 * @param other substracting Vector2.
	 * @return a new Vector2
	 */
	public void substractAbs(Vector2 other) {
		
		this.x = Math.abs(this.x - other.x);
		this.y = Math.abs(this.y - other.y);
		
	}
	
	public void normalize() {
		
		double length = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		
		if(length != 0) {
			double s = 1 / length;
			x = (int) Math.round(x * s);
			y = (int) Math.round(y * s);
		}
		
	}
	
	// Static operations
	
	/**
	 * Substracts the right operand vector to the left operand vector, applying absolute value to the result.
	 *  
	 * <p>
	 * Examples <code>(x,y)</code>:
	 * </p>
	 * <pre>
	 * |(0,14) - (0,7)| = |(0,7)| 	= <b>(0,7)</b>
	 * |(0,7) - (0,14)| = |(0,-7)| 	= <b>(0,7)</b>
	 * |(11,0) - (5,0)| = |(6,0)| 	= <b>(6,0)</b>
	 * |(5,0) - (11,0)| = |(-6,0)| 	= <b>(6,0)</b>
	 * </code>
	 *  
	 * </p>
	 * @param leftOperand Vector2 to substract from.
	 * @param rightOperand substracting Vector2.
	 * @return a new Vector2
	 */
	public static Vector2 substractAbs(Vector2 leftOperand, Vector2 rightOperand) {
		
		int x = Math.abs(leftOperand.x - rightOperand.x);
		int y = Math.abs(leftOperand.y - rightOperand.y);
		
		return new Vector2(x,y);
		
	}
	
	
}
