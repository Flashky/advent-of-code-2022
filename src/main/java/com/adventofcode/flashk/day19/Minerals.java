package com.adventofcode.flashk.day19;

import java.util.List;
import java.util.stream.Collectors;

public class Minerals {

	private List<Blueprint> blueprints;
	private RobotFactory robotFactory;
	
	private long maxGeodes = Long.MIN_VALUE;
	
	public Minerals(List<String> inputs) {
		
		blueprints = inputs.stream().map(Blueprint::new).collect(Collectors.toList());
		System.out.println("test");
	}
	
	public long solveA() {
		long result = 0;
		
		// TODO Algoritmo:
		/*
			Para cada blueprint:
			Hasta que ya no queden minutos restantes:
				Calcular creación de robots: 
					Parece que hay una prioridad:
					1. Intenta crear un robot de geoda.
					2. Intenta crear un robot de obsidiana.
					3. Intenta crear un robot de clay.
					4. Intenta crear un robot de ore.
				
				Calcular creación de geodas
				
		 */
		return result;
	}
}
