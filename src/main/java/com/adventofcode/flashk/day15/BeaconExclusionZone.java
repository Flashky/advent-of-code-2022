package com.adventofcode.flashk.day15;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import com.adventofcode.flashk.common.Vector2;

public class BeaconExclusionZone {

	private static final String SENSOR_BEACON_REGEX = "Sensor at x=(-?\\d*), y=(-?\\d*): closest beacon is at x=(-?\\d*), y=(-?\\d*)";
	private static final Pattern SEASON_BEACON_PATTERN = Pattern.compile(SENSOR_BEACON_REGEX);
	private static final int DISTRESS_BEACON_MAX= 4000000;
	
	private List<Sensor> sensors = new ArrayList<>(); // Sensor-Beacon map
	
	private int minX = Integer.MAX_VALUE;
	private int maxX = Integer.MIN_VALUE;
	private int minY = Integer.MAX_VALUE;
	private int maxY = Integer.MIN_VALUE;
	
	private Set<Vector2> scanMap = new HashSet<>();
	private Set<Vector2> scanLinePoints = new HashSet<>();
	
	private Line scanLine;
	
	public BeaconExclusionZone(List<String> inputs) {
		
		
		for(String input : inputs) {
			Sensor sensor = new Sensor(input);
			sensors.add(new Sensor(input));
			
			// Update min and max dimensions of the map
			int x = Math.min(sensor.getPosition().getX(), sensor.getBeaconPosition().getX());
			minX = Math.min(minX, x); 
			
			x = Math.max(sensor.getPosition().getX(), sensor.getBeaconPosition().getX());
			maxX = Math.max(maxX, x);
			
			int y = Math.min(sensor.getPosition().getY(), sensor.getBeaconPosition().getY());
			minY = Math.min(minY, y);
			
			y = Math.max(sensor.getPosition().getY(), sensor.getBeaconPosition().getY());
			maxY = Math.max(maxY, y);
		}
		
		System.out.println("test");
		
		
	}
	
	public long solveA(int y) {

		for(Sensor sensor : sensors) {
			
			int distanceToY = sensor.distanceTo(y);

			// Sensor reaches scanline
			if(distanceToY >= 0) {

				int manhattanDistance = sensor.getDistance();
				int missingDistance = manhattanDistance - distanceToY;
				/*
				System.out.println("- Sensor: "+sensor.getPosition());
				System.out.println("Manhattan distance: "+manhattanDistance);
				System.out.println("Missing distance: "+missingDistance);
				System.out.println();
				*/
				
				int xLeft = sensor.getPosition().getX() - missingDistance;
				int xRight = sensor.getPosition().getX() + missingDistance;
				
				for(int x = xLeft; x <= xRight; x++) {
					scanLinePoints.add(new Vector2(x,y));
				}

				
			}
		}
		
		return scanLinePoints.size() - 1;
	}
	
	public long solveB() {
		

		// Create scan map
		List<Vector2> scanMap = new ArrayList<>();
		for(int x = 0; x <= DISTRESS_BEACON_MAX; x++) {
			for(int y = 0; y <= DISTRESS_BEACON_MAX; y++) {
				scanMap.add(new Vector2(x,y));
			}
		}
		
		// Perform scan on y
		for(int y = 0; y <= DISTRESS_BEACON_MAX; y++) {
			solveA(y);
		}
		
		// TODO crear algún tipo de intersección que permita ver
		Optional<Vector2> uniquePos = scanLinePoints.stream().filter(point -> !scanLinePoints.contains(point)).findFirst();
		
		for(Vector2 scannedPoint : scanLinePoints) {
			scanMap.remove(scannedPoint);
		}
		Vector2 unique = scanMap.get(0);
		
		// Estas son todas las posiciones que SI coinciden.
		// Habría que cruzarlo con las que NO coincidan.
		/*Set<Vector2> possiblePositions = scanLinePoints.stream()
			.filter(p -> p.getX() >= 0 && p.getX() < undetectedBeaconMaxPos)
			.filter(p -> p.getY() >= 0 && p.getY() < undetectedBeaconMaxPos)
			.collect(Collectors.toSet());
		*/
		
		// Given a single vector position p, result is: p.x * DISTRESS_BEACON_MAX * p.y
		long result = unique.getX() * DISTRESS_BEACON_MAX + unique.getY();
		
		return result;
		//return (x * undetectedBeaconMax) + y;
	}

	public int calculateManhattanDistance(Vector2 origin, Vector2 destination) {
		
		// (x1,y1) and (x2,y2) = |x1-x2| + |y1-y2|
		int xDistance = Math.abs(origin.getX()-destination.getX());
		int yDistance = Math.abs(origin.getY()-destination.getY());
		
		return xDistance + yDistance;
	}
}
