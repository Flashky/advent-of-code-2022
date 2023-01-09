package com.adventofcode.flashk.day16;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Valve implements Comparable<Valve> {

	private static final int OPEN_TIME = 1;

	private String name;
	private int flow;
	private boolean open = false;
	private boolean visited = false;
	private Set<Valve> neighbourValves = new HashSet<>();
	
	// Dijkstra attributes
	
	// Total minutes to reach this valve from origin valve
	private int totalMinutes = Integer.MAX_VALUE;
	
	// Minutes to reach to any neighbour valve
	private int minutes = 1;
	
	public Valve(String name) {
		this.name = name;
	}
	
	public void addNeighbour(Valve valve) {
		neighbourValves.add(valve);
	}
	
	public boolean isOpenable() {
		return !open && flow > 0;
	}
	
	public int getTimeToOpen() {
		return totalMinutes + OPEN_TIME;
	}
	
	public void reset() {
		totalMinutes = Integer.MAX_VALUE;
		visited = false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Valve other = (Valve) obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Valve [name=" + name + "]";
	}

	@Override
	public int compareTo(Valve other) {
		return Integer.compare(totalMinutes, other.totalMinutes);
	}

	
}
