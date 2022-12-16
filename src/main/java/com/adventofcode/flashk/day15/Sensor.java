package com.adventofcode.flashk.day15;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.adventofcode.flashk.common.Vector2;

import lombok.Getter;

@Getter
public class Sensor {

	private static final String SENSOR_BEACON_REGEX = "Sensor at x=(-?\\d*), y=(-?\\d*): closest beacon is at x=(-?\\d*), y=(-?\\d*)";
	private static final Pattern SEASON_BEACON_PATTERN = Pattern.compile(SENSOR_BEACON_REGEX);
	
	private static final Vector2 UP_RIGHT = Vector2.downRight();
	private static final Vector2 DOWN_RIGHT = Vector2.upRight();
	private static final Vector2 DOWN_LEFT = Vector2.upLeft();
	private static final Vector2 UP_LEFT = Vector2.downLeft();
	
	private Vector2 position;
	private Vector2 beaconPosition;
	private long manhattanDistance;
	
	// For iterating border part 2
	private int evaluatedCandidates = 0;
	private Vector2 left;
	private Vector2 right;
	private Vector2 top;
	private Vector2 bottom;
	private Vector2 currentBorderPos = null;
	
	private boolean leftReached = false;
	private boolean topReached = false;
	private boolean rightReached = false;
	private boolean bottomReached = false;
	
	public Sensor(String input) {
		
		Matcher matcher = SEASON_BEACON_PATTERN.matcher(input);
		matcher.find();
		
		// Add new sensor-beacon pair
		int sensorX = Integer.parseInt(matcher.group(1));
		int sensorY = Integer.parseInt(matcher.group(2));
		int beaconX = Integer.parseInt(matcher.group(3));
		int beaconY = Integer.parseInt(matcher.group(4));
		
		position = new Vector2(sensorX, sensorY);
		beaconPosition = new Vector2(beaconX, beaconY);
		manhattanDistance = Vector2.manhattanDistance(position, beaconPosition);

		// Sensor out of range limits
		int borderDistance = (int) manhattanDistance + 1;
		left =  Vector2.transform(position, new Vector2(-borderDistance,0));
		right = Vector2.transform(position, new Vector2(borderDistance,0));
		top = Vector2.transform(position, new Vector2(0,-borderDistance));
		bottom = Vector2.transform(position, new Vector2(0,borderDistance));
		currentBorderPos = new Vector2(left); // start at left

	}
	
	public Optional<Vector2> nextBorderPos(long maxRange) {
	
		// Iterador de posiciones
		// Si genero todas las posiciones de golpe, peto la memoria.
		
		// Nos vamos moviendo por el perímetro del sensor hasta encontrar una posición válida dentro del rango
		
		boolean isCandidate = true;


		// Start at left border and go to top
		while(!topReached) {
			currentBorderPos.transform(UP_RIGHT); // (1,-1) ya que la y disminuye hacia arriba
			topReached = currentBorderPos.equals(top);
			
			isCandidate = inRange(maxRange);
			
			if(isCandidate) {
				return Optional.of(currentBorderPos);
			}
			
		}
		
		// Go from top to right side
		while(!rightReached) {
			currentBorderPos.transform(DOWN_RIGHT);
			rightReached = currentBorderPos.equals(right);
			
			isCandidate = inRange(maxRange);
			
			if(isCandidate) {
				return Optional.of(currentBorderPos);
			}
		}
		
		// Go to right side to bottom
		while(!bottomReached) {
			currentBorderPos.transform(DOWN_LEFT);
			bottomReached = currentBorderPos.equals(bottom);
			
			isCandidate = inRange(maxRange);
			
			if(isCandidate) {
				return Optional.of(currentBorderPos);
			}
		}
		
		// Go to bottom to left
		while(!leftReached) {
			currentBorderPos.transform(UP_LEFT);
			leftReached = currentBorderPos.equals(left);
			
			isCandidate = inRange(maxRange);
			
			if(isCandidate) {
				return Optional.of(currentBorderPos);
			}
		}
		
		
		return Optional.empty();
		
	}

	private boolean inRange(long maxRange) {
		return currentBorderPos.getX() >= 0 && currentBorderPos.getX() <= maxRange && currentBorderPos.getY() >= 0 && currentBorderPos.getY() <= maxRange;
	}

	public boolean isInRange(Vector2 point) {
		return manhattanDistance >= Vector2.manhattanDistance(position, point);
	}
	
	private boolean isInRange(int y) {
		
		if(position.getY() == y) {
			return true;
		}
		
		if(position.getY() < y) {
			long maxY = position.getY() + manhattanDistance;
			return maxY >= y;
		}
		
		// position.y > y
		long minY = position.getY() - manhattanDistance;
		return minY <= y;
	}
	
	public int distanceTo(int y) {
		
		if(!isInRange(y)) {
			return -1; // Does not intersect
		}
		
		Vector2 intersection = new Vector2(position.getX(),y);
		double distance = Vector2.distance(position, intersection);
		
		return (int) distance;
	}

}
