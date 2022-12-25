package com.adventofcode.flashk.day18;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import com.adventofcode.flashk.common.Vector3;

public class BoilingBoulders {

	private Set<Cube> lavaDroplets;
	
	private int minX;
	private int maxX;
	private int minY;
	private int maxY;
	private int minZ;
	private int maxZ;
	
	public BoilingBoulders(List<String> inputs) {
		lavaDroplets = inputs.stream().map(Cube::new).collect(Collectors.toSet());
	}
	
	public long solveA() {
		
		compareZ();
		compareY();
		compareX();
		
		// Sum all open sides
		return lavaDroplets.stream()
							.map(Cube::getOpenSides)
							.reduce(0, Integer::sum);
	}

	public long solveB() {
		
		// Calculate max dimensions
		calculateLimits();
		
		// Fill with air cubes
		fillWithAirCubes();
		
		// Recalculate open sides
		return solveA();
	}
	
	private void fillWithAirCubes() {
		for(int x = minX; x <= maxX; x++) {
			for(int y = minY; y <= maxY; y++) {
				for(int z = minZ; z <= maxZ; z++) {
					Cube cube = getOrCreate(new Vector3(x,y,z));
					dfs(cube);
					
				}
			}
		}
		
	}

	private Queue<Cube> getAdjacents(Cube lavaDroplet) {
		Vector3 initialPos = lavaDroplet.getPos();
		
		Queue<Cube> adjacents = new LinkedList<>();
		
		adjacents.add(getOrCreate(Vector3.transform(Vector3.left(), initialPos)));
		adjacents.add(getOrCreate(Vector3.transform(Vector3.right(), initialPos)));
		adjacents.add(getOrCreate(Vector3.transform(Vector3.up(), initialPos)));
		adjacents.add(getOrCreate(Vector3.transform(Vector3.down(), initialPos)));
		adjacents.add(getOrCreate(Vector3.transform(Vector3.forward(), initialPos)));
		adjacents.add(getOrCreate(Vector3.transform(Vector3.backward(), initialPos)));
		
		return adjacents;
	}

	private Cube getOrCreate(Vector3 pos) {
		
		Optional<Cube> cube = lavaDroplets.stream().filter(c -> c.getPos().equals(pos)).findFirst();
		
		if(cube.isPresent()) {
			return cube.get();
		} else {
			// Create an air cube
			return new Cube(pos, false);
		}

	}
	
	private boolean dfs(Cube cube) {
		
		// Algoritmo de flood
		/*
			El algoritmo busca rellenar los cubos faltantes de aire.
			
			Para ello, va añadiendo cubos candidatos al conjunto de droplets y va evaluando recursivamente por los vecinos:
			- Caso base: un vecino de lava es una solución potencial (true).
			- Caso base: un cubo que se sale de límites indica que no hay solución por ese camino (false).
			
			Para que una solución desde un cubo P sea válida:
			- Caso recursivo: las soluciones de sus vecinos han de ser válidas (acabar en un cubo de lava y no salirse del límite) 
			
			Entonces, vamos añadiendo los potenciales cubos de solución al conjunto de cubos de lava (aunque sean de aire). Si todos sus vecinos dan una solución válida, el algoritmo daría true y podríamos proceder a recalcular las caras.
			Si alguno de los vecinos no da una solución válida, eliminamos los cubos de aire del conjunto de cubos de lava.
		 */
	
		// 
		// Caso base - Borde de lava
		if(cube.isLava() || cube.isVisited()) {
			return true;
		}
		
		
		// Caso base - Out of Range
		if(cube.getPos().getX() < minX 
				|| cube.getPos().getX() > maxX
			|| cube.getPos().getY() < minY 
			|| cube.getPos().getY() > maxY
			|| cube.getPos().getZ() < minZ 
			|| cube.getPos().getZ() > maxZ)
		{
			// No hay solución válida por este camino
			return false;
		}

		// El cubo a evaluar es un cubo de aire (si no, se saldría por el CB)
		// Lo añadimos como posible cubo solución
		lavaDroplets.add(cube); 
		cube.setVisited(true);
		
		// Calculamos adyacentes, sean de lava o de aire
		Queue<Cube> adjacents = getAdjacents(cube);
		
		// Aquí guardaremos todos los cubos de aire para eliminarlos luego si un caso no tiene solución
		// También añadimos el cubo actual a la solución
		Set<Cube> airCubes = new HashSet<>();
		airCubes.add(cube);
		
		boolean isSolution = true;
		while(isSolution && !adjacents.isEmpty()) {
			
			// Verificar arriba, abajo, izquierda, derecha, delante y detrás.
			// Si todas esas soluciones dan true, entonces es un cubo que está rodeado de lava eventualmente en todas las direcciones
			
			Cube next = adjacents.poll();		
			
			if(!next.isLava()) {
				airCubes.add(next);
			}
			
			isSolution = dfs(next);
		}
		
		
		if(!isSolution) {
			lavaDroplets.removeAll(airCubes);
			cube.setVisited(false);
			return false;
		}
		
		return true;

	}


	private void calculateLimits() {
		
		// X limits
		Comparator<Cube> comparator = Comparator.<Cube>comparingInt(c -> c.getPos().getX());
		minX = lavaDroplets.stream().min(comparator).get().getPos().getX();
		maxX = lavaDroplets.stream().max(comparator).get().getPos().getX();
		
		// Y limits
		comparator = Comparator.<Cube>comparingInt(c -> c.getPos().getY());
		minY = lavaDroplets.stream().min(comparator).get().getPos().getY();
		maxY = lavaDroplets.stream().max(comparator).get().getPos().getY();
		
		// Z limits
		comparator = Comparator.<Cube>comparingInt(c -> c.getPos().getZ());
		minZ = lavaDroplets.stream().min(comparator).get().getPos().getZ();
		maxZ = lavaDroplets.stream().max(comparator).get().getPos().getZ();
		
	}

	private void compareZ() {

		// Order from bigger group to smaller group (xyz - in excel zyx)
		Comparator<Cube> comparator = Comparator.<Cube>comparingInt(c -> c.getPos().getX())
												.thenComparingInt(c -> c.getPos().getY())
												.thenComparingInt(c -> c.getPos().getZ());
		
		List<Cube> orderedByZ = lavaDroplets.stream().sorted(comparator).collect(Collectors.toList());
		
		// El último cubo no se evalua
		for(int i = 0; i < orderedByZ.size()-1; i++) {
			
			// Comparar por pares
			Cube first = orderedByZ.get(i);
			Cube second = orderedByZ.get(i+1); 
			
			if(first.isAdjacent(second) && first.isAdjacentZ(second)) {
				first.decrementOpenSides();
				second.decrementOpenSides();
			}
			
		}

	}
	
	private void compareY() {

		// Order from bigger group to smaller group (xzy - in excel yzx)
		Comparator<Cube> comparator = Comparator.<Cube>comparingInt(c -> c.getPos().getX())
												.thenComparingInt(c -> c.getPos().getZ())
												.thenComparingInt(c -> c.getPos().getY());
		
		List<Cube> orderedByY = lavaDroplets.stream().sorted(comparator).collect(Collectors.toList());
		
		// El último cubo no se evalua
		for(int i = 0; i < orderedByY.size()-1; i++) {
			
			// Comparar por pares
			Cube first = orderedByY.get(i);
			Cube second = orderedByY.get(i+1); 
			
			if(first.isAdjacent(second) && first.isAdjacentY(second)) {
				first.decrementOpenSides();
				second.decrementOpenSides();
			}
			
		}

	}
	
	private void compareX() {

		// Order from bigger group to smaller group (zyx - in excel xyz)
		Comparator<Cube> comparator = Comparator.<Cube>comparingInt(c -> c.getPos().getZ())
												.thenComparingInt(c -> c.getPos().getY())
												.thenComparingInt(c -> c.getPos().getX());
		
		List<Cube> orderedByX = lavaDroplets.stream().sorted(comparator).collect(Collectors.toList());
		
		// El último cubo no se evalua
		for(int i = 0; i < orderedByX.size()-1; i++) {
			
			// Comparar por pares
			Cube first = orderedByX.get(i);
			Cube second = orderedByX.get(i+1); 
			
			if(first.isAdjacent(second) && first.isAdjacentX(second)) {
				first.decrementOpenSides();
				second.decrementOpenSides();
			}
			
		}

	}
}
