package com.adventofcode.flashk.day19;

/**
 * Esta clase usa un determinado blueprint para ir generando robots.
 * También lleva el control de cuantos materiales de cada tipo tengo para saber que robot debería construir a continuación.
 *
 */
public class RobotFactory {

	public static final int MAX_TIME_PART_1 = 24;
	public static final int MAX_TIME_PART_2 = 32;
	
	private int maxTime;
	
	private Blueprint blueprint;

	// Materials
	private int maxGeodeAmount = Integer.MIN_VALUE;
	
	// Max robots per type
	private int maxOreRobots = Integer.MIN_VALUE;
	private int maxClayRobots;
	private int maxObsidianRobots = Integer.MIN_VALUE;
	
	// Robots
	private int oreRobots;
	private int clayRobots;
	private int obsidianRobots;
	private int geodeRobots;

	private void initializeFactory(Blueprint blueprint) {
		
		this.blueprint = blueprint;
		
		// Reset resources
		this.maxGeodeAmount = 0;
		
		// Reset robots
		this.oreRobots = 1;
		this.clayRobots = 0;
		this.obsidianRobots = 0;
		this.geodeRobots = 0;
		
		// Determine max ore robots to build
		this.maxOreRobots = Math.max(maxOreRobots, blueprint.getOreRobot().getOre());
		this.maxOreRobots = Math.max(maxOreRobots, blueprint.getClayRobot().getOre());
		this.maxOreRobots = Math.max(maxOreRobots, blueprint.getObsidianRobot().getOre());
		this.maxOreRobots = Math.max(maxOreRobots, blueprint.getGeodeRobot().getOre());
		
		// Determine max clay robots to build
		this.maxClayRobots = blueprint.getObsidianRobot().getClay();

		// Determine max obsidian robots to build
		this.maxObsidianRobots = blueprint.getGeodeRobot().getObsidian();
		
	}

	// Podemos pasar maxGeodes como parámetro para poder aquellos resultados que no sean prometedores
	// Returns the quality level (blueprint id * geode amount)
	public long buildWithQuality(Blueprint blueprint) {
		
		maxTime = MAX_TIME_PART_1;
		
		initializeFactory(blueprint);
		build(0, 0, 0, 0, 0);
		
		return blueprint.getId() * maxGeodeAmount;
	}

	public long build(Blueprint blueprint) {
		
		maxTime = MAX_TIME_PART_2;
		
		initializeFactory(blueprint);
		build(0, 0, 0, 0, 0);
		
		return maxGeodeAmount;
	}
	
	
	private void build(int minutes, int oreAmount, int clayAmount, int obsidianAmount, int geodeAmount) {
		if(minutes >= maxTime) {
			this.maxGeodeAmount = Math.max(this.maxGeodeAmount, geodeAmount);
		} else {
		
			// Only attempt to build geode robots if there is  at least 1 geode robot
			if(obsidianRobots > 0) {
				buildRobot(minutes, oreAmount, clayAmount, obsidianAmount, geodeAmount, blueprint.getGeodeRobot());
			}
			
			// Only attempt to build obsidian robots if no max has reached and there is at least 1 clay robot.
			if(obsidianRobots < maxObsidianRobots && clayRobots > 0) {
				buildRobot(minutes, oreAmount, clayAmount, obsidianAmount, geodeAmount, blueprint.getObsidianRobot());
			}
			
			if(clayRobots < maxClayRobots) {
				buildRobot(minutes, oreAmount, clayAmount, obsidianAmount, geodeAmount, blueprint.getClayRobot());
			}
			
			if(oreRobots < maxOreRobots) {
				buildRobot(minutes, oreAmount, clayAmount, obsidianAmount, geodeAmount, blueprint.getOreRobot());
			}
			
			// Explore not building any more robots
			doNotBuild(minutes, oreAmount, clayAmount, obsidianAmount, geodeAmount);
		}
		
	}

	private void doNotBuild(int minutes, int oreAmount, int clayAmount, int obsidianAmount, int geodeAmount) {
		
		int minutesBeforeEnd = maxTime - minutes;
		
		int newOreAmount = oreAmount + (minutesBeforeEnd * oreRobots);
		int newClayAmount = clayAmount + (minutesBeforeEnd * clayRobots);
		int newObsidianAmount = obsidianAmount + (minutesBeforeEnd * obsidianRobots);
		int newGeodeAmount = geodeAmount + (minutesBeforeEnd * geodeRobots);
		
		build(maxTime, newOreAmount, newClayAmount, newObsidianAmount, newGeodeAmount);
		
	}

	private void buildRobot(int minutes, int oreAmount, int clayAmount, int obsidianAmount, int geodeAmount, Robot robotToBuild) {
		
		int minutesBeforeFinishBuild = 1;

		// Check time required to harvest enough ore
		if(robotToBuild.getOre() > oreAmount) {
			int missingOre = robotToBuild.getOre() - oreAmount;
			int minutesBeforeStartBuild = (int) Math.ceil((double) missingOre / (double) oreRobots);
			minutesBeforeFinishBuild = Math.max(minutesBeforeFinishBuild, minutesBeforeStartBuild+1);
		}
		
		// Check time required to harvest enough clay
		if(robotToBuild.getClay() > clayAmount) {
			int missingClay = robotToBuild.getClay() - clayAmount;
			int minutesBeforeStartBuild = (int) Math.ceil((double) missingClay / (double) clayRobots);
			minutesBeforeFinishBuild = Math.max(minutesBeforeFinishBuild, minutesBeforeStartBuild+1);
		}
		
		// Check time required to harvest enough obsidian
		if(robotToBuild.getObsidian() > obsidianAmount) {
			int missingObsidian = robotToBuild.getObsidian() - obsidianAmount;
			int minutesBeforeStartBuild = (int) Math.ceil((double) missingObsidian / (double) obsidianRobots);
			minutesBeforeFinishBuild = Math.max(minutesBeforeFinishBuild, minutesBeforeStartBuild+1);
		}

		int newMinutes = minutes + minutesBeforeFinishBuild;
		
		// Build robot only if there is enough time to build
		if(newMinutes < maxTime) {
			
			int newOreAmount = updateOreAmount(oreAmount, minutesBeforeFinishBuild, robotToBuild);
			int newClayAmount = updateClayAmount(clayAmount, minutesBeforeFinishBuild, robotToBuild);
			int newObsidianAmount = updateObsidianAmount(obsidianAmount, minutesBeforeFinishBuild, robotToBuild);
			int newGeodeAmount = updateGeodeAmount(geodeAmount, minutesBeforeFinishBuild);
			
			increaseRobot(robotToBuild);
			build(newMinutes, newOreAmount, newClayAmount, newObsidianAmount, newGeodeAmount);
			decreaseRobot(robotToBuild);
		}
	}
	
	private void increaseRobot(Robot robotToBuild) {
		
		switch(robotToBuild.getType()) {
			case ORE: oreRobots++; break;
			case CLAY: clayRobots++; break;
			case OBSIDIAN: obsidianRobots++; break;
			case GEODE: geodeRobots++; break;
			default: throw new UnsupportedOperationException("There is no robot for type = "+robotToBuild.getType());
		}
		
	}

	private void decreaseRobot(Robot robotToBuild) {
		
		switch(robotToBuild.getType()) {
			case ORE: oreRobots--; break;
			case CLAY: clayRobots--; break;
			case OBSIDIAN: obsidianRobots--; break;
			case GEODE: geodeRobots--; break;
			default: throw new UnsupportedOperationException("There is no robot for type = "+robotToBuild.getType());
		}
		
	}
	
	/**
	 * Updates the ore amount after x minutes minus the ore cost of the robot to build.
	 * @param currentOre
	 * @param robotToBuild
	 * @param minutes
	 * @return
	 */
	private int updateOreAmount(int currentOre, int minutes, Robot robotToBuild) {
		return currentOre + (minutes * oreRobots) - robotToBuild.getOre();
	}
	
	private int updateClayAmount(int currentClay, int minutes, Robot robotToBuild) {
		return currentClay + (minutes * clayRobots) - robotToBuild.getClay();
	}
	
	private int updateObsidianAmount(int currentObsidian, int minutes, Robot robotToBuild) {
		return currentObsidian + (minutes * obsidianRobots) - robotToBuild.getObsidian();
	}
	
	public int updateGeodeAmount(int currentGeode, int minutes) {
		return currentGeode + (minutes * geodeRobots);
	}
}
