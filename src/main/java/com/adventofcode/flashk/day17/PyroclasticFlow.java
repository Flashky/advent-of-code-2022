package com.adventofcode.flashk.day17;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

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
	private long minY = 1; // Purge bar

	// Collider manager
	private Set<Collider2DL> colliders = new HashSet<>();
	
	// Part 2
	private TetrisState currentTetrisState;
	private Set<TetrisState> foundTetrisStates = new HashSet<>();
	private Deque<TetrisState> tetrisCycle = new LinkedList<>();
	private boolean cycleEndFound = false;
	
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
		System.out.println();
		// Before cycle
		long rocksBeforeCycle = 0;
		long heightBeforeCycle = 0;
		
		// Cycle
		long cycleRocks = 0;
		long cycleHeight = 0;
		
		// After cycle
		long rocksAfterCycle = 0;
		long totalCycles = 0;
		long uncycledRocks = 0;
		
		for(int rockCount = 1; rockCount <= numberOfRocks; rockCount++) {
			
			// Spawn a new rock
			Rock nextRock = spawn();
			
			// Begin movement.
			boolean jetGas = true;
			while(nextRock.isMoving()) {
				
				if(jetGas) {
					// Apply horizontal movement
					
					// 1. Obtain direction
					Vector2L direction = getHorizontalDirection();
					
					// 2. Attempt to move
					nextRock.move(direction);
					
					// 3. Collision check
					if(collidesWithAnything(nextRock)) {
						
						// Restore previous position
						if(LEFT.equals(direction)) {
							nextRock.move(RIGHT);
						} else {
							nextRock.move(LEFT);
						}
					}
					
					// Finished movement
					// Next movement will be vertical
					
					jetGas = false;
				} else {
					
					// 1. Attempt to move down
					nextRock.move(DOWN);
					
					// 2. Collision check
					if(collidesWithAnything(nextRock)) {

						// Restore previous position and make rock stop moving
						nextRock.move(UP);
						nextRock.setMoving(false);
						
					}
					//System.out.println("Fall 1 unit - " +nextRock.getPosition());
					// Next movement will be horizontal
					jetGas = true;
				}
				

			}
			
			// Cuando la roca ya no se esté moviendo, añadimos sus colliders al listado de colliders
			// De esta forma, la siguiente roca podrá comparar colisiones con esta roca.
			colliders.addAll(nextRock.getColliders());
			
			// START CHECK CYCLE REPETION  ALGORITHM
			
			// Add current rock to pattern list
			if(!foundTetrisStates.contains(currentTetrisState)) {
				tetrisCycle.clear();
				currentTetrisState.setMaxY(maxY);
				foundTetrisStates.add(currentTetrisState);
				rocksBeforeCycle++;
				System.out.println("Rock count: " + rocksBeforeCycle + " - " + currentTetrisState);
				

			} else {
				
				
				
				// Already seen state, might be a cycle MIGHT BE pero puede que no lo sea
				// He partido de una premisa falsa: 
				
				
				if(tetrisCycle.isEmpty()) {
					// Cycle start
					heightBeforeCycle = maxY; // OK
					
					System.out.println();
					System.out.println("Cycle start!!");
					rocksBeforeCycle++;
					System.out.println("Rock count: " + rocksBeforeCycle + " - " + currentTetrisState);
					
					
				} else if(tetrisCycle.peek().equals(currentTetrisState)) {
	
					// Cycle end
					cycleEndFound = true;
					System.out.println("Cycle end!!");

					// Adjust before cycle data
					rocksBeforeCycle--;
					
					// Calculate cycle data
					//cycleHeight = maxY - heightBeforeCycle;
					AtomicLong heightBeforeCycleAtomic = new AtomicLong(heightBeforeCycle);
					tetrisCycle.stream().forEach(status -> status.normalizeHeight(heightBeforeCycleAtomic.get()));
					
					cycleRocks = tetrisCycle.size(); // OK
					cycleHeight = tetrisCycle.peekLast().getMaxY(); // OK
					
							
					// Caclulate after cycle data
					rocksAfterCycle = numberOfRocks - rocksBeforeCycle;
					totalCycles = rocksAfterCycle / cycleRocks;
					uncycledRocks = rocksAfterCycle % cycleRocks;
					

					break;
				}
				
				if(!cycleEndFound) {
					// Add rock to cycle
					currentTetrisState.setMaxY(maxY);
					tetrisCycle.add(currentTetrisState);
					//cycleRocks++;
				}
			}

			// END CHECK CYCLE REPETION  ALGORITHM
		
			maxY = Math.max(maxY, nextRock.getMaxY());
			
		}
		
		
		if(cycleEndFound) {
			
			System.out.println("----------------------------------------");
			System.out.println("Before cycle");
			System.out.println("----------------------------------------");
			System.out.println("Rocks before cycle = " + rocksBeforeCycle);
			System.out.println("Height before cycle = " +heightBeforeCycle);
			
			System.out.println();
			System.out.println("----------------------------------------");
			System.out.println("Cycle");
			System.out.println("----------------------------------------");
			System.out.println("Rocks per cycle = "+cycleRocks);
			System.out.println("Cycle height = "+cycleHeight);
			
			System.out.println();
			System.out.println("----------------------------------------");
			System.out.println("After cycle");
			System.out.println("----------------------------------------");
			System.out.println("Remaining rocks = "+rocksAfterCycle);
			System.out.println("Remaining cycles = "+totalCycles);
			System.out.println("Uncycled rocks = "+uncycledRocks); // CUIDADO CON ESTAS, HAY QUE CALCULAR SU ALTURA
			
			System.out.println();
			System.out.println("----------------------------------------");
			System.out.println("Calculations");
			System.out.println("----------------------------------------");
			long heightAfterCycle = calculateHeightAfterCycles(uncycledRocks);
			long totalCyclesHeight = totalCycles * cycleHeight;
			long totalHeight = heightBeforeCycle + totalCyclesHeight + heightAfterCycle;
			System.out.println("Height before cycles = "+heightBeforeCycle);
			System.out.println("Cycles height = " + totalCyclesHeight);
			System.out.println("Height after cycles = " + heightAfterCycle);
			System.out.println("Total height = "+ totalHeight);
			System.out.println();
			
			return totalHeight;

			// Calculate how many cycles fit
		}
		
		return maxY;
		
	}

	private long calculateHeightAfterCycles(long uncycledRocks) {
		long heightAfterCycle = 0;

		//Long height = tetrisCycle.stream().mapToLong(TetrisState::getCycleHeight).reduce(0, Long::sum);

		for(long i = uncycledRocks; i != 0; i--) {
			TetrisState status = tetrisCycle.poll();
			heightAfterCycle = status.getMaxY();
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
		
		// Add movement to tetris pattern
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
			default:
				throw new IllegalArgumentException("Invalid rock type: "+rockShapeOrder[nextRockIndex]);
		}
	
		// Save current pattern
		currentTetrisState = new TetrisState();	
		currentTetrisState.setShapeIndex(nextRockIndex);
		
		// Obtain next circular index
		nextRockIndex = (nextRockIndex+1) % rockShapeOrder.length;
		
		return spawnedRock;
	}
	
}
