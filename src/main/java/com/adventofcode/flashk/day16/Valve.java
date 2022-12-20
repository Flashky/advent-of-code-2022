package com.adventofcode.flashk.day16;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Valve {

	private static final String VALVE_REGEX = "Valve ([A-Z]*) has flow rate=(\\d*)";
	private static final Pattern VALVE_PATTERN = Pattern.compile(VALVE_REGEX);
	private static final String SEPARATOR = "; ";
	
	private String name;
	private int flow;
	
	@Setter
	private boolean open;
	private int visitedNeighbours = 0;
	

	private List<String> leadingValves = new ArrayList<>();
	
	public Valve(String input) {
		
		String[] inputSplit = input.split(SEPARATOR);
		
		initializeValve(inputSplit[0]);
		initializeLeadingCaves(inputSplit[1]);
	}

	
	public boolean hasUnvisitedNeighbours() {
		return visitedNeighbours < leadingValves.size();
	}
	
	
	private void initializeValve(String input) {
		Matcher matcher = VALVE_PATTERN.matcher(input);
		matcher.find();
		
		name = matcher.group(1);
		flow = Integer.parseInt(matcher.group(2));
	}
	
	public boolean isOpenable() {
		return !open && flow > 0;
	}
	
	private void initializeLeadingCaves(String input) {
		
		
		
		if(input.startsWith("tunnel leads to valve ")) {
			input = input.substring(22, input.length());
		} else if(input.startsWith("tunnels lead to valves ")) {
			input = input.substring(23, input.length());
		}
		
		String[] split = input.split(", ");
		
		for(String valve : split) {
			leadingValves.add(valve);
		}

	}

	
	
}
