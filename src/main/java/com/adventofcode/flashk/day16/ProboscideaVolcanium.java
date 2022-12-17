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
	
	//private int minutes = 0;
	//private long releasedPressure = 0;
	//private long currentFlow = 0;
	private int maxReleasedPressure = Integer.MIN_VALUE;
	private int openedValves = 0;
	private int openableValves = 0;
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
		long result = 0;
		
		// Release maximum pressure
		
		// Modelo de datos
		
		// Tenemos válvulas. De cada válvula necesitamos saber:
		// - Su nombre.
		// - Su flujo.
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
		
		// Algoritmo
		
		// Empezamos en origin.
		// Repetimos los siguientes pasos hasta (que todas las válvulas estén abiertas) o hasta que el tiempo llegue a 30 minutos:
		// 1. Elegimos una válvula candidata.
		// 2. Vamos a ella, sumamos 1 al contador de tiempo, sumamos a releasedPressure += currentFlow
		// 3a. Si el flujo de la válvula es 0:
		//	3a.1 Repetimos 1 desde dicha válvula.
		// 3b. Si no:
		//	3b.1 Sumamos 1 al contador de tiempo; releasedPressure += currentFlow
		// 	3b.2 Abrimos válvula: currentFlow += valve.flow
		//	3b.3 Repetimos 1 desde dicha válvula.
		// 
		
		// Aparentemente es un algoritmo de backtracking
		
		// Caso base - Se han alcanzado los 30 minutos
		// - Comparamos la solución con la mejor solución hasta el momento y actualizamos
		// - ¿Devuelve algo el caso base?

		/*
		 * Evaluación del ejemplo:
		 * A es visitado 2 veces. A tiene 3 vecinos.
		 * B es visitado 1 veces. B tiene 2 vecinos.
		 * C es visitado 2 veces. C tiene 2 vecinos.
		 * D es visitado 3 veces. D tiene 3 vecinos.
		 * E es visitado 2 veces. E tiene 2 vecinos.
		 * F es visitado 2 veces. F tiene 2 vecinos.
		 * G es visitado 2 veces. G tiene 2 vecinos.
		 * H es visitado 1 veces. H tiene 1 vecinos.
		 * I es visitado 2 veces. I tiene 2 vecinos.
		 * J es visitado 1 veces. J tiene 1 vecinos.
		 */
		
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
		

		
		
		maxPressure(origin, null, 0, 0, 0);
		
		return maxReleasedPressure;
	}
	
	private void maxPressure(Valve currentValve, String previousValveName, int totalMinutes, int totalReleasedPressure, int totalFlow) {
		//System.out.println(currentValve.getName() + " - minutes: "+totalMinutes);
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
			maxPressure(currentValve, previousValveName, MAX_TIME, estimatedTotalReleasedPressure, totalFlow);
			
		} else {

			// Recursive case 2 - Open an openable valve that has flow greater than 0
			
			// Puesto que lleva 1 minuto abrir la válvula, para la siguiente llamda recursiva:
			// - Incrementamos el minuto en 1.
			// - Incrementamos la presión liberada con el totalFlow adicional generado en ese minuto.
			// - Incrementamos el flujo total generado con el flujo adicional de la válvula
			
			/// Finalmente, la válvula pasa a estar abierta y actualizamos el contador de válvulas abiertas.
			
			// Open valve
			if (!currentValve.isOpen() && currentValve.getFlow() > 0) {
				currentValve.setOpen(true);
				openedValves++;
				
				// You spend 1 minute opening the valve, update total minutes and total released pressure before calling recursive method
				maxPressure(currentValve, previousValveName, totalMinutes + 1, totalReleasedPressure + totalFlow, totalFlow + currentValve.getFlow());
				
				// Backtrack - Close valve
				currentValve.setOpen(false);
				openedValves--;
			}
			
			// Recursive case 3
			// Después de explorar la opción de abrir la válvula, habría que explorar la opción de ignorarla y moverse a la siguiente.
			visitNeighbours(currentValve, previousValveName, totalMinutes, totalReleasedPressure, totalFlow);
		}

	}

	private void visitNeighbours(Valve currentValve, String previousValveName, int totalMinutes,
			int totalReleasedPressure, int totalFlow) {
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
		List<String> leadingValves = currentValve.getLeadingValves(previousValveName);
		
		for(String valveName : leadingValves) {

			// Obtain next valve to evaluate
			//Valve nextValve = valves.stream().filter(v -> valveName.equals(v.getName())).findFirst().get();
			Valve nextValve = valvesByName.get(valveName);
			
			// Move to next valve
			currentValve.visitNeighbour(valveName);
			maxPressure(nextValve, currentValve.getName(), totalMinutes + 1, totalReleasedPressure + totalFlow, totalFlow);
			currentValve.unvisitNeighbour(valveName);
			
			
		}
	}
}
