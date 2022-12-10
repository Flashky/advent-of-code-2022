package com.adventofcode.flashk.common;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Vector2 {

	private int x;
	private int y;
	
	public Vector2(Vector2 anotherVector) {
		this.x = anotherVector.x;
		this.y = anotherVector.y;
	}
	
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
	
	/**
	 * Modifies this vector to have a magnitude of 1.
	 * 
	 * <p>This function <strong>will modify</strong> the current vector.
	 * Use {@link #normalized()} if you want to keep current vector unchanged.<p>
	 */
	public void normalize() {
		
		double length = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		
		if(length == 0) {
			throw new IllegalStateException("A zero vector (0,0) cannot be normalized");
		}
		
		double s = 1 / length;
		x = (int) Math.round(x * s);
		y = (int) Math.round(y * s);
				
	}
	
	/**
	 * Returns a copy of this vector with a magnitude of 1.
	 * <p>
	 * This function <strong>will NOT modify</strong> the current vector.
	 * Use {@link #normalize()} if you want to modify the current vector.
	 * </p>
	 * @return a normalized version of the current vector.
	 */
	public Vector2 normalized() {
		
		double length = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		
		if(length == 0) {
			throw new IllegalStateException("A zero vector (0,0) cannot be normalized");
		}
		
		double s = 1 / length;
		int newX = (int) Math.round(x * s);
		int newY = (int) Math.round(y * s);
		
		return new Vector2(newX, newY);
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
	
	public static Vector2 substract(Vector2 leftOperand, Vector2 rightOperand) {
		int x = leftOperand.x - rightOperand.x;
		int y = leftOperand.y - rightOperand.y;
		return new Vector2(x,y);
	}
	
	/**
	 * Shorthand for <code>Vector2(1,0)</code>.
	 * @return A unitary vector that points to the right.
	 */
	public static Vector2 right() {
		return new Vector2(1,0);
	}
	
	public static double distance(Vector2 a, Vector2 b) {
		
		int xDiff = b.x - a.x;
		int yDiff = b.y - a.y;
		
		return Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
	}
	/**
	 * Shorthand for <code>Vector2(-1,0)</code>.
	 * @return A unitary vector that points to the left.
	 */
	public static Vector2 left() {
		return new Vector2(-1,0);
	}
	
	/**
	 * Shorthand for <code>Vector2(0,1)</code>.
	 * @return A unitary vector that points up.
	 */
	public static Vector2 up() {
		return new Vector2(0,1);
	}
	
	/**
	 * Shorthand for <code>Vector2(0,-1)</code>.
	 * @return A unitary vector that points down.
	 */
	public static Vector2 down() {
		return new Vector2(0,-1);
	}

	/**
	 * Shorthand for <code>Vector2(1,1)</code>.
	 * @return A unitary vector that points to the up right diagonal.
	 */
	public static Vector2 upRight() {
		return new Vector2(1,1);
	}

	/**
	 * Shorthand for <code>Vector2(-1,1)</code>.
	 * @return A unitary vector that points to the up left diagonal.
	 */
	public static Vector2 upLeft() {
		return new Vector2(-1,1);
	}

	/**
	 * Shorthand for <code>Vector2(1,-1)</code>.
	 * @return A unitary vector that points to the down right diagonal.
	 */
	public static Vector2 downRight() {
		return new Vector2(1,-1);
	}

	/**
	 * Shorthand for <code>Vector2(-1,-1)</code>.
	 * @return A unitary vector that points to the down left diagonal.
	 */
	public static Vector2 downLeft() {
		return new Vector2(-1,-1);
	}
	
}
