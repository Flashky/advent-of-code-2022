package com.adventofcode.flashk.day17;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
	private long minY = 1; // Purge bar

	// Collider manager
	private Set<Collider2DL> colliders = new HashSet<>();
	
	// Part 2
	private TetrisPattern currentTetrisPattern;
	private List<TetrisPattern> tetrisFullPattern = new ArrayList<>();
	
	
	public PyroclasticFlow(String input) {
		jetMovements = input.toCharArray();
		
		// Initialize map colliders
		Vector2L start = new Vector2L();
		Vector2L end = new Vector2L(8,0);
		
		colliders.add(new Collider2DL(start,end)); // Floor
		colliders.add(new Collider2DL(start, new Vector2L(0, Integer.MAX_VALUE))); // Left border
		colliders.add(new Collider2DL(end, new Vector2L(end.getX(), Integer.MAX_VALUE))); // Right border
		
		
	}
	
	/*
	 Problemas a tratar:

	Parte 2
	
	Necesitaremos ver si se repite algún patrón. 
	Una forma de visualizarlo es como una cadena.
	
	Por ejemplo ``ABCDXABCDX`` sería un patrón ``ABCDX`` que se repite dos veces.
	
	Pero ojo, puede haber patrones dentro de patrones:
	1. ``ABCDABCDXABCDABCDX``, tenemos un patrón ABCDABCD que se repite dos veces.
	2. De repente, aparece una ``X`` y el patrón se rompe.
	3. Finalmente el patrón se ve que es ``ABCDABCDX`` 
	
	Para el tetris necesitaríamos una estructura de datos que nos permita identificar un estado único.

	QUIERO una estructura de datos que permita modelar un estado
	PARA poder guardar un listado de estados y comprobar si en algún momento se repite
	
	¿Ahora bien, qué es un estado?
	El problema tiene claramente un patrón que se repite continuamente: las formas de las rocas, dando un nombre a cada una:
	- A: Barra horizontal
	- B: Cruz
	- C: L
	- D: Barra vertical
	- E: cuadado
	
	Vemos que el tipo de roca no es suficiente para definir un estado, continuamente se repite un ciclo: "ABCDE ABCDE ABCDE..."
	Por lo tanto, para definir un estado hace falta algo más. 
	
	Opciones:
	
	1. La coordenada horizontal en la que termina la roca:
		1.1 El campo de juego va desde las coordenadas x=1 hasta x=7. Por lo que podemos guardar el nombre de la roca y su coordenada:
			A,1 -> Barra horizontal que ha caido en la coordenada x=1
			B,6 -> Cruz que ha caído en la coordenada x=6
			
	2. En el caso de que lo anterior no sea suficiente, podríamos guardar también el listado de movimientos que ha hecho dicha figura. Por ejemplo:
		A,1,>>< -> Barra horizontal que ha caído en la coordenada x=1 después de moverse dos veces a la derecha y una vez a la izquierda
		
	Cuantas más restricciones pongamos, más fácil será evitar que el patrón se repita por casualidad.
	
	Por lo tanto, la estructura de datos podría ser un objeto que guarde los tres datos:
	- Shape: String / Character
	- Position: int
	- Movements: String
	
	La estructura necesitaría un equals para comparar con otra estructura.
	
	Ahora nos falta otra estructura:.
	
	QUIERO una estructura que almacene un listado de patrones
	PARA poder comparar con el patrón actual
	
	
	
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
			
			// Add current rock to pattern list
			currentTetrisPattern.setX(nextRock.getPosition().getX());
			System.out.println(currentTetrisPattern);
			tetrisFullPattern.add(currentTetrisPattern);
		}
		
		return maxY;
		
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
		currentTetrisPattern.addMovement(jetMovements[nextJetMovementIndex]);
		
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
		currentTetrisPattern = new TetrisPattern();	
		currentTetrisPattern.setShape(rockShapeOrder[nextRockIndex]);
		
		// Obtain next circular index
		nextRockIndex = (nextRockIndex+1) % rockShapeOrder.length;
		
		return spawnedRock;
	}
	
}
