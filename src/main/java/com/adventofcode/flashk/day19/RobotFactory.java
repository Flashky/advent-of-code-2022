package com.adventofcode.flashk.day19;

import lombok.Setter;

/**
 * Esta clase usa un determinado blueprint para ir generando robots.
 * También lleva el control de cuantos materiales de cada tipo tengo para saber que robot debería construir a continuación.
 *
 */
public class RobotFactory {

	private Blueprint blueprint;

	public RobotFactory(Blueprint blueprint) {
		this.blueprint = blueprint;
	}
	
	// Materials
	private int oreAmount;
	private int clayAmount;
	private int obsidianAmount;
	private int geodeAmount;
	
	// Robots
	private int oreRobots = 1; // Always start with 1 ore robot
	private int clayRobots;
	private int obsidianRobots;
	private int geodeRobots;
	
	
	// Podemos pasar maxGeodes como parámetro para poder aquellos resultados que no sean prometedores
	// Returns the quality level (blueprint id * geode amount)
	public int work(int minutes, int maxGeodes) {
		
		// TODO algoritmo de generación de robots
		
		// Premisas:
		// - La fábrica solo puede construir un tipo de robot por minuto.
		
		// Ideas:
		// Como la fábrica solo puede construir un tipo de robot por minuto, los diferentes robots tienen mas o menos prioridad:
		// 1. Geode robot
		// 2. Obsidian robot
		// 3. Clay robot
		// 4. Ore robot
		
		// Dudas:
		// - El ejemplo no llega a construir un ore robot jamás. Si tienes 2 ore puedes elegir entre construir 1 ore robot o 1 clay robot.
		
		// 1. Construimos un robot si podemos construirlo
		// Al construirlo, restamos los materiales
		// A continuación, los robots aumentan el número de materiales
		
		return blueprint.getId() * geodeAmount;
	}
}
