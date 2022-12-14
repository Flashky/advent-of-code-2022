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
	
	
	public long solveA() {
		
		long sandUnits = 0;
		Vector2 sandUnit = null;
	
		do {
			
			sandUnit = new Vector2(SAND_START);
			sandUnits++;
			
			while(move(sandUnit)) {}
			
		} while(sandUnit.getY() < maxOverflowY);
		
		return sandUnits-1;
	}
	
	public long solveB() {
		
		maxOverflowY += 2;
		Vector2 floorStart = new Vector2(0, maxOverflowY);
		Vector2 floorEnd = new Vector2(cols-1,maxOverflowY);
		
		// Draw floor
		drawRockLine(floorStart, floorEnd);
		
		// Fill
		long sandUnits = 0;
		Vector2 sandUnit = null;
	
		do {
			//drawMap(sandUnits); // Enable DEBUG if needed and uncomment method
			sandUnit = new Vector2(SAND_START);
			sandUnits++;
			
			while(move(sandUnit)) {}
			
		} while(!SAND_START.equals(sandUnit));
		
		return sandUnits;
	}

	private boolean move(Vector2 sandUnit) {
		
		if(sandUnit.getY() >= maxOverflowY) {
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
		
		for(Path path : paths) {
			List<Vector2> points = path.getPoints();
			
			Vector2 start = null;
			for(Vector2 end : points) {
				if(start != null) {
					// Calcula el mínimo y
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
