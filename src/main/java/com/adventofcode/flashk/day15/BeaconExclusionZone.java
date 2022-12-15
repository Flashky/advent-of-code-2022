package com.adventofcode.flashk.day15;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
	
	Set<Vector2> lastScanLinePoints;
	private Set<Vector2> scanMap = new HashSet<>();
	
	private Line scanLine;
	
	public BeaconExclusionZone(List<String> inputs) {
		
		
		for(String input : inputs) {
			Sensor sensor = new Sensor(input);
			sensors.add(new Sensor(input));
			
			// Update min and max dimensions of the map
			//int x = Math.min(sensor.getPosition().getX(), sensor.getBeaconPosition().getX());
			minX = Math.min(minX, sensor.getPosition().getX()); 
			
			//x = Math.max(sensor.getPosition().getX(), sensor.getBeaconPosition().getX());
			maxX = Math.max(maxX, sensor.getPosition().getX());
			
//			//int y = Math.min(sensor.getPosition().getY(), sensor.getBeaconPosition().getY());
			minY = Math.min(minY, sensor.getPosition().getY());
			
			//y = Math.max(sensor.getPosition().getY(), sensor.getBeaconPosition().getY());
			maxY = Math.max(maxY, sensor.getPosition().getY());
		}
		
		System.out.println("test");
		
		
	}
	
	public long solveA(int y) {
		
		lastScanLinePoints = new HashSet<>();
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
					lastScanLinePoints.add(new Vector2(x,y));
				}

				
			}
		}
		
		return lastScanLinePoints.size() - 1;
	}
	
	public long solveB() {
		

		// Create scan map
		// Problema: no cabe una lista tan grande en memoria
		
		/*
		List<Vector2> scanMap = new ArrayList<>();
		for(int x = minX; x <= maxX; x++) {
			for(int y = minY; y <= maxY; y++) {
				scanMap.add(new Vector2(x,y));
			}
		}*/
		
		
		//List<Vector2> scanMap = new ArrayList<>();
		// Perform scan on y
		List<Vector2> scanMap = null;
		for(int y = minY; y <= maxY; y++) {
			solveA(y);
			
			scanMap = new ArrayList<>();
			for(int x = minX; x <= maxX; x++) {
				scanMap.add(new Vector2(x,y));
			}
			//System.out.println("y = "+y+" | Scanned points: "+lastScanLinePoints.size());
			lastScanLinePoints = lastScanLinePoints.stream().filter(p -> p.getX() >= minX && p.getX() <= maxX).collect(Collectors.toSet());
			//System.out.println("y = "+y+" | Scanned points in range: "+lastScanLinePoints.size());
			
			if(lastScanLinePoints.size() == maxX) {
				for(Vector2 scannedPoint : lastScanLinePoints) {
					scanMap.remove(scannedPoint);
				}
				
				if(!scanMap.isEmpty()) {
					break;
				}
			}
			
		}
		
		// TODO crear algún tipo de intersección que permita ver
		//Optional<Vector2> uniquePos = scanLinePoints.stream().filter(point -> !scanLinePoints.contains(point)).findFirst();
		
		

		Vector2 unique = scanMap.get(0);

		// Given a single vector position p, result is: p.x * DISTRESS_BEACON_MAX * p.y
		long result = unique.getX() * DISTRESS_BEACON_MAX + unique.getY();
		
		return result;
	}

	public long solveB2() {
		long result = 0;
		
		Queue<Sensor> sensorQueue = new LinkedList<>();
		sensorQueue.addAll(sensors);
		
		while(!sensorQueue.isEmpty()) {
			Sensor sensor = sensorQueue.poll();
			System.out.println("- Sensor: "+sensor.getPosition());
			for(Vector2 edgePos : sensor.getExternalEdge()) {

				// Comparar con los sensores restantes
				
				boolean coincidence = false;
				for(Sensor sensor2 : sensorQueue) {
					if(sensor2.getExternalEdge().contains(edgePos)) {
						coincidence = true;
						break;
					}
				}
				
				if(!coincidence) {
					System.out.println(edgePos);
					break;
				}
			}
		}
		
		return result;
		
	}
	public int calculateManhattanDistance(Vector2 origin, Vector2 destination) {
		
		// (x1,y1) and (x2,y2) = |x1-x2| + |y1-y2|
		int xDistance = Math.abs(origin.getX()-destination.getX());
		int yDistance = Math.abs(origin.getY()-destination.getY());
		
		return xDistance + yDistance;
	}
}
