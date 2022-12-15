package com.adventofcode.flashk.day15;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.adventofcode.flashk.common.Vector2;

public class BeaconExclusionZone {

	private static final int DISTRESS_BEACON_MAX = 4000000;
	
	private List<Sensor> sensors = new ArrayList<>(); // Sensor-Beacon map
	
	private Set<Vector2> lastScanLinePoints;
	
	public BeaconExclusionZone(List<String> inputs) {
		
		sensors = inputs.stream().map(Sensor::new).collect(Collectors.toList());
		
	}
	
	public long solveA(int y) {
		
		lastScanLinePoints = new HashSet<>();
		for(Sensor sensor : sensors) {
			
			int distanceToY = sensor.distanceTo(y);

			// Sensor is in range of scanline
			if(distanceToY >= 0) {

				int manhattanDistance = (int) sensor.getManhattanDistance();
				int missingDistance = (int) manhattanDistance - distanceToY;
				
				int xLeft = sensor.getPosition().getX() - missingDistance;
				int xRight = sensor.getPosition().getX() + missingDistance;
				
				for(int x = xLeft; x <= xRight; x++) {
					lastScanLinePoints.add(new Vector2(x,y));
				}

				
			}
		}
		
		return lastScanLinePoints.size() - 1;
	}

	public long solveB3(long maxRange) {
		
		Vector2 lostBeaconPosition = null;
		boolean found = false;
		Iterator<Sensor> candidateSensors = sensors.iterator();
		
		while(!found && candidateSensors.hasNext()) {
			
			// Elegimos un sensor a evaluar
			Sensor currentSensor = candidateSensors.next();
			
			// Escogemos una posición candidata para la baliza.
			// Las posiciones candidatas se situan en el perímetro del sensor (mhd + 1)
			Optional<Vector2> candidate = currentSensor.nextBorderPos(maxRange);
			while(!found && candidate.isPresent()) {

				boolean isCandidate = true;
				
				// El candidato deberá estar fuera de rango de todos los sensores
				Iterator<Sensor> testSensors = sensors.iterator();
				while(isCandidate && testSensors.hasNext()) {
					
					// Siguiente sensor y posición a comparar
					Sensor testSensor = testSensors.next();
					Vector2 testCandidate = candidate.get();
					
					// Evitamos comparar un sensor consigo mismo
					if(currentSensor != testSensor) {
						
						// Si el sensor está en rango al candidato, entonces no es una posición válida para la baliza
						if(testSensor.isInRange(testCandidate)) {
							
							// Calculate next candidate
							isCandidate = false;
							candidate = currentSensor.nextBorderPos(maxRange);
						}
					}
				}
				
				// Si el sensor sigue siendo candidato tras testear contra todos los sensores, esa es la posición de la baliza
				if(isCandidate) {
					found = true;
					lostBeaconPosition = candidate.get();
				}
	
			} // while
	
		}
		
		//System.out.println("Candidate: " + lostBeaconPosition);
		
		return (long) lostBeaconPosition.getX() * DISTRESS_BEACON_MAX + lostBeaconPosition.getY();

	}
	
}
