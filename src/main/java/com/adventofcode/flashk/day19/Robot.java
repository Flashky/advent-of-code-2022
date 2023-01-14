package com.adventofcode.flashk.day19;

import lombok.Getter;

@Getter	
public class Robot {

	private int ore;
	private int clay;
	private int obsidian;
	private RobotType type;
	
	public Robot(int ore, int clay, int obsidian, RobotType type) {
		this.ore = ore;
		this.clay = clay;
		this.obsidian = obsidian;
		this.type = type;
	}

}
