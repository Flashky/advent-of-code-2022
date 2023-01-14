package com.adventofcode.flashk.day19;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;

@Getter
public class Blueprint {

	private static final String BLUEPRINT_REGEX = "Blueprint (\\d*):";
	private static final String ORE_ROBOT_REGEX = "Each ore robot costs (\\d*) ore.";
	private static final String CLAY_ROBOT_REGEX = "Each clay robot costs (\\d*) ore.";
	private static final String OBSIDIAN_ROBOT_REGEX = "Each obsidian robot costs (\\d*) ore and (\\d*) clay.";
	private static final String GEODE_ROBOT_REGEX = "Each geode robot costs (\\d*) ore and (\\d*) obsidian.";

	private int id;
	private Robot oreRobot;
	private Robot clayRobot;
	private Robot obsidianRobot;
	private Robot geodeRobot;
	
	public Blueprint(String input) {
		
		// Blueprint id
		Pattern pattern = Pattern.compile(BLUEPRINT_REGEX);
		
		Matcher matcher = pattern.matcher(input);
		matcher.find();
		
		id = Integer.parseInt(matcher.group(1));
		
		// Ore robot costs
		pattern = Pattern.compile(ORE_ROBOT_REGEX);
	
		matcher = pattern.matcher(input);
		matcher.find();
		
		oreRobot = new Robot(Integer.parseInt(matcher.group(1)),0,0, RobotType.ORE);
		
		// Clay robot
		pattern = Pattern.compile(CLAY_ROBOT_REGEX);
		
		matcher = pattern.matcher(input);
		matcher.find();
		
		clayRobot = new Robot(Integer.parseInt(matcher.group(1)),0,0, RobotType.CLAY);
		
		// Obsidian robot
		pattern = Pattern.compile(OBSIDIAN_ROBOT_REGEX);
		
		matcher = pattern.matcher(input);
		matcher.find();
		
		obsidianRobot = new Robot(Integer.parseInt(matcher.group(1)),Integer.parseInt(matcher.group(2)),0, RobotType.OBSIDIAN);
		
		// Geode robot
		pattern = Pattern.compile(GEODE_ROBOT_REGEX);
		
		matcher = pattern.matcher(input);
		matcher.find();
		
		geodeRobot = new Robot(Integer.parseInt(matcher.group(1)),0,Integer.parseInt(matcher.group(2)), RobotType.GEODE);
	}
	
}
