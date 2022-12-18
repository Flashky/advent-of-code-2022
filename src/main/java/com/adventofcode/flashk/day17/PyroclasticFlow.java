package com.adventofcode.flashk.day17;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.adventofcode.flashk.common.Collider2D;
import com.adventofcode.flashk.common.Pair;
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
	private Set<Collider2D> colliders = new HashSet<>();
	
	// Part 2
	//private Set<Vector2L> min
	
	public PyroclasticFlow(String input) {
		jetMovements = input.toCharArray();
		
		// Initialize map colliders
		Vector2L start = new Vector2L();
		Vector2L end = new Vector2L(8,0);
		
		colliders.add(new Collider2D(start,end)); // Floor
		colliders.add(new Collider2D(start, new Vector2L(0, Integer.MAX_VALUE))); // Left border
		colliders.add(new Collider2D(end, new Vector2L(end.getX(), Integer.MAX_VALUE))); // Right border
		
		
	}
	
	/*
	 Problemas a tratar:
	 
	 [x] Gestión de rocas
	 	[x] Creación de rocas de todas las formas respecto a una posición (x,y) ya calculada.
	 	[x] Movimiento de roca según un vector dirección.
	 	[x] Cálculo de colisiones contra otras rocas o bordes del mapa.
	 
	 [x] Spawneo de rocas:
	 	[x] Calcular la posición inicial (x,y) de la roca
	 		[x] Coordenada x: 2 unidades respecto a la izquierda (x = 2)
	 		[x] Coordenada y: 3 unidades respecto al collider que esté en la posición más alta:
	 			[x] Caso inicial: el único collider que cuenta es colliderBottom situado en y = 0
	 			[x] Caso tras primera roca: collider con y más alta entre todos los colliders de todas las rocas.
	 	[x] Creación de la roca que corresponda según una lista circular.
	 
	 [x] Movimiento:
	 	[x] Cálculo de vector dirección para el jet stream
	 	[x] Gestor de alternancia de movimiento lateral - hacia abajo.
	 	[x] Comprobación de colisión al hacer movimiento lateral.
	 	[x] Comprobación de colisión al hacer movimiento vertical.
	 

		// Parte 2

		[ ] Calcular para cada columna cuál es su y más baja.
		[ ] Descartar colliders por debajo de cierto umbral.
			[ ] Opción 1: borrar colliders por debajo de ciertos umbrales. Borrar lleva a menos comparaciones
			[ ] Opción 2: filtrar los colliders por debajo de ciertos umbrales.
			[ ] Opción 3: Aun borrando colliders es demasiado, hay que buscar un patrón
		
	 */
	
	public long solveA(long numberOfRocks) {
		
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
			
			// TODO la solución de borrar colliders no es suficiente para un volumen de datos tan alto
			// La solución va por buscar un patrón que se repita
			// ¿Cómo almacenamos un patrón? ¿cómo lo comparamos? al principio no hay nada con lo que comparar.
			//cleanColliderPool();
		}
		
		return maxY;
		
	}

	private void cleanColliderPool() {
		
		boolean found = false;
		long y = minY;
		
		Set<Vector2L> collisionPoints = new HashSet<>(); // TODO igual esto debería empezar en la última barra de bloqueo siempre
		// Inicialmente la barra de bloqueo se empieza a evaluar para y = 0
		// cuando se encuentre un bloqueo, se puede actualizar para y = y barra bloqueo
		
		collisionPoints.add(new Vector2L(1,y));
		collisionPoints.add(new Vector2L(2,y));
		collisionPoints.add(new Vector2L(3,y));
		collisionPoints.add(new Vector2L(4,y));
		collisionPoints.add(new Vector2L(5,y));
		collisionPoints.add(new Vector2L(6,y));
		collisionPoints.add(new Vector2L(7,y));
		
		while(!found && y < maxY) {
			
			Iterator<Vector2L> pointIterator = collisionPoints.iterator();
			int collisionCount = 0;
			while(collisionCount < 7 && pointIterator.hasNext()) {
				
				Vector2L point = pointIterator.next();
				Optional<Collider2D> collider = colliders.stream().filter(c -> c.collidesWith(point)).findAny();
				
				if(collider.isPresent()) {
					collisionCount++;
				}
			}
			
			// Si tras haber recorrido todos los puntos, todos tenían colisión:
			if(collisionCount == 7) {
				
				found = true;
				maxY = y;
				
				// TODO Borrar todos los colliders por debajo de y - 5
				long cutOffsetY = y - 5;
				colliders = colliders.stream().filter(c -> c.getMinY() > cutOffsetY).collect(Collectors.toSet());
			} else {
				// Pasamos a evaluar la siguiente fila
				y++;
				collisionPoints.stream().forEach(p -> p.transformY(1));
			}
		
		}
		// Cuidado: los colliders del lateral y de abajo no se deben borrar.
		// Comparar colliders
		
	}

	private boolean collidesWithAnything(Rock rock) {
		
		// Test collisions against all colliders
		Optional<Collider2D> result = colliders.stream().filter(collider -> rock.collidesWith(collider)).findAny();
		return result.isPresent();

	}

	private Vector2L getHorizontalDirection() {
		
		// Obtain next jet movement
		Vector2L direction = jetMovements[nextJetMovementIndex] == JET_LEFT ? LEFT : RIGHT;
		
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
	
		// Obtain next circular index
		nextRockIndex = (nextRockIndex+1) % rockShapeOrder.length;
		
		return spawnedRock;
	}
	
}
