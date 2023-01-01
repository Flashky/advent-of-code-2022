package com.adventofcode.flashk.day22;

import java.util.List;

import com.adventofcode.flashk.common.Vector2;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Side {

	private static final char WALL = '#';
	public static final char FACING_RIGHT = '>';
	public static final char FACING_LEFT = '<';
	public static final char FACING_UP = '^';
	public static final char FACING_DOWN = 'v';
	public static final char ROTATE_LEFT = 'L';

	// Side info
	private int sideSize;
	private int maxIndex;
	private int id;
	
	private char[][] map;

	// Start row and col to calculate from relative coordinates to absolute coordinates
	private int startRow;
	private int startCol;
	
	// Other sides
	private Side left;
	private Side right;
	private Side up;
	private Side down;
	
	
	private Vector2 position = new Vector2();
	private Vector2 direction = Vector2.right();
	private char facingDirection = FACING_RIGHT;
	
	public Side(List<String> inputs, int startRow, int startCol, int sideSize) {
		
		this.sideSize = sideSize;
		this.maxIndex = sideSize - 1;
		this.map = new char[sideSize][sideSize];
		
		this.startRow = startRow;
		this.startCol = startCol;
		
		int mapRow = 0;
		int endRow = startRow + sideSize;
		int endCol = startCol + sideSize;
		for(int row = startRow; row < endRow; row++) {
			this.map[mapRow++] = inputs.get(row).substring(startCol, endCol).toCharArray();
		}
	}
	
	public boolean isWall(Vector2 position) {
		return map[position.getY()][position.getX()] == WALL;
	}

	public Side move(int distance) {

		boolean canMove = true;
		Side activeSide = this;
		
		map[position.getY()][position.getX()] = facingDirection;
		
		while(distance > 0 & canMove) {
			
			Vector2 newPosition = Vector2.transform(position, direction);
			
			if(newPosition.getX() < 0) {
				activeSide = left.moveLeft(distance-1, this);
				canMove = false;
			} else if(newPosition.getX() == sideSize) {
				activeSide = right.moveRight(distance-1, this);
				canMove = false;
			} else if(newPosition.getY() < 0) {
				activeSide = up.moveUp(distance-1, this);
				canMove = false;
			} else if(newPosition.getY() == sideSize) {
				activeSide = down.moveDown(distance-1, this);
				canMove = false;
			} else if(!isWall(newPosition)) {
				position = newPosition;
				map[position.getY()][position.getX()] = facingDirection;
				distance--;
			} else {
				canMove = false;
			}
			
		}
		
		if(activeSide != null) {
			return activeSide;
		}
		
		return this;
	}

	public void rotate(char rotation) {
		
		if(rotation == ROTATE_LEFT) {
			direction.rotateRight();
		} else {
			direction.rotateLeft();	
		}
		
		// Update facing direction
		updateFacingDirection();
		
	}

	public Vector2 getAbsolutePosition() {
		
		int absoluteX = position.getX() + startCol;
		int absoluteY = position.getY() + startRow;
		
		return new Vector2(absoluteX, absoluteY);
	}
	
	private void updateFacingDirection() {
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
	
	/**
	 * Covers cases:
	 * <ul>
	 * <li>Left <> Left: Same way and direction</li>
	 * <li>Left <> Right: Same way and opposite direction</li>
	 * <li>Left <> Down: Complex simmetry</li>
	 * <li>Left <> Up: Basic Simmetry</li>
	 * @param distance
	 * @param originSide
	 * @return
	 */
	private Side moveLeft(int distance, Side originSide) {

		// On any case, make a conversion for position and direction, then call back move.
		if(originSide == left) {
			return transformSameWayAndDirectionHorizontal(distance, originSide); // Left <> Left
		} else if(originSide == right) {
			return transformSameWayOppositeDirectionHorizontal(distance, originSide); // Left <> Right
		} else if(originSide == down) {
			return transformComplexSimmetry(distance, originSide); // Left <> Down
		} else if(originSide == up) {
			return transformBasicSimmetry(distance, originSide); // Left <> Up
		} else {
			throw new UnsupportedOperationException("Ops! the cube might not be correctly built.");
		}
	}
	

	/**
	 * Covers cases:
	 * <ul>
	 * <li>Right <> Right: Same way and direction</li>
	 * <li>Right <> Left: Same way and opposite direction</li>
	 * <li>Right <> Up: Complex simmetry</li>
	 * <li>Right <> Down: Basic simmetry</li>
	 * @param distance
	 * @param originSide
	 * @return
	 */
	private Side moveRight(int distance, Side originSide) {

		// On any case, make a conversion for position and direction, then call back move.
		if(originSide == right) {
			return transformSameWayAndDirectionHorizontal(distance, originSide); // Right <> Right
		} else if(originSide == left) {
			return transformSameWayOppositeDirectionHorizontal(distance, originSide); // Right <> Left
		} else if(originSide == up) {
			return transformComplexSimmetry(distance, originSide); // Right <> Up
		} else if(originSide == down) {
			return transformBasicSimmetry(distance, originSide); // Right <> Down
		} else {
			throw new UnsupportedOperationException("Ops! the cube might not be correctly built.");
		}
	}
	
	/**
	 * Covers cases:
	 * <ul>
	 * <li>Up <> Up: Same way and direction</li>
	 * <li>Up <> Down: Same way and opposite direction</li>
	 * <li>Up <> Right: Complex simmetry</li>
	 * <li>Up <> Left: Basic simmetry</li>
	 * @param distance
	 * @param originSide
	 * @return
	 */
	private Side moveUp(int distance, Side originSide) {

		if(originSide == up) {
			return transformSameWayAndDirectionVertical(distance, originSide); // Up <> Up
		} else if(originSide == down) {
			return transformSameWayOppositeDirectionVertical(distance, originSide); // Up <> Down
		} else if(originSide == right) {
			return transformComplexSimmetry(distance, originSide); // Up <> Right
		} else if(originSide == left) {
			return transformBasicSimmetry(distance, originSide); // Up <> Left
		} else {
			throw new UnsupportedOperationException("Ops! the cube might not be correctly built.");
		}
	}
	
	/**
	 * Covers cases:
	 * <ul>
	 * <li>Down <> Down: Same way and direction</li>
	 * <li>Down <> Up: Same way and opposite direction</li>
	 * <li>Down <> Left: Complex simmetry</li>
	 * <li>Down <> Right: Basic simmetry</li>
	 * @param distance
	 * @param originSide
	 * @return
	 */
	private Side moveDown(int distance, Side originSide) {
		if(originSide == down) {
			return transformSameWayAndDirectionVertical(distance, originSide); // Down <> Down
		} else if(originSide == up) {
			return transformSameWayOppositeDirectionVertical(distance, originSide); // Down <> Up
		} else if(originSide == left) {
			return transformComplexSimmetry(distance, originSide); // Down <> Left
		} else if(originSide == right) {
			return transformBasicSimmetry(distance, originSide); // Down <> Right
		} else {
			throw new UnsupportedOperationException("Ops! the cube might not be correctly built.");
		}
	}

	
	
	private Side transformSameWayAndDirectionHorizontal(int distance, Side originSide) {

		Vector2 originPos = originSide.getPosition();
		Vector2 originDirection = originSide.getDirection();
		
		int newX = originPos.getX();
		int newY = Math.abs(maxIndex - originPos.getY());
		
		// Same way and direction - Change direction vector sign
		position = new Vector2(newX, newY);
		direction = new Vector2(- originDirection.getX(), - originDirection.getY());
		updateFacingDirection();
		
		// Check if wall before proceding
		if(isWall(position)) {
			return null;
		}
		
		return move(distance);
		
	}
	
	private Side transformSameWayAndDirectionVertical(int distance, Side originSide) {

		Vector2 originPos = originSide.getPosition();
		Vector2 originDirection = originSide.getDirection();
		
		int newX = Math.abs(maxIndex - originPos.getX());
		int newY = originPos.getY();
		
		// Same way and direction - Change direction vector sign
		position = new Vector2(newX, newY);
		direction = new Vector2(- originDirection.getX(), - originDirection.getY());
		updateFacingDirection();
		
		// Check if wall before proceding
		if(isWall(position)) {
			return null;
		}
		
		return move(distance);
		
	}
	
	private Side transformSameWayOppositeDirectionHorizontal(int distance, Side originSide) {

		Vector2 originPos = originSide.getPosition();
		Vector2 originDirection = originSide.getDirection();
		
		int newX = Math.abs(maxIndex - originPos.getX());
		int newY = originPos.getY();
		
		// Same way and opposite direction - Keep direction vector as it is
		position = new Vector2(newX, newY);
		direction = new Vector2(originDirection);
		updateFacingDirection();
		
		// Check if wall before proceding
		if(isWall(position)) {
			return null;
		}
		
		return move(distance);
		
	}
	
	private Side transformSameWayOppositeDirectionVertical(int distance, Side originSide) {

		Vector2 originPos = originSide.getPosition();
		Vector2 originDirection = originSide.getDirection();
		
		int newX = originPos.getX();
		int newY = Math.abs(maxIndex - originPos.getY());
		
		// Same way and opposite direction - Keep direction vector as it is
		position = new Vector2(newX, newY);
		direction = new Vector2(originDirection);
		updateFacingDirection();
		
		// Check if wall before proceding
		if(isWall(position)) {
			return null;
		}
		
		return move(distance);
		
	}
	
	
	private Side transformBasicSimmetry(int distance, Side originSide) {

		Vector2 originPos = originSide.getPosition();
		Vector2 originDirection = originSide.getDirection();
		
		/*
		int newX = Math.abs(maxIndex - originPos.getY());
		int newY = Math.abs(maxIndex - originPos.getX());
		*/
		int newX = originPos.getY();
		int newY = originPos.getX();
		
		// Update position and direction - Basic simmetry - Invert coordinates and keep sign
		position = new Vector2(newX, newY);
		direction = new Vector2(-originDirection.getY(), -originDirection.getX());
		updateFacingDirection();
		
		// Check if wall before proceding
		if(isWall(position)) {
			return null;
		}
		
		return move(distance);
		
	}
	
	private Side transformComplexSimmetry(int distance, Side originSide) {

		Vector2 originPos = originSide.getPosition();
		Vector2 originDirection = originSide.getDirection();
		
		int newX = Math.abs(maxIndex - originPos.getY());
		int newY = Math.abs(maxIndex - originPos.getX());
		
		// Different way - Complex simmetry - Invert coordinates and keep sign
		position = new Vector2(newX, newY);
		direction = new Vector2(originDirection.getY(), originDirection.getX());
		updateFacingDirection();
		
		// Check if wall before proceding
		if(isWall(position)) {
			return null;
		}
		
		return move(distance);
		
	}


}
