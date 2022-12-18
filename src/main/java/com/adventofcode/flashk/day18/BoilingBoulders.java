package com.adventofcode.flashk.day18;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class BoilingBoulders {

	private List<Cube> lavaDroplets;
	

	public BoilingBoulders(List<String> inputs) {
		lavaDroplets = inputs.stream().map(Cube::new).collect(Collectors.toList());
	}
	
	public long solveA() {
		
		compareZ();
		compareY();
		compareX();
		
		AtomicLong result = new AtomicLong(0L);
		lavaDroplets.stream().forEach(c -> result.getAndAdd(c.getOpenSides()));
		
		return result.get();
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
		
		int decrementedSides = 0;
		// El último cubo no se evalua
		for(int i = 0; i < orderedByX.size()-1; i++) {
			
			// Comparar por pares
			Cube first = orderedByX.get(i);
			Cube second = orderedByX.get(i+1); 
			
			if(first.isAdjacent(second) && first.isAdjacentX(second)) {
				first.decrementOpenSides();
				second.decrementOpenSides();
				decrementedSides = decrementedSides+2;
			}
			
		}
		
		//System.out.println("Decremented sides on X: "+decrementedSides);

	}
}
