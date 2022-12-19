package com.adventofcode.flashk.day19;

import lombok.Getter;

@Getter	
public class Robot {

	private int ore;
	private int clay;
	private int obsidian;
	
	public Robot(int ore, int clay, int obsidian) {
		this.ore = ore;
		this.clay = clay;
		this.obsidian = obsidian;
	}
}
