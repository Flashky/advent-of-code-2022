package com.adventofcode.flashk.day24;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.adventofcode.flashk.common.Collider2D;
import com.adventofcode.flashk.common.Vector2;
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
	
	//private int rows;
	//private int cols;

	// Indicates whether in a position exists a blizzard or not
	private Set<Vector2> blizzardPositions = new HashSet<>();
	private List<Blizzard> blizzards = new ArrayList<>();
	
	private Vector2 currentPosition;
	private Vector2 startPosition;
	private Vector2 endPosition;
	
	private int rows;
	private int cols;
	private Collider2D topCollider;
	private Collider2D bottomCollider;
	private Collider2D leftCollider;
	private Collider2D rightCollider;
	
	private Range horizontalRange;
	private Range verticalRange;
	
	public BlizzardBasin(List<String> inputs) {
		
		rows = inputs.size();
		cols = inputs.get(0).length();
		
		// Setup colliders positions
		topCollider = new Collider2D(new Vector2(1,rows-1), new Vector2(cols-1, rows-1));	// Expected: (1,6) -> (6,6)
		bottomCollider = new Collider2D(new Vector2(0,0), new Vector2(cols-3,0)); 			// Expected: (0,0) -> (4,0)
		leftCollider = new Collider2D(new Vector2(0,0), new Vector2(0, cols-1));			// Expected: (0,0) -> (0,6)
		rightCollider = new Collider2D(new Vector2(cols-1,0), new Vector2(cols-1, rows-1));	// Expected: (6,0) -> (6,6)
		
		// Max horizontal range for blizzards
		horizontalRange = new Range(1,cols-2);
		verticalRange = new Range(1, rows-2);
		
		// Bottom-left corner of the map will be (0,0) using this method
		int y = 0;
		for(int rowIndex = rows-1; rowIndex > 0; rowIndex--) {
			String input = inputs.get(rowIndex);
			char[] row = input.toCharArray();
			for(int colIndex = 0 ; colIndex < row.length-1; colIndex++) {
				char content = row[colIndex];
				
				Blizzard blizzard = null;
				if(content == Blizzard.LEFT || content == Blizzard.RIGHT) {
					blizzard = new Blizzard(content, y, colIndex, horizontalRange);
				} else if(content == Blizzard.UP || content == Blizzard.DOWN) {
					blizzard = new Blizzard(content, y, colIndex, verticalRange);
				}
				
				if(blizzard != null) {
					blizzards.add(blizzard);
					blizzardPositions.add(blizzard.getPos());
				}
			}
			y++;
		}

		// Initialize start, end and current position
		
		currentPosition = new Vector2(1,rows-1); 	// Expected: (1,6)
		startPosition = new Vector2(currentPosition);
		endPosition = new Vector2(cols-2,0);		// Expected: (5,0)
	}
	
	public long solveA() {
		System.out.println();
		int minutes = 0;
		do {
			minutes++;
			moveBlizzards();

			// Check movements
			if(currentPosition.equals(startPosition)) {
				// Only check down
				Vector2 down = Vector2.transform(currentPosition, Vector2.down());
				if(!blizzardPositions.contains(down)) {
					currentPosition = down;
					//System.out.println(String.format("Minute %s move %s",minutes, "down"));
				}
				
			} else {
				//check 4 directions against colliders
				Vector2 up = Vector2.transform(currentPosition, Vector2.up());
				Vector2 right = Vector2.transform(currentPosition, Vector2.right());
				Vector2 down = Vector2.transform(currentPosition, Vector2.down());
				Vector2 left = Vector2.transform(currentPosition, Vector2.left());
				
				// TODO en realidad deberíamos mirar las cuatro posibles direcciones recursivamente y no una sola
				// en ocasiones puede darse el caso de que haya más de un posible movimiento
				if(!rightCollider.collidesWith(right) && !blizzardPositions.contains(right)) {
					
					currentPosition = right;
					//System.out.println(String.format("Minute %s move %s",minutes, "right"));
					
				} else if(!bottomCollider.collidesWith(down) && !blizzardPositions.contains(down)) {
					
					currentPosition = down;
					//System.out.println(String.format("Minute %s move %s",minutes, "down"));
					
				} else if(!topCollider.collidesWith(up) && !blizzardPositions.contains(up)) {
					
					currentPosition = up;
					//System.out.println(String.format("Minute %s move %s",minutes, "up"));
					
				} else if(!leftCollider.collidesWith(left) && !blizzardPositions.contains(left)) {
					
					currentPosition = left;
					//System.out.println(String.format("Minute %s move %s",minutes, "left"));
					
				}  else {
					
					//System.out.println(String.format("Minute %s move %s",minutes, "wait"));
					
				}

			}
		} while(!currentPosition.equals(endPosition));
		
		return minutes;
	}
	
	// OK
	private void moveBlizzards() {
		blizzardPositions = new HashSet<>();
		blizzards.stream().forEach(blizzard -> {
			blizzard.move();
			blizzardPositions.add(blizzard.getPos());
		});
	}
}
