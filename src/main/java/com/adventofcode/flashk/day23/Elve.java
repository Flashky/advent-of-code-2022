package com.adventofcode.flashk.day23;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.adventofcode.flashk.common.Collider2D;
import com.adventofcode.flashk.common.Vector2;

import lombok.Getter;

public class Elve {

	@Getter
	private Vector2 position;
	
	@Getter
	private Collider2D collider;
	private Collider2D scanNorth;
	private Collider2D scanSouth;
	private Collider2D scanWest;
	private Collider2D scanEast;
	private Vector2 nextDirection;
	
	public Elve(int x, int y) {
		
		position = new Vector2(x,y);
		collider = new Collider2D(position);
		
		Vector2 upLeft = Vector2.upLeft();
		Vector2 upRight = Vector2.upRight();
		Vector2 downLeft = Vector2.downLeft();
		Vector2 downRight = Vector2.downRight();
		
		Vector2 start = Vector2.transform(position, upLeft);
		Vector2 end = Vector2.transform(position, upRight);
		scanNorth = new Collider2D(start,end);
		
		start = Vector2.transform(position, downLeft);
		end = Vector2.transform(position, downRight);
		scanSouth = new Collider2D(start,end);
		
		start = Vector2.transform(position, upLeft);
		end = Vector2.transform(position, downLeft);
		scanWest = new Collider2D(start,end);
		
		start = Vector2.transform(position, upRight);
		end = Vector2.transform(position, downRight);
		scanEast = new Collider2D(start,end);
		
	}

	public void move() {
		if(nextDirection != null) {
			position.transform(nextDirection);
			collider.transform(nextDirection);
			scanNorth.transform(nextDirection);
			scanSouth.transform(nextDirection);
			scanWest.transform(nextDirection);
			scanEast.transform(nextDirection);
		}
	}
	
	public Optional<Vector2> evaluate(Set<Elve> otherElves, Deque<Character> directionPriority) {
		
		// Copy directions priority
		Deque<Character> directionsPriorityCopy = new LinkedList<>();
		directionsPriorityCopy.addAll(directionPriority);
		
		
		// Exclude self collider and colliders that are too far away
		Set<Collider2D> elvesColliders = otherElves.stream()
													.filter(elve -> Vector2.distance(position, elve.getPosition()) < 2)
													.map(elve -> elve.getCollider())
													.filter(elveCollider -> !elveCollider.equals(collider))
													.collect(Collectors.toSet());
		
		long northHitCount = elvesColliders.stream().filter(elve -> elve.collidesWith(scanNorth)).count();
		long southHitCount = elvesColliders.stream().filter(elve -> elve.collidesWith(scanSouth)).count();
		long westHitCount = elvesColliders.stream().filter(elve -> elve.collidesWith(scanWest)).count();
		long eastHitCount = elvesColliders.stream().filter(elve -> elve.collidesWith(scanEast)).count();
		
		if(northHitCount == 0 &&
				southHitCount == 0 &&
				westHitCount == 0 &&
				eastHitCount == 0) {
			// Elve is alone - do not move
			return Optional.empty();
		}
		
		if(northHitCount > 0 &&
				southHitCount > 0 &&
				westHitCount > 0 &&
				eastHitCount > 0) {
			// Elve is surrounded - do not move
			return Optional.empty();
		}
		
		while(!directionPriority.isEmpty()) {
			Character direction = directionsPriorityCopy.poll();

			if(direction == 'N' && northHitCount == 0) {
				nextDirection = Vector2.up();
				return Optional.of(Vector2.transform(position, nextDirection));
			} else if(direction == 'S' && southHitCount == 0) { 
				nextDirection = Vector2.down();
				return Optional.of(Vector2.transform(position, nextDirection));
			} else if(direction == 'W' && westHitCount == 0) { 
				nextDirection = Vector2.left();
				return Optional.of(Vector2.transform(position, nextDirection));
			} else if(direction == 'E' && eastHitCount == 0) { 
				nextDirection = Vector2.right();
				return Optional.of(Vector2.transform(position, nextDirection));
			}

		}
		
		// This case should not happen, queue won't be empty.
		nextDirection = null;
		return Optional.empty();
	}
}
