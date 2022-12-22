package com.adventofcode.flashk.day22;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.adventofcode.flashk.common.Vector2;

public class MonkeyMap {

	private static final String MOVEMENT_REGEX = "(\\d*)";
	private static final Pattern MOVEMENT_PATTERN = Pattern.compile(MOVEMENT_REGEX);
	
	private static final String ROTATION_REGEX = "(R|L)";
	private static final Pattern ROTATION_PATTERN = Pattern.compile(ROTATION_REGEX);
	
	private static final int NOT_SET = -1;
	private static final char EMPTY = ' ';
	private static final char PATH = '.';
	private static final char WALL = '#';
	
	private static final char FACING_RIGHT = '>';
	private static final char FACING_LEFT = '<';
	private static final char FACING_UP = '^';
	private static final char FACING_DOWN = 'v';
	
	private static final char ROTATE_RIGHT = 'R';
	private static final char ROTATE_LEFT = 'L';
	
	// QUIERO un Vector2 de dirección
	// PARA saber en qué dirección moverme en cada momento
	
	// ALGORITMOS
	
	// [x] Parse del input
	// [x] Algoritmo de comprobación de rangos
	// [ ] Algoritmo de movimiento vertical / horizontal
	// [s] Algoritmo de rotación
	// [x] Algoritmo de password

	// Instructions
	private Queue<Integer> movements = new LinkedList<>();	
	private Queue<Character> rotations = new LinkedList<>();
	
	// Map
	private int rows;
	private int cols;
	private char[][] map;

	// Ranges // TODO quizá sería mejor un mapa Map<Integer, Range> siendo el primer valor, la fila/columna
	private Map<Integer,Range> horizontalRanges = new HashMap<>();
	private Map<Integer,Range> verticalRanges = new HashMap<>();
	
	
	// Position and direction
	private Vector2 position = null;
	private Vector2 direction = Vector2.right();
	private char facingDirection = FACING_RIGHT;
	private Map<Character, Long> facingValues = new HashMap<>();
	
	// Cuidado: En el mapa 'y' crece hacia abajo, por lo que rotar a la izquierda se convierte en rotar a la derecha y viceversa.
	
	public MonkeyMap(List<String> inputs) {
		
		// Initialize movement instructions
		String instructions = inputs.get(inputs.size()-1);
		initializeMovements(instructions);
		initializeRotations(instructions);
		
		// Remove last two lines
		inputs.remove(inputs.size()-1);
		inputs.remove(inputs.size()-1);
		
		// Initialize map and ranges
		initializeMap(inputs);
		initializeRanges();
		
		// Initialize position
		int x = inputs.get(0).indexOf(PATH);
		
		position = new Vector2(x,0);
		
		// Facing values
		facingValues.put(FACING_RIGHT, 0L);
		facingValues.put(FACING_DOWN, 1L);
		facingValues.put(FACING_LEFT, 2L);
		facingValues.put(FACING_UP, 3L);
		
	}


	public long solveA() {
		
		// Initial position trace painting
		map[position.getY()][position.getX()] = FACING_RIGHT; 
		
		while(!movements.isEmpty()) {
			
			int distance = movements.poll();
			
			if(facingDirection == FACING_RIGHT) {
				
				// Move right while possible
				Optional<Vector2> right = moveRight();
				while(distance > 0 && right.isPresent()) {
					map[position.getY()][position.getX()] = FACING_RIGHT;
					position = right.get();
					right = moveRight();
					distance--;
				}
				
			} else if (facingDirection == FACING_DOWN){

				// Move down while possible
				Optional<Vector2> down = moveDown();
				while(distance > 0 && down.isPresent()) {
					map[position.getY()][position.getX()] = FACING_DOWN;
					position = down.get();
					down = moveDown();
					distance--;
				}
				
			} else if (facingDirection == FACING_LEFT) {
				
				// Move left while possible
				Optional<Vector2> left = moveLeft();
				while(distance > 0 && left.isPresent()) {
					map[position.getY()][position.getX()] = FACING_LEFT;
					position = left.get();
					left = moveLeft();
					distance--;
				}
				
			} else if (facingDirection == FACING_UP) {
				
				// Move up while possible
				Optional<Vector2> up = moveUp();
				while(distance > 0 && up.isPresent()) {
					map[position.getY()][position.getX()] = FACING_UP;
					position = up.get();
					up = moveUp();
					distance--;
				}
			}
			
			// Once movements have finished or stopped against a wall, rotate
			rotate();
			
		}
		
		return password();
	}

	private void rotate() {
		
		if(!rotations.isEmpty()) {
			Character rotation = rotations.poll();
			
			if(rotation == ROTATE_LEFT) {
				direction.rotateRight();
			} else {
				direction.rotateLeft();	
			}
			
			updateFacingValue();
		}
	}
	
	/**
	 * Attempts to move right.
	 * @return an Optional filled with the new Vector2 position if the movement is possible. {@link Optional#empty()} otherwise.
	 */
	private Optional<Vector2> moveRight() {
		Range maxRange = horizontalRanges.get(position.getY());
		int newX = maxRange.mod(position.getX()+1);
		
		Vector2 newPosition = map[position.getY()][newX] == PATH ? new Vector2(newX, position.getY()) : null;
		return Optional.ofNullable(newPosition);
	}

	/**
	 * Attempts to move down.
	 * @return an Optional filled with the new Vector2 position if the movement is possible. {@link Optional#empty()} otherwise.
	 */
	private Optional<Vector2> moveDown() {
		Range maxRange = verticalRanges.get(position.getX());
		int newY = maxRange.mod(position.getY()+1);
		
		Vector2 newPosition = map[newY][position.getX()] == PATH ? new Vector2(position.getX(), newY) : null;
		return Optional.ofNullable(newPosition);
	}
	

	/**
	 * Attempts to move left.
	 * @return an Optional filled with the new Vector2 position if the movement is possible. {@link Optional#empty()} otherwise.
	 */
	private Optional<Vector2> moveLeft() {
		Range maxRange = horizontalRanges.get(position.getY());
		int newX = maxRange.mod(position.getX()-1);
		
		Vector2 newPosition = map[position.getY()][newX] == PATH ? new Vector2(newX, position.getY()) : null;
		return Optional.ofNullable(newPosition);
	}
	
	/**
	 * Attempts to move up.
	 * @return an Optional filled with the new Vector2 position if the movement is possible. {@link Optional#empty()} otherwise.
	 */
	private Optional<Vector2> moveUp() {
		Range maxRange = verticalRanges.get(position.getX());
		int newY = maxRange.mod(position.getY()-1);
		
		Vector2 newPosition = map[newY][position.getX()] == PATH ? new Vector2(position.getX(), newY) : null;
		return Optional.ofNullable(newPosition);
	}

	/**
	 * Calculates the password
	 * <p>Password is the sum of:</p>
	 * <ul>
	 * 	<li>1000 times the row.</li>
	 * 	<li>4 times the column.</li>
	 * 	<li>Facing value (>: 0, v: 1, <: 2, ^3).</li>
	 * </ul>
	 * <p>Position (row and column) is 1-index based, so if the final position is 0,0, then row and column will be 1,1.</p>
	 * @return
	 */
	private long password() {
		
		long rowValue = 1000L * (long) (position.getY()+1);
		long colValue = 4L * (long) (position.getX()+1);
		long facingValue = facingValues.get(facingDirection);
		
		return rowValue + colValue + facingValue;
	}
	

	private void initializeMovements(String instructions) {
		Matcher matcher = MOVEMENT_PATTERN.matcher(instructions);
		
		while(matcher.find()) {
			String number = matcher.group(1);
			if(StringUtils.isNotBlank(number)) {
				movements.add(Integer.parseInt(number));
			}
		}
	}
	
	private void initializeRotations(String instructions) {
		
		Matcher matcher = ROTATION_PATTERN.matcher(instructions);
		
		while(matcher.find()) {
			String rotation = matcher.group(1);
			if(StringUtils.isNotBlank(rotation)) {
				rotations.add(rotation.charAt(0));
			}
		}
	}
	
	private void updateFacingValue() {
		if(Vector2.right().equals(direction)) {
			facingDirection = FACING_RIGHT;
		} else if(Vector2.left().equals(direction)) {
			facingDirection = FACING_LEFT;
		} else if(Vector2.up().equals(direction)) {
			facingDirection = FACING_DOWN;
		} else {
			facingDirection = FACING_UP;
		}
	}
	
	
	private void initializeRanges() {
		
		int[] minVertical = new int[cols];
		int[] maxVertical = new int[cols];
		int[] minHorizontal = new int[rows];
		int[] maxHorizontal = new int[rows];
		
		for(int i = 0; i < minVertical.length; i++) {
			minVertical[i] = NOT_SET;
			maxVertical[i] = NOT_SET;
		}
		
		for(int i = 0; i < minHorizontal.length; i++) {
			minHorizontal[i] = NOT_SET;
			maxHorizontal[i] = NOT_SET;
		}
		
		for(int rowIndex = 0; rowIndex < rows; rowIndex++) {
			for(int colIndex = 0; colIndex < cols; colIndex++) {
				
				if((map[rowIndex][colIndex] != EMPTY) && minVertical[colIndex] == NOT_SET){
					minVertical[colIndex] = rowIndex;
					maxVertical[colIndex] = rowIndex;
				} else if (map[rowIndex][colIndex] != EMPTY){
					maxVertical[colIndex] = rowIndex;
				}
				
				// Horizontal ranges
				if(map[rowIndex][colIndex] != EMPTY && minHorizontal[rowIndex] == NOT_SET) {
					minHorizontal[rowIndex] = colIndex;
					maxHorizontal[rowIndex] = colIndex;
				} else if(map[rowIndex][colIndex] != EMPTY) {
					maxHorizontal[rowIndex] = colIndex;
				}
			}
		}
		
		// Initialize vertical ranges
		for(int i = 0; i < minVertical.length; i++) {
			int start = minVertical[i];
			int end = maxVertical[i];
			verticalRanges.put(i,new Range(start,end));
		}
		
		// Initialize horizontal ranges
		for(int i = 0; i < minHorizontal.length; i++) {
			int start = minHorizontal[i];
			int end = maxHorizontal[i];
			horizontalRanges.put(i,new Range(start,end));
		}
	}

	private void initializeMap(List<String> inputs) {
		
		cols = inputs.stream().map(String::length).sorted(Collections.reverseOrder()).findFirst().get();
		rows = inputs.size();
		map = new char[rows][cols];
		
		int rowIndex = 0;
		for(String input : inputs) {
			
			char row[] = new char[cols];
			int colIndex = 0;
			char inputRow[] = input.toCharArray();
			
			for(int inputColIndex = 0; inputColIndex < row.length; inputColIndex++) {
				if(inputColIndex < inputRow.length) {
					row[colIndex++] = inputRow[inputColIndex];
				} else {
					row[colIndex++] = EMPTY;
				}
			}
			
			map[rowIndex++] = row;
			
		}

	}

}
