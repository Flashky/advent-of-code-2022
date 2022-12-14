package com.adventofcode.flashk.day14;

import java.util.List;
import java.util.stream.Collectors;

import com.adventofcode.flashk.common.Vector2;

public class RegolithReservoir {

	// Debug purposes only
	//private final static boolean DEBUG = false;
	//private final static int DEBUG_SAND_UNIT = 92;
	
	// Cave tile values
	private final static char ROCK = '#';
	private final static char AIR = '.';
	private final static char SAND = 'o';
	
	// Cave map
	private final static int rows = 500;
	private final static int cols = 1000;
	private char[][] caveMap = new char[cols][rows];
	
	// Sand flow directions and start
	private final static Vector2 DOWN = Vector2.up();
	private final static Vector2 DOWN_LEFT = Vector2.upLeft();
	private final static Vector2 DOWN_RIGHT = Vector2.upRight();
	private final static Vector2 SAND_START = new Vector2(500,0);
	
	// Any sand that drops below this value is flowing into the abyss below;
	private int maxOverflowY = Integer.MIN_VALUE;
	
	public RegolithReservoir(List<String> inputs) {
		
		List<Path> paths = inputs.stream().map(Path::new).collect(Collectors.toList());
		initializeCaveMap(paths);

	}
	
	public int solve(boolean includeFloor) {
		
		if(includeFloor) {
			// Draw floor
			maxOverflowY += 2;
			Vector2 floorStart = new Vector2(0, maxOverflowY);
			Vector2 floorEnd = new Vector2(cols-1,maxOverflowY);
			drawRockLine(floorStart, floorEnd);
		}
		
		return moveSand();
	}

	/**
	 * Drop sand units until the sand blocks the entrance or overflows.
	 * 
	 * @return generated sand unit units <i>(excluding overflowed sand unit)</i>.
	 */
	private int moveSand() {
		
		int sandUnits = 0;
		Vector2 sandUnit = null;
	
		do {
			
			sandUnit = new Vector2(SAND_START);
			sandUnits++;
			
			while(move(sandUnit)) {}
			
		} while ((!SAND_START.equals(sandUnit)) && !overflows(sandUnit));
		
		return overflows(sandUnit) ? --sandUnits : sandUnits;
	}
	
	/**
	 * Attempts to move a sand unit one position.
	 * @param sandUnit the sand unit to move
	 * @return a boolean meaning <code>true</code> if the sand unit has moved, <code>false</code> if sand cannot move.
	 */
	private boolean move(Vector2 sandUnit) {
		
		if(overflows(sandUnit)) {
			return false;
		}
		
		Vector2 down = Vector2.transform(sandUnit, DOWN);
		if(isEmpty(down)) {
			sandUnit.transform(DOWN);
			return true;
		} 
		
		Vector2 downLeft = Vector2.transform(sandUnit, DOWN_LEFT);
		if(isEmpty(downLeft)) {
			sandUnit.transform(DOWN_LEFT);
			return true;
		}
		
		Vector2 downRight = Vector2.transform(sandUnit, DOWN_RIGHT);
		if(isEmpty(downRight)) {
			sandUnit.transform(DOWN_RIGHT);
			return true;
		}
		
		// Cannot move, fix sand at floor
		caveMap[sandUnit.getY()][sandUnit.getX()] = SAND;
				
		return false;
	}

	
	private boolean overflows(Vector2 sandUnit) {
		return sandUnit.getY() >= maxOverflowY;
	}

	private boolean isEmpty(Vector2 position) {
		return caveMap[position.getY()][position.getX()] == AIR;

	}

	private void initializeCaveMap(List<Path> paths) {
		
		for(int rowIndex = 0; rowIndex < rows; rowIndex++) {
			caveMap[rowIndex] = new char[cols];
			for(int colIndex = 0; colIndex < cols; colIndex++) {
				caveMap[rowIndex][colIndex] = AIR;
			}
		}
		
		// Draw all rock lines
		for(Path path : paths) {
			List<Vector2> points = path.getPoints();
			
			Vector2 start = null;
			for(Vector2 end : points) {
				if(start != null) {
					// Actualiza la distancia de la roca más baja
					maxOverflowY = Math.max(maxOverflowY, end.getY());
					
					// Rellena los huecos en el array
					drawRockLine(start, end);
				}
				
				start = end;
			}
		}
	}


	private void drawRockLine(Vector2 start, Vector2 end) {
		
		Vector2 direction = Vector2.substract(end, start);
		direction.normalize();
		Vector2 updatePos = new Vector2(start);
		
		while(!updatePos.equals(end)) {
			caveMap[updatePos.getY()][updatePos.getX()] = ROCK;
			updatePos.transform(direction);
		}
		caveMap[updatePos.getY()][updatePos.getX()] = ROCK;
		
	}
	
	/*
	private void drawMap(int sandUnits) {
		if(DEBUG && (sandUnits == DEBUG_SAND_UNIT)) {
			for(int i = 0; i < rows; i++) {
				for(int j = 0; j < cols; j++) {
					System.out.print(caveMap[i][j]);
				}
				System.out.println();
			}
		}
	}*/
	
}
