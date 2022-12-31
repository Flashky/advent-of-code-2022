package com.adventofcode.flashk.day24;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.adventofcode.flashk.day22.Range;

public class BlizzardBasin {

	private static final char EMPTY = '.';
	private static final char WALL = '#';
	
	// Premisas:
	// - La entrada está arriba a la izquierda.
	// - La salida está abajo a la derecha.
	// - Como máximo podemos movernos en 4 direcciones, o no movernos. Por lo tanto, 5 decisiones como máximo.

	// Teorías:
	// - Debido a las posiciones tan concretas de inicio y fin, los movimientos a la derecha y hacia abajo
	// deberían tener más valor que los movimientos a la izquierda y arriba, puesto que nos acercan al objetivo.

	// Teorías descartadas
	// - No hay una sola posición posible de movimiento. En el ejemplo, en el minuto 8 podemos movernos arriba oa la izquierda y se elige arriba
	
	// Deducciones comprobadas:
	// - En la columna de inicio y en la de fin no hay ventiscas verticales. De no ser así, se saldrían.
	
	// Estructuras de datos:
	// - Los datos de entrada son como mucho de tamaño 37x103 -> Podemos usar un array bidimensional
	// - Incluso podemos eliminar los bordes (paredes) para que sea mucho más sencillo calcular el módulo de posicionamiento de las ventiscas:
	// 	- En tal caso tendríamos un array de 35x101
	
	// QUIERO una estructura de datos que modele una ventisca.
	// PARA poder gestionar su posición y movimiento en cada turno.
	
	// QUIERO un array bidimensional que me permita almacenar ventiscas para cada punto (x,y)
	
	private Cell[][] initialValleyMap;
	private Cell[][] valleyMap;
	
	private Map<Integer, Set<Cell>> visitedCellsByMinute = new HashMap<>();
	private int blizzardStates = 1; // Total number of possible blizzard states
	
	private Cell endPosition;
	
	private int rows;
	private int cols;

	// DFS
	private int shortestTime = Integer.MAX_VALUE;
	
	public BlizzardBasin(List<String> inputs) {
		
		rows = inputs.size();
		cols = inputs.get(0).length();
		
		initialValleyMap = new Cell[rows][cols]; // To check for patterns 
		valleyMap = new Cell[rows][cols];
		
		// Max horizontal range for blizzards
		Range horizontalRange = new Range(1,cols-2);
		Range verticalRange = new Range(1, rows-2);
		
		// Bottom-left corner of the map will be (0,0) using this method
		for(int row = 0; row < rows; row++) {
			valleyMap[row] = new Cell[cols];
			initialValleyMap[row] = new Cell[cols];
			char[] valleyRow = inputs.get(row).toCharArray();
			for(int col = 0; col < cols; col++) {
				
				switch(valleyRow[col]) {
					case WALL: 
						initialValleyMap[row][col] = new Cell(true, row, col); 
						valleyMap[row][col] = new Cell(true, row, col); 
						break;
					case EMPTY: 
						initialValleyMap[row][col] = new Cell(false, row, col); 
						valleyMap[row][col] = new Cell(false, row, col); 
						break;
					case Blizzard.LEFT: 
						initialValleyMap[row][col] = new Cell(new Blizzard(Blizzard.LEFT,row,col,horizontalRange), row, col); 
						valleyMap[row][col] = new Cell(new Blizzard(Blizzard.LEFT,row,col,horizontalRange), row, col); 
						break;
					case Blizzard.RIGHT: 
						initialValleyMap[row][col] = new Cell(new Blizzard(Blizzard.RIGHT,row,col,horizontalRange), row, col);
						valleyMap[row][col] = new Cell(new Blizzard(Blizzard.RIGHT,row,col,horizontalRange), row, col); 
						break;
					case Blizzard.UP: 
						initialValleyMap[row][col] = new Cell(new Blizzard(Blizzard.UP,row,col,verticalRange), row, col);
						valleyMap[row][col] = new Cell(new Blizzard(Blizzard.UP,row,col,verticalRange), row, col); 
						break;
					case Blizzard.DOWN: 
						initialValleyMap[row][col] = new Cell(new Blizzard(Blizzard.DOWN,row,col,verticalRange), row, col);
						valleyMap[row][col] = new Cell(new Blizzard(Blizzard.DOWN,row,col,verticalRange), row, col); 
					break;
					default:
						throw new IllegalArgumentException("Invalid cell value: "+valleyRow[col]);
				}

			}
		}
		
		// Initialize start, end and current position
		
		endPosition = valleyMap[rows-1][cols-2];
		blizzardStates = calculateDifferentBlizzardPatterns();
		initializeVisitedMap();

	}

	
	public int solveABFS2() {
		
		// Test pattern recognition
		int minutes = 0;
		do {
			moveBlizzards();
			minutes++;
		} while(!sameBlizzardPattern());
		
		System.out.println(minutes);
		return 0;
	}
	
	public int solveABFS() {
		
		// Implementar como BFS:
		// https://en.wikipedia.org/wiki/Breadth-first_search
		Set<Integer> blizzardMinutes = new HashSet<>();
		int previousMinute = -1;
		//int blizzardMovements = 0;
		
		Queue<Cell> toVisit = new LinkedList<>();
		
		// Set start position as visited and add to queue
		Cell currentPos = valleyMap[0][1];
		currentPos.setExpedition(true);
		visitedCellsByMinute.get(0).add(currentPos);
		toVisit.add(currentPos);
		
		drawMap(currentPos);
		moveBlizzards();
		blizzardMinutes.add(0);
		blizzardMinutes.add(1);
		
		while(!toVisit.isEmpty()) {
					
			// Move expedition
			currentPos.setExpedition(false);
			previousMinute = currentPos.getMinutes();
			currentPos = toVisit.poll();
			currentPos.setExpedition(true);

			
			if(isSolution(currentPos)) {
				return currentPos.getMinutes();
			} else {
				
				currentPos.setExpedition(true);
			
				
				// Move blizzards
				if(!blizzardMinutes.contains(currentPos.getMinutes())) {
					moveBlizzards(); 
					blizzardMinutes.add(currentPos.getMinutes());
				}
	
				
				// Draw map after both expedition and blizzards have moved

				if(currentPos.getMinutes() > 0) {
					//drawMap(currentPos);
				}
				
				// Simulate blizzard movement before calculating adjacents and then rollback
				moveBlizzards();
				Queue<Cell> adjacentCells = getAdjacentCells(currentPos);
				backtrackBlizzards();
				
				for(Cell cell : adjacentCells) {
					if(!isVisited(cell)) {
						int modMinute = cell.getMinutes() % blizzardStates;
						visitedCellsByMinute.get(modMinute).add(cell);
						toVisit.add(cell);
					}
				}
				
			}
		}
	
		
		return -1;

	}

	
	private boolean isVisited(Cell cell) {
		
		// TODO aplicar módulo sobre los minutos de la celda
		int minutes = cell.getMinutes() % blizzardStates;
		return visitedCellsByMinute.get(minutes).contains(cell);
		
	}

	
	public int solveADFS() {
		dfs(valleyMap[0][1], 0);
		
		return shortestTime;
	}
	

	private void dfs(Cell currentPosition, int minutes) {
		
		//currentPosition.incrementTime();
		
		if(isSolution(currentPosition)) {
			shortestTime = Math.min(shortestTime, minutes);
		} else if(minutes > shortestTime) {
			return; // Not optimal solution
		} else if(currentPosition.isEmpty() && !isVisited(currentPosition)) {
			
			// Mark current position as visited
			int modMinute = minutes % blizzardStates;
			visitedCellsByMinute.get(modMinute).add(currentPosition);
			
			currentPosition.setExpedition(true);
			//drawMap(currentPosition);
			moveBlizzards(); // Cada vez que el mapa cambia
			
			Queue<Cell> adjacentCells = getAdjacentCells(currentPosition);

			for(Cell nextPosition : adjacentCells) {
				currentPosition.setExpedition(false);
				dfs(nextPosition, minutes+1);
			}
			
			backtrackBlizzards();
		}
	}
	
	private Queue<Cell> getAdjacentCells(Cell currentPosition) {
		
		Queue<Cell> adjacentCells = new LinkedList<>();
		
		int row = currentPosition.getRow();
		int col = currentPosition.getCol();
		int minutes = currentPosition.getMinutes() + 1;
		
		// Left
		Cell adjacent = valleyMap[row][col-1];
		if(adjacent.isEmpty()) {
			adjacent.setMinutes(minutes);
			adjacentCells.add(adjacent);
		}
		
		// Right
		adjacent = valleyMap[row][col+1];
		if(adjacent.isEmpty()) {
			adjacent.setMinutes(minutes);
			adjacentCells.add(adjacent);
		}
		
		// Down
		adjacent = valleyMap[row+1][col];
		if(adjacent.isEmpty()) {
			adjacent.setMinutes(minutes);
			adjacentCells.add(adjacent);
		}
		

		

		
		// Up - Extra verification to avoid start position issues
		if(currentPosition.getRow() > 0) {
			adjacent = valleyMap[row-1][col];
			if(adjacent.isEmpty()) {
				adjacent.setMinutes(minutes);
				adjacentCells.add(adjacent);
			}
		}
		
		// Wait - Only if there are no blizzards over us
		if(adjacentCells.isEmpty() && currentPosition.isEmpty()) {
			adjacent = valleyMap[row][col];
			if(adjacent.isEmpty()) {
				adjacent.setMinutes(minutes);
				adjacentCells.add(adjacent);
			}
		}
		
		return adjacentCells;
	}

	private boolean isSolution(Cell currentPosition) {
		return currentPosition.equals(endPosition);
	}
	
	private void moveBlizzards() {
		List<Blizzard> nextBlizzardPositions = new ArrayList<>();
		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < cols; col++) {
				while(valleyMap[row][col].hasBlizzards()) {
					Blizzard blizzard = valleyMap[row][col].nextBlizzard();
					blizzard.move();
					nextBlizzardPositions.add(blizzard);
				}
			}
		}
		
		nextBlizzardPositions.stream()
							.forEach(blizzard -> valleyMap[blizzard.getRow()][blizzard.getCol()].addBlizzard(blizzard));
	}
	
	private void backtrackBlizzards() {
		List<Blizzard> nextBlizzardPositions = new ArrayList<>();
		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < cols; col++) {
				while(valleyMap[row][col].hasBlizzards()) {
					Blizzard blizzard = valleyMap[row][col].nextBlizzard();
					blizzard.backtrack();
					nextBlizzardPositions.add(blizzard);
				}
			}
		}
		
		nextBlizzardPositions.stream()
							.forEach(blizzard -> valleyMap[blizzard.getRow()][blizzard.getCol()].addBlizzard(blizzard));
	}
	
	private void drawMap(Cell currentPosition) {
		System.out.println();
		
		if(currentPosition.getMinutes() == 0) {
			System.out.println("Initial state:");
		} else {
			System.out.println(String.format("Minute %s:",currentPosition.getMinutes()));
		}
		
		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < cols; col++) {
				System.out.print(valleyMap[row][col].toString());
			}
			System.out.println();
		}
	}
	
	private boolean sameBlizzardPattern() {
		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < cols; col++) {
				
				Queue<Blizzard> initialBlizzards = initialValleyMap[row][col].getBlizzards();
				Queue<Blizzard> currentBlizzards = valleyMap[row][col].getBlizzards();
				if(!sameBlizzards(initialBlizzards, currentBlizzards)) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean sameBlizzards(Queue<Blizzard> initialBlizzards, Queue<Blizzard> currentBlizzards) {
		if(initialBlizzards.size() != currentBlizzards.size()) {
			return false;
		}
		
		boolean same = true;
		
		Iterator<Blizzard> initialBlizzardsIt = initialBlizzards.iterator();
		Iterator<Blizzard> currentBlizzardsIt = currentBlizzards.iterator();
		
		while(initialBlizzardsIt.hasNext() && same) {
			same = initialBlizzardsIt.next().getDirection() == currentBlizzardsIt.next().getDirection();
		}
		
		return same;
		
	}
	

	private void initializeVisitedMap() {
		for(int i = 0; i < blizzardStates; i++) {
			Set<Cell> visitedCells = new HashSet<>();
			visitedCellsByMinute.put(i, visitedCells);
		}
	}

	private int calculateDifferentBlizzardPatterns() {
		int minutes = 0;
		do {
			moveBlizzards();
			minutes++;
		} while(!sameBlizzardPattern());
		
		return minutes;
	}
}
