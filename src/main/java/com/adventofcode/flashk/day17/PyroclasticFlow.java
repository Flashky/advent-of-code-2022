package com.adventofcode.flashk.day17;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;

import com.adventofcode.flashk.common.Collider2DL;
import com.adventofcode.flashk.common.Vector2L;

public class PyroclasticFlow {

	private static final char JET_LEFT = '<';
	
	private static final int SPAWN_OFFSET_X = 2;
	private static final int SPAWN_OFFSET_Y = 3;
	
	// Movement directions
	private static final Vector2L UP = Vector2L.up();
	private static final Vector2L DOWN = Vector2L.down();
	private static final Vector2L LEFT = Vector2L.left();
	private static final Vector2L RIGHT = Vector2L.right();
	
	private char[] jetMovements;
	private int nextJetMovementIndex = 0;
	
	// Rock spawning management
	private char[] rockShapeOrder = { Rock.HORIZONTAL_BAR, Rock.CROSS, Rock.CORNER, Rock.VERTICAL_BAR, Rock.SQUARE };
	private int nextRockIndex = 0;
	private long maxY = 0;

	// Collider manager
	private Set<Collider2DL> colliders = new HashSet<>();
	
	
	// Part 2
	private TetrisState currentTetrisState;
	private Deque<TetrisState> tetrisCycle = new LinkedList<>();
	
	public PyroclasticFlow(String input) {
		jetMovements = input.toCharArray();
		
		// Initialize map colliders
		Vector2L start = new Vector2L();
		Vector2L end = new Vector2L(8,0);
		
		colliders.add(new Collider2DL(start,end)); // Floor
		colliders.add(new Collider2DL(start, new Vector2L(0, Integer.MAX_VALUE))); // Left border
		colliders.add(new Collider2DL(end, new Vector2L(end.getX(), Integer.MAX_VALUE))); // Right border
		
		
	}
	
	public long solveA(long numberOfRocks) {

		// Part 2
		Set<TetrisState> foundTetrisStates = new HashSet<>();
		
		for(int rockCount = 1; rockCount <= numberOfRocks; rockCount++) {
			
			// Spawn a new rock
			Rock nextRock = spawn();
			
			// Begin movement.
			boolean jetGas = true;
			while(nextRock.isMoving()) {
				
				if(jetGas) {
					
					// Horizontal movement

					Vector2L direction = getHorizontalDirection();
					nextRock.move(direction);
					
					if(collidesWithAnything(nextRock)) {
						
						// Restore previous position
						if(LEFT.equals(direction)) {
							nextRock.move(RIGHT);
						} else {
							nextRock.move(LEFT);
						}
					}

					jetGas = false; // Next movement will be vertical
					
				} else {
					
					// Vertical movement
					nextRock.move(DOWN);
					
					if(collidesWithAnything(nextRock)) {

						// Restore previous position and make rock stop moving
						nextRock.move(UP);
						nextRock.setMoving(false);
						
					}

					jetGas = true; // Next movement will be horizontal
				}
				

			}
			
			// Cuando la roca ya no se esté moviendo, añadimos sus colliders al listado de colliders
			// De esta forma, la siguiente roca podrá comparar colisiones con esta roca.
			
			colliders.addAll(nextRock.getColliders());
			
			// START CHECK CYCLE REPETION  ALGORITHM
			
			if(!foundTetrisStates.contains(currentTetrisState)) {
				
				// Reset cycle	
				tetrisCycle.clear();
				currentTetrisState.setMaxY(maxY);
				foundTetrisStates.add(currentTetrisState);
				
			} else if(foundCycle()) {
	
				// Cycle end match
				return calculateHeight(numberOfRocks, rockCount);
					
			} else {
				// Add rock to cycle
				currentTetrisState.setMaxY(maxY);
				tetrisCycle.add(currentTetrisState);
			}
		

			// END CHECK CYCLE REPETION  ALGORITHM
		
			maxY = Math.max(maxY, nextRock.getMaxY());
			
		}
		
		return maxY;
		
	}

	private long calculateHeight(long numberOfRocks, int rockCount) {
		
		// Calculate before cycle data
		long rocksBeforeCycles = rockCount - tetrisCycle.size() - 2;
		final long heightBeforeCycles = tetrisCycle.peek().getMaxY();
		
		// Calculate cycle data
		tetrisCycle.stream().forEach(status -> status.normalizeHeight(heightBeforeCycles));
		long rocksPerCycle = tetrisCycle.size();
		long heightPerCycle = tetrisCycle.peekLast().getMaxY();
		long remainingRocks = numberOfRocks - rocksBeforeCycles;
		long totalCycles = remainingRocks / rocksPerCycle;
		final long totalCyclesHeight = totalCycles * heightPerCycle;
		
		//  Calculate after cycle data
		long rocksAfterCycles = remainingRocks % rocksPerCycle;
		final long heightAfterCycles = calculateHeightAfterCycles(rocksAfterCycles);
		
		return heightBeforeCycles + totalCyclesHeight + heightAfterCycles;
	}

	private boolean foundCycle() {
		return !tetrisCycle.isEmpty() && tetrisCycle.peek().equals(currentTetrisState);
	}

	private long calculateHeightAfterCycles(long remainingRocks) {
		long heightAfterCycle = 0;

		if(remainingRocks > 1) {
			heightAfterCycle = tetrisCycle.stream().skip(remainingRocks-1).findFirst().get().getMaxY();
		}

		return heightAfterCycle;
	}

	private boolean collidesWithAnything(Rock rock) {
		
		// Test collisions against all colliders
		Optional<Collider2DL> result = colliders.stream().filter(collider -> rock.collidesWith(collider)).findAny();
		return result.isPresent();

	}

	private Vector2L getHorizontalDirection() {
		
		// Obtain next jet movement
		Vector2L direction = jetMovements[nextJetMovementIndex] == JET_LEFT ? LEFT : RIGHT;
		
		// Add movement to current tetris state
		currentTetrisState.setJetIndex(nextJetMovementIndex);
		
		// Obtain next circular index
		nextJetMovementIndex = (nextJetMovementIndex+1) % jetMovements.length;
		
		return direction;
	}
	
	/**
	 * Spawns a rock
	 * @return
	 */
	private Rock spawn() {
		
		// Calculate the rock position: 2 units to left side and 3 units above
		// Add 1 to both x and y as the real position of the object is on over that position
		Vector2L position = new Vector2L(SPAWN_OFFSET_X + 1, maxY + SPAWN_OFFSET_Y + 1);
		
		Rock spawnedRock = null;
		switch(rockShapeOrder[nextRockIndex]) {
			case Rock.HORIZONTAL_BAR: spawnedRock = new HorizontalRock(position); break;
			case Rock.VERTICAL_BAR: spawnedRock = new VerticalRock(position); break;
			case Rock.SQUARE: spawnedRock = new SquareRock(position); break;
			case Rock.CROSS: spawnedRock = new CrossRock(position); break;
			case Rock.CORNER: spawnedRock = new CornerRock(position); break;
		}
	
		// Prepare current tetris state
		currentTetrisState = new TetrisState();	
		currentTetrisState.setShapeIndex(nextRockIndex);
		
		// Obtain next circular index
		nextRockIndex = (nextRockIndex+1) % rockShapeOrder.length;
		
		return spawnedRock;
	}
	
}
