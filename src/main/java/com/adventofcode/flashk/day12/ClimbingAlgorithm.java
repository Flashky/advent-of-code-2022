package com.adventofcode.flashk.day12;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

public class ClimbingAlgorithm {

	// Uses Dijkstra algorithm to solve the shortest path possible.
	
	private final static char LOWEST_HEIGHT = 'a';
	private final static char HIGHEST_HEIGHT = 'z';
	private final static char START = 'S';
	private final static char END = 'E';
	
	private Node[][] heightMap;
	private int rows;
	private int cols;

	private Node origin;
	private Node destination;
	
	private List<Node> possibleStarts = new ArrayList<>();
	
	public ClimbingAlgorithm(List<String> inputs) {
	
		rows = inputs.size();
		cols = inputs.get(0).length();
		heightMap = new Node[rows][cols];
		
		int rowIndex = 0;
		int colIndex = 0;
		
		for(String input : inputs) {
			
			colIndex = 0;
			
			for(char height : input.toCharArray()) {
				
				heightMap[rowIndex][colIndex] = new Node(rowIndex, colIndex, height);
				
				if(height == START) {
					origin = heightMap[rowIndex][colIndex];
					origin.setHeight(LOWEST_HEIGHT);
					possibleStarts.add(heightMap[rowIndex][colIndex]);
				} else if (height == END) {
					destination = heightMap[rowIndex][colIndex];
					destination.setHeight(HIGHEST_HEIGHT);
				} else if(height == LOWEST_HEIGHT) {
					possibleStarts.add(heightMap[rowIndex][colIndex]);
				}
				
				colIndex++;
				
			}
			
			rowIndex++;
		}
		
	}
	
	/*

		v..v<<<<
		>v.vv<<^
		.>vv>E^^
		..v>>>^^
		..>>>>>^
		
		v: 8
		>: 11
		<: 6
		^: 6
		
		expected: 31 (no cuenta el propio nodo E)

	 */
	
	public long solveA() {
		
		// Uses Dijkstra algorithm to solve the shortest path possible.
		
		origin.setTotalSteps(1);
		PriorityQueue<Node> queue = new PriorityQueue<>();
		queue.add(origin);
		
		while(!queue.isEmpty()) {
			
			Node minNode = queue.poll();
			minNode.setVisited(true);

			Set<Node> adjacentNodes = getAdjacentNodesFromArray(minNode);
			
			for(Node adjacentNode : adjacentNodes) {
				if(!adjacentNode.isVisited()) {
					
					// Cost of moving to the adjacent node
					int step = adjacentNode.getStep();
					
					// Cost of moving to the adjacent node + total cost to reach to this node
					int estimatedSteps = minNode.getTotalSteps() + step;
					
					if(adjacentNode.getTotalSteps() > estimatedSteps) {
						adjacentNode.setTotalSteps(estimatedSteps);
						adjacentNode.setParent(minNode);
						queue.add(adjacentNode);
					}
				}
			}
		}
		
		return destination.getTotalSteps() - 1; // final node does not count
	}
	
	public long solveB() {
		
		List<Long> results = new ArrayList<>();
		
		// Apply Dijkstra algorithm from all possible starts
		for(Node possibleStart : possibleStarts) {
			origin = possibleStart;
			results.add(solveA());
			reset();
		}
		
		return results.stream().sorted().findFirst().get();
		
	}
	
	private void reset() {
		for(int rowIndex = 0; rowIndex < rows; rowIndex++) {
			for(int colIndex = 0; colIndex < cols; colIndex++) {
				heightMap[rowIndex][colIndex].reset();
			}
		}
	}
	
	private Set<Node> getAdjacentNodesFromArray(Node node) {
		
		// Seleccionar únicamente nodos tal que la diferencia de altura sea 1 positivo
		
		Set<Node> adjacentNodes = new HashSet<>();
		
		int rowIndex = node.getRow();
		int colIndex = node.getCol();
		
		int right = colIndex+1;
		int left = colIndex-1;
		int up = rowIndex-1;
		int down = rowIndex+1;
		
		if(!isOutOfBounds(rowIndex, right)) {
			Node to = heightMap[rowIndex][right];
			if(heightDifference(node, to) <= 1) {
				adjacentNodes.add(to);
			}
		}
		
		if(!isOutOfBounds(rowIndex, left)) {
			Node to = heightMap[rowIndex][left];
			if(heightDifference(node, to) <= 1) {
				adjacentNodes.add(to);
			}

		}
		
		if(!isOutOfBounds(up, colIndex)) {
			Node to = heightMap[up][colIndex];
			if(heightDifference(node, to) <= 1) {
				adjacentNodes.add(to);
			}
		}
		
		if(!isOutOfBounds(down, colIndex)) {
			Node to = heightMap[down][colIndex];
			if(heightDifference(node, to) <= 1) {
				adjacentNodes.add(to);
			}
		}
		
		return adjacentNodes;
	}
	
	private boolean isOutOfBounds(int rowIndex, int colIndex) {
		return (rowIndex >= rows || rowIndex < 0) || (colIndex >= cols || colIndex < 0);
	}
	
	private int heightDifference(Node from, Node to) {
		return to.getHeight() - from.getHeight();
	}
}
