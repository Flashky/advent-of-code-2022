package com.adventofcode.flashk.day15;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.adventofcode.flashk.common.Vector2;

import lombok.Getter;

@Getter
public class Sensor {

	private static final String SENSOR_BEACON_REGEX = "Sensor at x=(-?\\d*), y=(-?\\d*): closest beacon is at x=(-?\\d*), y=(-?\\d*)";
	private static final Pattern SEASON_BEACON_PATTERN = Pattern.compile(SENSOR_BEACON_REGEX);
	
	public Vector2 position;
	public Vector2 beaconPosition;
	public int distance;
	
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
		
	}
	
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
