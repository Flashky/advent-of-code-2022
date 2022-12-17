package com.adventofcode.flashk.day17;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.adventofcode.flashk.common.Collider2D;
import com.adventofcode.flashk.common.Vector2;

public class PyroclasticFlow {

	private static final char JET_LEFT = '<';
	
	private static final int SPAWN_OFFSET_X = 2;
	private static final int SPAWN_OFFSET_Y = 3;
	
	// Movement directions
	private static final Vector2 UP = Vector2.up();
	private static final Vector2 DOWN = Vector2.down();
	private static final Vector2 LEFT = Vector2.left();
	private static final Vector2 RIGHT = Vector2.right();
	
	private char[] jetMovements;
	private int nextJetMovementIndex = 0;
	
	// Rock spawning management
	private char[] rockShapeOrder = { Rock.HORIZONTAL_BAR, Rock.CROSS, Rock.CORNER, Rock.VERTICAL_BAR, Rock.SQUARE };
	private int nextRockIndex = 0;
	private long maxY = 0;
	

	// Collider manager
	private Set<Collider2D> colliders = new HashSet<>();
	
	public PyroclasticFlow(String input) {
		jetMovements = input.toCharArray();
		
		// Initialize map colliders
		Vector2 start = new Vector2();
		Vector2 end = new Vector2(8,0);
		
		colliders.add(new Collider2D(start,end)); // Floor
		colliders.add(new Collider2D(start, new Vector2(0, Integer.MAX_VALUE))); // Left border
		colliders.add(new Collider2D(end, new Vector2(end.getX(), Integer.MAX_VALUE))); // Right border
		
		
	}
	
	/*
	 Problemas a tratar:
	 
	 [ ] Gestión de rocas
	 	[x] Creación de rocas de todas las formas respecto a una posición (x,y) ya calculada.
	 	[x] Movimiento de roca según un vector dirección.
	 	[x] Cálculo de colisiones contra otras rocas o bordes del mapa.
	 
	 [ ] Spawneo de rocas:
	 	[ ] Calcular la posición inicial (x,y) de la roca
	 		[x] Coordenada x: 2 unidades respecto a la izquierda (x = 2)
	 		[ ] Coordenada y: 3 unidades respecto al collider que esté en la posición más alta:
	 			[ ] Caso inicial: el único collider que cuenta es colliderBottom situado en y = 0
	 			[ ] Caso tras primera roca: collider con y más alta entre todos los colliders de todas las rocas.
	 	[x] Creación de la roca que corresponda según una lista circular.
	 
	 [ ] Movimiento:
	 	[x] Cálculo de vector dirección para el jet stream
	 	[ ] Gestor de alternancia de movimiento lateral - hacia abajo.
	 	[ ] Comprobación de colisión al hacer movimiento lateral.
	 	[ ] Comprobación de colisión al hacer movimiento vertical.
	 
	 * @return
	 */
	
	public long solveA(long numberOfRocks) {

		// Spawn a rock always on y = tallestPosition + 3
		System.out.println();
		// Rocks examples

		for(int rockCount = 1; rockCount <= numberOfRocks; rockCount++) {
			
			// Spawn a new rock
			Rock nextRock = spawn();
			
			// Begin movement.
			boolean jetGas = true;
			while(nextRock.isMoving()) {
				
				if(jetGas) {
					// Apply horizontal movement
					
					// 1. Obtain direction
					Vector2 direction = getHorizontalDirection();
					
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
					
					/*
					if(direction.equals(LEFT)) {
						System.out.println("Push rock left -"+ nextRock.getPosition());
					} else {
						System.out.println("Push rock right - "+ nextRock.getPosition());
					}*/
					
					
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
			
			if(nextRock.getMaxY() > maxY) {
				maxY = nextRock.getMaxY();
			}
			
			// TODO borrador de colliders inactivos
			// Un collider es inactivo si es imposible que nada choque contra él (hay una barra horizontal bloqueando por encima de él)
			// Solo podemos borrar colliders que estén a 5 unidades por debajo de la barra de corte
		}
		
		return maxY;
		
	}

	private boolean collidesWithAnything(Rock rock) {
		
		// Test collisions against all colliders
		Optional<Collider2D> result = colliders.stream().filter(collider -> rock.collidesWith(collider)).findAny();
		return result.isPresent();

	}

	private Vector2 getHorizontalDirection() {
		
		// Obtain next jet movement
		Vector2 direction = jetMovements[nextJetMovementIndex] == JET_LEFT ? LEFT : RIGHT;
		
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
		Vector2 position = new Vector2(SPAWN_OFFSET_X + 1, maxY + SPAWN_OFFSET_Y + 1);
		
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
	
		// Obtain next circular index
		nextRockIndex = (nextRockIndex+1) % rockShapeOrder.length;
		
		return spawnedRock;
	}
	
}
