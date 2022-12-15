package com.adventofcode.flashk.day15;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.adventofcode.flashk.common.Vector2;

import lombok.Getter;

@Getter
public class Sensor {

	private static final String SENSOR_BEACON_REGEX = "Sensor at x=(-?\\d*), y=(-?\\d*): closest beacon is at x=(-?\\d*), y=(-?\\d*)";
	private static final Pattern SEASON_BEACON_PATTERN = Pattern.compile(SENSOR_BEACON_REGEX);
	
	private Vector2 position;
	private Vector2 beaconPosition;
	private int distance;
	private Set<Vector2> externalEdge = new HashSet<>();
	
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
		calculateManhattanDistance();
		calculateEdge();
	}
	
	private void calculateEdge() {
		
		// Number of edge points must be = (manhattanDistance + 1) * 4
		int edgeDistance = distance + 1;
		
		Vector2 leftLimit = Vector2.transform(position, new Vector2(-edgeDistance,0));
		Vector2 rightLimit = Vector2.transform(position, new Vector2(edgeDistance,0));
		Vector2 upLimit = Vector2.transform(position, new Vector2(0,-edgeDistance));
		Vector2 downLimit = Vector2.transform(position, new Vector2(0,edgeDistance));
		
		// Start at the left edge
		Vector2 currentPos = new Vector2(leftLimit);
		
		// Go diagonal up right
		Vector2 direction = Vector2.downRight();
		do {
			externalEdge.add(new Vector2(currentPos));
			currentPos.transform(direction);
		} while(!currentPos.equals(upLimit));
		
		// Go diagonal down right
		direction = Vector2.upRight();
		do {
			externalEdge.add(currentPos);
			currentPos.transform(direction);
		} while(!currentPos.equals(rightLimit));
		
		// Go diagonal down left
		direction = Vector2.upLeft();
		do {
			externalEdge.add(currentPos);
			currentPos.transform(direction);
		} while(!currentPos.equals(downLimit));
		
		// Go diagonal up left
		direction = Vector2.downLeft();
		do {
			externalEdge.add(currentPos);
			currentPos.transform(direction);
		}while(!currentPos.equals(leftLimit));
		
		externalEdge.add(currentPos);
		System.out.println("Finished edge");
	}

	/*
	public boolean collide(Sensor other) {
		
		boolean collide = false;
		Iterator<Vector2> edgeIterator = externalEdge.iterator();
		
		while((!collide) && edgeIterator.hasNext()) {
			if(other.externalEdge.contains(edgeIterator.next())) {
				collide = true;
			}
		}
		
		return collide;
	}*/
	public boolean isInRange(int y) {
		
		if(position.getY() == y) {
			return true;
		}
		
		if(position.getY() < y) {
			int maxY = position.getY() + distance;
			return maxY >= y;
		}
		
		// position.y > y
		int minY = position.getY() - distance;
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
	
	private void calculateManhattanDistance() {
		
		// (x1,y1) and (x2,y2) = |x1-x2| + |y1-y2|
		int xDistance = Math.abs(position.getX()-beaconPosition.getX());
		int yDistance = Math.abs(position.getY()-beaconPosition.getY());
		
		distance = xDistance + yDistance;
	}
}
