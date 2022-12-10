package com.adventofcode.flashk.day09;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.adventofcode.flashk.common.Vector2;

public class RopeBridge {

	// Constant vectors to avoid build the same vectors again
	private static final Vector2 RIGHT = Vector2.right();
	private static final Vector2 LEFT = Vector2.left();
	private static final Vector2 UP = Vector2.up();
	private static final Vector2 DOWN = Vector2.down();
	private static final Vector2 UP_RIGHT = Vector2.upRight();
	private static final Vector2 UP_LEFT = Vector2.upLeft();
	private static final Vector2 DOWN_RIGHT = Vector2.downRight();
	private static final Vector2 DOWN_LEFT = Vector2.downLeft();
	
	private Set<Vector2> passedCoordinates = new HashSet<>();
	
	private Knot headKnot = new Knot();
	private List<Knot> tailKnots = new ArrayList<>();
	
	private List<Movement> movements = new ArrayList<>();

	private Vector2 diagonalDirectionA = new Vector2(1,2);
	private Vector2 diagonalDirectionB = new Vector2(2,1);
	private Vector2 diagonalDirectionC = new Vector2(2,2);
	
	
	
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
		Vector2 distance = Vector2.substractAbs(head, tail);
		
		if((distance.getX() > 1) && (distance.getY() == 0)) {
			
			// Left & Right
			if(head.getX() > tail.getX()) {
				tail.transform(RIGHT);
			} else {
				tail.transform(LEFT);
			}
			
			if(tailKnot.isLast()) {
				passedCoordinates.add(new Vector2(tail.getX(), tail.getY()));
			}
			
			
		} else if((distance.getX() == 0) && (distance.getY() > 1)) {
			
			// Up & Down
			
			if(head.getY() > tail.getY()) {
				tail.transform(UP);
			} else {
				tail.transform(DOWN);
			}

			if(tailKnot.isLast()) {
				passedCoordinates.add(new Vector2(tail.getX(), tail.getY()));
			}
			
		} else if(distance.equals(diagonalDirectionA) || distance.equals(diagonalDirectionB) || distance.equals(diagonalDirectionC)) {
			
			if(head.getY() > tail.getY()) {
				
				// Diagonal up
				
				if(head.getX() > tail.getX()) {
					tail.transform(UP_RIGHT);
					if(tailKnot.isLast()) {
						passedCoordinates.add(new Vector2(tail.getX(), tail.getY()));
					}
				} else if (head.getX() < tail.getX()) {
					tail.transform(UP_LEFT);
					if(tailKnot.isLast()) {
						passedCoordinates.add(new Vector2(tail.getX(), tail.getY()));
					}
				}
				
			} else if(head.getY() < tail.getY()) {

				// Diagonal down
				
				if(head.getX() > tail.getX()) {
					tail.transform(DOWN_RIGHT);
					if(tailKnot.isLast()) {
						passedCoordinates.add(new Vector2(tail.getX(), tail.getY()));
					}
				} else if (head.getX() < tail.getX()) {
					tail.transform(DOWN_LEFT);
					if(tailKnot.isLast()) {
						passedCoordinates.add(new Vector2(tail.getX(), tail.getY()));
					}
				}
				
			}
			
		}
	}

}
