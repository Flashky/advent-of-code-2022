package com.adventofcode.flashk.day16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.util.CombinatoricsUtils;

public class ProboscideaVolcanium {

	private static final String VALVE_REGEX = "Valve ([A-Z]*) has flow rate=(\\d*)";
	private static final Pattern VALVE_PATTERN = Pattern.compile(VALVE_REGEX);
	private static final String TUNNELS_REGEX = "lead to valves ([,A-Z ]*)|leads to valve ([A-Z]*)";
	private static final Pattern TUNNELS_PATTERN = Pattern.compile(TUNNELS_REGEX);
	private static final String SEPARATOR = ", ";
	
	public static final int MAX_TIME_PART_1 = 30;
	public static final int MAX_TIME_PART_2 = 26;
	
	// Current valve to calculate dijktra from
	private Valve startingValve;
	private int maxTime = MAX_TIME_PART_1;
	
	private Set<Valve> allValves = new HashSet<>();
	private Set<Valve> openableValves = new HashSet<>();
	private Map<Integer,Valve> openableValvesByNumber = new HashMap<>();
	
	private long maxReleasedPressure = 0;
	
	public ProboscideaVolcanium(List<String> inputs) {
		
		Map<String, Valve> valves = new HashMap<>();
		
		for(String input : inputs) {
			
			// Create valve (graph vertex)
			Valve currentValve = createValve(valves, input);
			
			// Create tunnels (graph edges)
			createTunnel(valves, input, currentValve);
			
		}
		
		
		// Start with AA valve
		startingValve = valves.get("AA");
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
		
		maxReleasedPressure = 0;
		
		Set<Valve> candidates = getNextCandidates(maxTime);
		for(Valve nextValve : candidates) {
			dijkstra(startingValve);
			releasePressure(nextValve, maxTime, 0);
		}
		
		
		return maxReleasedPressure;
	
	}
	
	public long solveB() {
		
		maxTime = MAX_TIME_PART_2;

		// Generate all possible combinations
		Map<Integer, List<Set<Valve>>> valveCombinationsByGroupSize = new HashMap<>();
		for(int k = 0; k <= openableValves.size(); k++) {
			Iterator<int[]> combinations = CombinatoricsUtils.combinationsIterator(openableValves.size(), k);
			
			List<Set<Valve>> result = map(combinations);
			valveCombinationsByGroupSize.put(k,result);

		}
		
		// Hay tantas combinaciones opuestas como partition: p = (n/2) + 1
		// Para 6:
		// p = (6/2) + 1 = 4
		
		int totalOpenableValves = openableValves.size();
		int partitions = (totalOpenableValves / 2) + 1;
		long maxCombinedReleasedPressure = 0;
		
		// Generate all possible partition of valves
		for(int partition = 0; partition < partitions; partition++) {
			
			int opposedPartition = totalOpenableValves - partition;
			List<Set<Valve>> elephantOptions = valveCombinationsByGroupSize.get(partition);
			List<Set<Valve>> humanOptions = valveCombinationsByGroupSize.get(opposedPartition);
			
			// Pick opposing sets:
			Set<Valve> elephantSelection;
			Set<Valve> humanSelection;
	
			int elephantIndex = 0;
			int humanIndex = humanOptions.size()-1;
			
			while(humanIndex >= 0) {

				elephantSelection = elephantOptions.get(elephantIndex++);
				humanSelection = humanOptions.get(humanIndex--);
				
				// Solve for elephant
				resetValves();
				openableValves = elephantSelection;
				long pressureElephant = solveA();
				
				// Solve for human
				resetValves();
				openableValves = humanSelection;
				long pressureHuman = solveA();
				long totalPressure = pressureElephant + pressureHuman;
				maxCombinedReleasedPressure = Math.max(maxCombinedReleasedPressure, totalPressure);
				
			}
			
		}
		
		
		return maxCombinedReleasedPressure;
	}


	private List<Set<Valve>> map(Iterator<int[]> combinations) {
		
		List<Set<Valve>> valvesCombinationList = new ArrayList<>();
		while(combinations.hasNext()) {
			Set<Valve> valveCombination = map(combinations.next());
			valvesCombinationList.add(valveCombination);
		}
		return valvesCombinationList;
	}
	
	private Set<Valve> map(int[] combination) {
		return Arrays.stream(combination).mapToObj(value -> openableValvesByNumber.get(value)).collect(Collectors.toSet());
	}

	private void releasePressure(Valve currentValve, int remainingTime, long totalPressure) {
		// PRE 1: currentValve no está abierta (hemos prefiltrado)
		// PRE 2: La válvula actual da tiempo a abrirla y a producir presión (hemos prefiltrado)
		
		currentValve.setOpen(true);
		int remainingTimeAfterOpen = remainingTime - currentValve.getTimeToOpen();
		long updatedPressure = totalPressure + currentValve.getFlow() * remainingTimeAfterOpen;
		
		Set<Valve> candidates = getNextCandidates(remainingTimeAfterOpen);
		
		if(candidates.isEmpty()) {
			maxReleasedPressure = Math.max(updatedPressure, maxReleasedPressure);
		} else {
			for(Valve nextValve : candidates) {
				dijkstra(currentValve);
				releasePressure(nextValve, remainingTimeAfterOpen, updatedPressure);
			}
		}
		
		currentValve.setOpen(false);
	}
	
	public Set<Valve> getNextCandidates(int remainingTime) {
		
		// Search for open valves that we could reach in time to open
		return openableValves.stream()
								.filter(v -> !v.isOpen())
								.filter(v -> v.getTimeToOpen() < remainingTime)
								.collect(Collectors.toSet());
	}
	
	private void dijkstra(Valve currentValve) {
		
		resetValves();
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
	}
	
	private void resetValves() {
		allValves.forEach(Valve::reset);
	}
	
	private Valve createValve(Map<String, Valve> valves, String input) {
		Matcher valveMatcher = VALVE_PATTERN.matcher(input);
		valveMatcher.find();
		
		String name = valveMatcher.group(1);
		int flow = Integer.parseInt(valveMatcher.group(2));
		
		Valve currentValve = valves.getOrDefault(name, new Valve(name));
		if(flow > 0) {
			currentValve.setFlow(flow);
			openableValvesByNumber.put(openableValves.size(), currentValve);
			openableValves.add(currentValve);
		}
		
		valves.put(name, currentValve);
		allValves.add(currentValve);
		
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
