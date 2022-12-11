package com.adventofcode.flashk.day09;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.adventofcode.flashk.common.Vector2;

public class RopeBridge {


	
	private Set<Vector2> passedCoordinates = new HashSet<>();
	
	private Knot headKnot = new Knot();
	private List<Knot> tailKnots = new ArrayList<>();
	
	private List<Movement> movements = new ArrayList<>();

	public RopeBridge(List<String> inputs) {
		
		for(String input : inputs) {
			movements.add(new Movement(input));
		}
		
		passedCoordinates.add(new Vector2(0,0));
		
	}

	public long solve(int numberOfKnots) {
		
		
		Knot lastTailKnot = null;
		for(int i = 0; i < numberOfKnots-1; i++) {
			
			if(i == 0) {
				lastTailKnot = new Knot(headKnot);
				
			} else {
				lastTailKnot = new Knot(lastTailKnot);
			}
			
			tailKnots.add(lastTailKnot);
		}
		
		
		for(Movement movement : movements) {
			performMovement(movement);
		}
		
		return passedCoordinates.size();
	}
	

	private void performMovement(Movement movement) {
		
		Vector2 head = headKnot.getPos();
		
		for(int steps = 0; steps < movement.getSteps(); steps++) {

			head.transform(movement.getDirection());
		
			for(Knot tailKnot : tailKnots) {
				moveTailKnot(tailKnot);
			}
		}
		
	}


	private void moveTailKnot(Knot tailKnot) {
		
		Vector2 head = tailKnot.getNext().getPos();
		Vector2 tail = tailKnot.getPos();
		
		int distance = (int) Vector2.distance(head, tail);

		if(distance > 1) {
			
			// Dados os puntos P = (p1,p2) y Q = (q1,q2)
			// El vector que une a ambos puntos (P -> Q) es:
			// Q - P =(q1-p1, q2-p2)

			// Aplicado a nuestro caso:
			// P = tail
			// Q = head
			
			// Por lo que :
			// 1. Restaríamos para obtener el vector dirección: head - tail
			// 2. Normalizamos el vector dirección.
			// 3. Transformamos la posición de tail en función del vector dirección 
			
			Vector2 direction = Vector2.substract(head, tail);
			direction.normalize();
			tail.transform(direction);
			
			if(tailKnot.isLast()) {
				passedCoordinates.add(new Vector2(tail.getX(), tail.getY()));
			}
		}
		
	}

}
