package com.adventofcode.flashk.day16;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProboscideaVolcanium {

	private static final int MAX_TIME = 30;
	
	private List<Valve> valves;
	private final Map<String, Valve> valvesByName = new HashMap<>();
	
	private Valve origin;

	
	private int maxReleasedPressure = Integer.MIN_VALUE;
	private int openedValves = 0;
	private int openableValves = 0;
	
	
	// Release maximum pressure
	
	// Modelo de datos
	
	// Tenemos válvulas. De cada válvula necesitamos saber:
	// - Su nombre (Por ejemplo: AA)
	// - Su flujo (flow rate)
	// - Si está abierta o no.
	
	
	// Datos adicionales para resolver el problema:
	
	// - Necesitamos un contador de tiempo: minutes
	// - Hay que llevar un contador de cuanta presión llevamos liberada: releasedPressure
	// - Por otro lado, hay que llevar un contador de cuanto es el flujo actual: currentFlow;
	// - Cada minuto adicional se sumará a releasedPressure = releasedPressure + currentFlow
	// - Problema de optimización. 
	// 	- Hay que llevar un contador de cuál es el camino que ha permitido llevar una mayor releasedPressure: maxReleasedPressure
	//	- Inicialmente maxReleasedPressure = -infinito o 0.
	//	- Se actualiza maxReleasedPressure cuando han transcurrido los 30 minutos
	// 
	// Al llegar a 30 minutos, habría que retroceder en el problema y ver si hay una solución más óptima
	
	
	/* Ruta completa del ejemplo:

		AA -> DD (open)
		DD -> CC
		CC -> BB (open)
		BB -> AA 
		AA -> II
		II -> JJ (open)
		JJ -> II
		II -> AA
		AA -> DD (REPEATED STEP) -> Puedes caminar dos veces por la misma tubería visitada.
		DD -> EE
		EE -> FF
		FF -> GG
		GG -> HH (open)
		HH -> GG
		GG -> FF
		FF -> EE (open)
		EE -> DD 
		DD -> CC (open) (REPETEAD STEP) 
		Stay in CC from minutes 24 to 30: 81*6 = 486 presión adicional en esos minutos
		
	 */
	
	
	/*
		Premisas falsas:
		- Un nodo es visitado como mucho, tantas veces como vecinos tenga.

		Premisas verdaderas:
		- Un nodo puede visitarse múltiples veces, y es posible realizar un mismo camino varias veces si es una solución óptima.
			- En el ejemplo, se repiten dos veces el paso AA -> DD y el paso DD -> CC
			
	 */
	
	
	// Algoritmo v2
	
	// Inicialmente lo había planteado como un algoritmo de backtracking en elq ue la profundidad la marca el tiempo restante: 
	// 29 minutos, 28, 27...
	// Sin embargo, esto tiene el problema de muchas ramas muertas que se meten en bucles.
	
	// La nueva idea mejorada se basa en backtracking también, pero con una topología diferente: permutaciones
	// Si hay 6 válvulas a abrir, podemos elegir cualquiera de ellas.
	// Luego, buscaríamos una de las 5 restantes.
	// Una de las 4...
	// Y así hasta que están todas abiertas.
	// Esto es 6! un total de 720 permutaciones diferentes de abrir las válvulas.
	//
	// Algoritmo
	// - En primer lugar, en una lista aparte, guardaremos únicamente las válvulas que se pueden abrir.
	// - Desde el nodo inicial hay que aplicar Dijkstra para saber cuál es el camino más corto a todos los nodos.
	// - En la aplicación de Dijkstra se calcularía un peso en cada nodo que indicaría cuál es la presión total liberada en los minutos restantes tras abrir cada nodo.
	// - Elegimos cualquiera de los 6 nodos posibles, lo marcamos como parte de la solución (sumamos al total) y lo ponemos como nuevo origen.
	// - Repetimos  Dijkstra. Se actualizarían los pesos desde la nueva posición.
	// - Elegimos cualquiera de los 5 nodos posibles, lo marcamos como parte de la solución  y lo ponemos como nuevo origen.
	// - Repetimos Dijkstra, se actualizarán los pesos desde la nueva posición.
	// - Elegimos cualquiera de los 4 nodos posibles...
	
	// Así hasta que no quedan nodos posibles (nodo hoja).
	// Cogemos el tiempo restante, cual sería el flujo total posible en esa solución y comprobamos si es una solución más óptima.
	// Hacemos backtracking desde aquí, e investigamos el resto de posibles soluciones. Si Dijkstra ya está calculado, no hace falta repetirlo.
	
	
	
	
	public ProboscideaVolcanium(List<String> inputs) {
		valves = inputs.stream().map(Valve::new).collect(Collectors.toList());
		
		for(Valve valve : valves) {
			valvesByName.put(valve.getName(), valve);
			if(valve.getFlow() > 0) {
				openableValves++;
			}
		}
		origin = valves.get(0);
	}
	
	public long solveA() {
		maxPressure(origin, 0, 0, 0);
		return maxReleasedPressure;
	}
	
	private void maxPressure(Valve currentValve, int totalMinutes, int totalReleasedPressure, int totalFlow) {

		if(totalMinutes >= MAX_TIME) {
			// Case base - Max time reached	

			// Check if solution is a better optimal solution
			if(totalReleasedPressure > maxReleasedPressure) {
				maxReleasedPressure = totalReleasedPressure;
			}
			
		} else if (openedValves == openableValves) {

			// Recursive case 1 - All valves are opened
			
			// Calculate total pressure for remaining minutes
			// Leads directly to case base
			
			int remainingMinutes = MAX_TIME - totalMinutes;
			int estimatedTotalReleasedPressure = totalReleasedPressure + (remainingMinutes * totalFlow);
			maxPressure(currentValve, MAX_TIME, estimatedTotalReleasedPressure, totalFlow);
			
		} else {

			// Recursive case 2 - Open an openable valve that has flow greater than 0

			
			/// Finalmente, la válvula pasa a estar abierta y actualizamos el contador de válvulas abiertas.
			
			// Open valve
			if (currentValve.isOpenable()) {
				currentValve.setOpen(true);
				openedValves++;
				
				// You spend 1 minute opening the valve, update total minutes and total released pressure before calling recursive method
				maxPressure(currentValve, totalMinutes + 1, totalReleasedPressure + totalFlow, totalFlow + currentValve.getFlow());
				
				// Backtrack - Close valve
				currentValve.setOpen(false);
				openedValves--;
			} else {
			
				// Recursive case 3
				// Después de explorar la opción de abrir la válvula, habría que explorar la opción de ignorarla y moverse a la siguiente.
				visitNeighbours(currentValve, totalMinutes, totalReleasedPressure, totalFlow);
			}
		}

	}

	private void visitNeighbours(Valve currentValve, int totalMinutes, int totalReleasedPressure, int totalFlow) {
		// Recursive case 3 - Move to other valve
		
		// Premisas:
		// - Podemos repetir nodos ya visitados previamente.
		
		// Problemas:
		// - Stack overflow. Es debido a la existencia de ciclos
		
		// Puesto que lleva 1 minuto moverse a otra válvula, para la siguiente llamada recursiva:
		// - Incrementamos el minuto en 1.
		// - Incrementamos la presión liberada con el totalFlow adicional generado en ese minuto.
		// - El flujo total permanece igual, no hemos abierto nada.

		//List<String> leadingValves = currentValve.getUnvisitedNeighbours();
		List<String> leadingValves = currentValve.getLeadingValves();
		
		for(String valveName : leadingValves) {

			// Obtain next valve to evaluate
			//Valve nextValve = valves.stream().filter(v -> valveName.equals(v.getName())).findFirst().get();
			Valve nextValve = valvesByName.get(valveName);
			
			// Move to next valve
			maxPressure(nextValve, totalMinutes + 1, totalReleasedPressure + totalFlow, totalFlow);
			
		}
	}
}
