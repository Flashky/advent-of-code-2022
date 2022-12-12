package com.adventofcode.flashk.day12;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Node implements Comparable<Node> {

	private final int row;
	private final int col;
	private char height;
	
	private int totalSteps = Integer.MAX_VALUE;
	private int step = 1;
	
	private boolean visited = false;
	
	public Node(int row, int col, char height) {
		this.row = row;
		this.col = col;
		this.height = height;
	}
	
	@Override
	public int compareTo(Node other) {
		return Integer.compare(totalSteps, other.totalSteps);
	}

	public void reset() {
		totalSteps = Integer.MAX_VALUE;
		visited = false;
	}
}
