package com.adventofcode.flashk.day09;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.adventofcode.flashk.common.Vector2;

public class RopeBridge {

	
	private Set<Vector2> passedCoordinates = new HashSet<>();
	private Vector2 head = new Vector2(0,0);
	private Vector2 tail = new Vector2(0,0);
	
	private List<Movement> movements = new ArrayList<>();
	
	private Map<Vector2, Vector2> movementMap = new HashMap<>();
	private Vector2 diagonalDirectionA = new Vector2(1,2);
	private Vector2 diagonalDirectionB = new Vector2(2,1);
	
	public RopeBridge(List<String> inputs) {
		
		for(String input : inputs) {
			movements.add(new Movement(input));
		}
		
		passedCoordinates.add(new Vector2(0,0));
		
	}
	
	
	public long solveA() {
		
		for(Movement movement : movements) {
			
			performMovement(movement);

		}
		
		return passedCoordinates.size();
	}


	private void performMovement(Movement movement) {
		
		for(int steps = 0; steps < movement.getSteps(); steps++) {
			head.transform(movement.getDirection());
		
			Vector2 distance = Vector2.substractAbs(head, tail);
			
			if((distance.getX() > 1) && (distance.getY() == 0)) {
				
				// Left & Right
				
				tail.transform(movement.getDirection());
				passedCoordinates.add(new Vector2(tail.getX(), tail.getY()));
				
			} else if((distance.getX() == 0) && (distance.getY() > 1)) {
				
				// Up & Down
				
				tail.transform(movement.getDirection());
				passedCoordinates.add(new Vector2(tail.getX(), tail.getY()));
				
			} else if(distance.equals(diagonalDirectionA) || distance.equals(diagonalDirectionB)) {
				
				if(head.getY() > tail.getY()) {
					
					// Diagonal up
					
					if(head.getX() > tail.getX()) {
						tail.transform(new Vector2(1,1)); // Diagonal up right
						passedCoordinates.add(new Vector2(tail.getX(), tail.getY()));
					} else if (head.getX() < tail.getX()) {
						tail.transform(new Vector2(-1,1)); // Diagonal up left
						passedCoordinates.add(new Vector2(tail.getX(), tail.getY()));
					}
					
				} else if(head.getY() < tail.getY()) {

					// Diagonal down
					
					if(head.getX() > tail.getX()) {
						tail.transform(new Vector2(1,-1)); // Diagonal up right
						passedCoordinates.add(new Vector2(tail.getX(), tail.getY()));
					} else if (head.getX() < tail.getX()) {
						tail.transform(new Vector2(-1,-1)); // Diagonal up left
						passedCoordinates.add(new Vector2(tail.getX(), tail.getY()));
					}
					
				}
				
			}
			
			System.out.println(distance);
		}
		
	}


	private void moveTailUp(Movement movement) {
		
		if(head.getX() > tail.getX()) {
			tail.transform(new Vector2(1,1)); // Diagonal up-right
		} else if(head.getX() < tail.getX()) {
			tail.transform(new Vector2(-1,1)); // Diagonal up-left
		} else {
			tail.transform(movement.getDirection()); // Just up
		}
		
		// Diagonal up-left
		passedCoordinates.add(new Vector2(tail.getX(), tail.getY()));
	}
}
