package com.adventofcode.flashk.day22;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.adventofcode.flashk.common.Vector2;

public class Cube {

	public static final int SAMPLE_CUBE_SIZE = 4;
	public static final int INPUT_CUBE_SIZE = 50;
	
	
	private Side side1;
	private Side side2;
	private Side side3;
	private Side side4;
	private Side side5;
	private Side side6;
	
	private int cubeSize;
	
	// Current movement attributes
	private Side activeSide;
	
	private Map<Character, Long> facingValues = new HashMap<>();
	
	public Cube(List<String> inputs, boolean isSample) {
		
		if(isSample) {
			initializeSampleCube(inputs);
		} else {
			initializeInputCube(inputs);
		}
		
		activeSide = side1;
		
		// Facing values
		facingValues.put(Side.FACING_RIGHT, 0L);
		facingValues.put(Side.FACING_DOWN, 1L);
		facingValues.put(Side.FACING_LEFT, 2L);
		facingValues.put(Side.FACING_UP, 3L);
	}
	
	
	public void move(int distance) {
		activeSide = activeSide.move(distance);
	}
	
	public void rotate(char rotation) {
		activeSide.rotate(rotation);
	}
	
	public long password() {
		
		Vector2 absolutePosition = activeSide.getAbsolutePosition();
		
		long rowValue = 1000L * (long) (absolutePosition.getY()+1);
		long colValue = 4L * (long) (absolutePosition.getX()+1);
		long facingValue = facingValues.get(activeSide.getFacingDirection());
		
		return rowValue + colValue + facingValue;
		
	}
	
	private void initializeSampleCube(List<String> inputs) {

		cubeSize = SAMPLE_CUBE_SIZE;

		side1 = new Side(inputs, 0, 8, cubeSize);
		side2 = new Side(inputs, 4, 0, cubeSize);
		side3 = new Side(inputs, 4, 4, cubeSize);
		side4 = new Side(inputs, 4, 8, cubeSize);
		side5 = new Side(inputs, 8, 8, cubeSize);
		side6 = new Side(inputs, 8, 12, cubeSize);
		
		// Initialize sides
		side1.setId(1);
		side1.setLeft(side3);
		side1.setRight(side6);
		side1.setUp(side2);
		side1.setDown(side4);
		
		side2.setId(2);
		side2.setLeft(side6);
		side2.setRight(side3);
		side2.setUp(side1);
		side2.setDown(side5);
		
		side3.setId(3);
		side3.setLeft(side2);
		side3.setRight(side4);
		side3.setUp(side1);
		side3.setDown(side5);
		
		side4.setId(4);
		side4.setLeft(side3);
		side4.setRight(side6);
		side4.setUp(side1);
		side4.setDown(side5);
		
		side5.setId(5);
		side5.setLeft(side3);
		side5.setRight(side6);
		side5.setUp(side4);
		side5.setDown(side2);
		
		side6.setId(6);
		side6.setLeft(side5);
		side6.setRight(side1);
		side6.setUp(side4);
		side6.setDown(side2);
	}
	
	private void initializeInputCube(List<String> inputs) {

		cubeSize = INPUT_CUBE_SIZE;
		
		side1 = new Side(inputs, 0, 50, cubeSize);
		side2 = new Side(inputs, 0, 100, cubeSize);
		side3 = new Side(inputs, 50, 50, cubeSize);
		side4 = new Side(inputs, 100, 0, cubeSize);
		side5 = new Side(inputs, 100, 50, cubeSize);
		side6 = new Side(inputs, 150, 0, cubeSize);
		
		// Initialize sides
		side1.setId(1);
		side1.setLeft(side4);
		side1.setRight(side2);
		side1.setUp(side6);
		side1.setDown(side3);
		
		side2.setId(2);
		side2.setLeft(side1);
		side2.setRight(side5);
		side2.setUp(side6);
		side2.setDown(side3);
		
		side3.setId(3);
		side3.setLeft(side4);
		side3.setRight(side2);
		side3.setUp(side1);
		side3.setDown(side5);
		
		side4.setId(4);
		side4.setLeft(side1);
		side4.setRight(side5);
		side4.setUp(side3); 
		side4.setDown(side6);
		
		side5.setId(5);
		side5.setLeft(side4); 
		side5.setRight(side2); 
		side5.setUp(side3);
		side5.setDown(side6);
		
		side6.setId(6);
		side6.setLeft(side1);
		side6.setRight(side5);
		side6.setUp(side4);
		side6.setDown(side2);
		
	}
	
}
