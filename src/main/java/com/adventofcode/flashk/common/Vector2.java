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
	
	/**
	 * Accepts coordinates in String format as "x,y" and creates a Vector2 from it.
	 * <pre>
	 * Vector2 vector1 = new Vector2("4,2");
	 * Vector2 vector2 = new Vector2(4,2);
	 * 
	 * vector1.equals(vector2) // true
	 * </pre>
	 * @param coordinates
	 * @return
	 */
	public Vector2(String coordinates) {
		
		String[] values = coordinates.split(",");
		
		this.x = Integer.parseInt(values[0]);
		this.y = Integer.parseInt(values[1]);
		
	}
	
	public void transform(int scalar) {
		this.x += scalar;
		this.y += scalar;
	}
	
	public void transformX(int scalar) {
		this.x += scalar;
	}
	
	public void transformY(int scalar) {
		this.y += scalar;
	}
	
	public void transform(Vector2 vector) {
		this.x += vector.x;
		this.y += vector.y;
	}
	
	
	public static Vector2 transform(Vector2 start, Vector2 end) {
	
		int x = start.x + end.x;
		int y = start.y + end.y;
		
		return new Vector2(x,y);
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
		double xs = x * s;
		double ys = y * s;
		
		// Use ceil for positive numbers and floor for negative numbers:
		// - Positive numbers such as 0.44 or 0.67 must be rounded to the highest integer (1) that means ceil.
		// - Negative numbers such as -0,44 or -0,67 must be rounded to the lowest integer (-1), that means floor.
		
		x = (xs >= 0) ? (int) Math.ceil(xs) : (int) Math.floor(xs);
		y = (ys >= 0) ? (int) Math.ceil(ys) : (int) Math.floor(ys);
				
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
		double xs = x * s;
		double ys = y * s;
		
		// Use ceil for positive numbers and floor for negative numbers:
		// - Positive numbers such as 0.44 or 0.67 must be rounded to the highest integer (1) that means ceil.
		// - Negative numbers such as -0,44 or -0,67 must be rounded to the lowest integer (-1), that means floor.
		
		int newX = (xs >= 0) ? (int) Math.ceil(xs) : (int) Math.floor(xs);
		int newY = (ys >= 0) ? (int) Math.ceil(ys) : (int) Math.floor(ys);
		
		return new Vector2(newX, newY);
	}
	
	/**
	 * Rotates the Vector2 90º clockwise.
	 */
	public void rotateRight() {
		
		//https://limnu.com/sketch-easy-90-degree-rotate-vectors/
		int newY = -this.x;
		this.x = this.y;
		this.y = newY;
	}
	
	/**
	 * Rotates the Vector2 90º counter-clockwise.
	 */
	public void rotateLeft() {
		int newX = -this.y;
		this.y = this.x;
		this.x = newX;
		
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
	
	/**
	 * Substracts the right operand vector to the left operand vector.
	 * <p>
	 * Examples <code>(x,y)</code>:
	 * </p>
	 * <pre>
	 * (0,14) - (0,7) = <b>(0,7)</b>
	 * (0,7) - (0,14) = <b>(0,-7)</b>
	 * (11,0) - (5,0) = <b>(6,0)</b>
	 * (5,0) - (11,0) = <b>(-6,0)</b>
	 * </code>
	 *  
	 * </p>
	 * @param leftOperand Vector2 to substract from.
	 * @param rightOperand substracting Vector2.
	 * @return a new Vector2
	 */
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
	
	public static int manhattanDistance(Vector2 a, Vector2 b) {
		
		// Given (x1,y1) and (x2,y2): 
		// Manhattan distance = |x1-x2| + |y1-y2|
		int xDistance = Math.abs(a.getX()-b.getX());
		int yDistance = Math.abs(a.getY()-b.getY());
		
		return xDistance + yDistance;
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
