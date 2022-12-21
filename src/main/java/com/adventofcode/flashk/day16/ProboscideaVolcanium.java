package com.adventofcode.flashk.day16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.adventofcode.flashk.day12.Node;

public class ProboscideaVolcanium {

	private static final String VALVE_REGEX = "Valve ([A-Z]*) has flow rate=(\\d*)";
	private static final Pattern VALVE_PATTERN = Pattern.compile(VALVE_REGEX);
	private static final String TUNNELS_REGEX = "lead to valves ([,A-Z ]*)|leads to valve ([A-Z]*)";
	private static final Pattern TUNNELS_PATTERN = Pattern.compile(TUNNELS_REGEX);
	private static final String SEPARATOR = ", ";
	
	private static final int MAX_TIME = 30;
	
	// Current valve to calculate dijktra from
	private Valve currentValve;
	private List<Valve> openableValves = new ArrayList<>();
	
	private int maxReleasedPressure = Integer.MIN_VALUE;
	private int openedValves = 0;
	
	public ProboscideaVolcanium(List<String> inputs) {
		
		Map<String, Valve> valves = new HashMap<>();
		
		for(String input : inputs) {
			
			// Create valve (graph vertex)
			Valve currentValve = createValve(valves, input);
			
			// Create tunnels (graph edges)
			createTunnel(valves, input, currentValve);
			
		}
		
		
		// Start with AA valve
		currentValve = valves.get("AA");
	}

	public long solveA() {
		
		// Algoritmo
		// Hay que mezclar dijkstra y backtracking
		
		// 0. Seteamos currentReleasedPressure = 0
		// 1. Calculamos dijkstra desde currentValue al resto de válvulas
		// 2. Elegimos una de las válvulas que queden por abrir y vamos a ella por el camino mínimo.
		// 	2.1. Actualizamos currentValve a la válvula en la que estamos.
		//	2.2. Abrimos válvula y la quitamos del listado de válvulas pendientes de abrir.
		// 	2.2. Actualizamos currentReleasedPresure calculando fórmula: 
		//			currentReleasedPresure = currentReleasedPresure + ((tiempo restante - tiempo en llegar - 1)*currentValve.flow) => en la primera iteración 30-t-1 => 29-t
		// 3. Si quedan válvulas por abrir, reseteamos los valores de totalMinutes y visitado de cada Valve y repetimos desde paso 1.
		// 4. Cuando no queden válvulas por abrir, actualizamos solución óptima: maxReleasedPressure = max(currentReleasedPressure,maxReleasedPressure)
		// 5. Aplicamos backtracking. Seleccionamos una válvula distinta para abrir.
		
		// Algoritmo de dijkstra: está funcionando ok
		// calcula el valor de totalMinutes para llegar a cada nodo
		currentValve.setTotalMinutes(0);
		
		PriorityQueue<Valve> queue = new PriorityQueue<>();
		queue.add(currentValve);
		
		while(!queue.isEmpty()) {
			
			Valve minValve = queue.poll();
			minValve.setVisited(true);

			Set<Valve> adjacentValves = minValve.getNeighbourValves();
			
			for(Valve adjacentValve : adjacentValves) {
				if(!adjacentValve.isVisited()) {
					
					// Cost of moving to the adjacent node
					int minutes = adjacentValve.getMinutes();
					
					// Cost of moving to the adjacent node + total cost to reach to this node
					int estimatedMinutes = minValve.getTotalMinutes() + minutes;
					
					if(adjacentValve.getTotalMinutes() > estimatedMinutes) {
						adjacentValve.setTotalMinutes(estimatedMinutes);
						queue.add(adjacentValve);
					}
				}
			}
		}
		
		
		return 0;
	}
	

	private Valve createValve(Map<String, Valve> valves, String input) {
		Matcher valveMatcher = VALVE_PATTERN.matcher(input);
		valveMatcher.find();
		
		String name = valveMatcher.group(1);
		int flow = Integer.parseInt(valveMatcher.group(2));
		
		Valve currentValve = valves.getOrDefault(name, new Valve(name));
		if(flow > 0) {
			currentValve.setFlow(flow);
			openableValves.add(currentValve);
		}
		valves.put(name, currentValve);
		return currentValve;
	}
	
	private void createTunnel(Map<String, Valve> valves, String input, Valve currentValve) {
		Matcher tunnelMatcher = TUNNELS_PATTERN.matcher(input);
		tunnelMatcher.find();
		
		String tunnels = tunnelMatcher.group(1);
		String tunnel = tunnelMatcher.group(2);
		
		String[] neighbourNames = null;
		
		if(!StringUtils.isBlank(tunnels)) {
			neighbourNames = tunnelMatcher.group(1).split(SEPARATOR);
		} else if(!StringUtils.isBlank(tunnel)){
			neighbourNames = new String[1];
			neighbourNames[0] = tunnel;
		}
		
		for(String neighbourName : neighbourNames) {
			
			// Search or create neighbour valve
			Valve neighbourValve = valves.getOrDefault(neighbourName, new Valve(neighbourName));
			valves.put(neighbourName, neighbourValve);
			
			// Add edge between both
			currentValve.addNeighbour(neighbourValve);
			neighbourValve.addNeighbour(currentValve);
			
		}
	}
	

}
