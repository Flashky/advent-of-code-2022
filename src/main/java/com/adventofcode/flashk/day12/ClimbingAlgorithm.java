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
	
	private List<Node> origins = new ArrayList<>();
	
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
					origins.add(heightMap[rowIndex][colIndex]);
				} else if (height == END) {
					destination = heightMap[rowIndex][colIndex];
					destination.setHeight(HIGHEST_HEIGHT);
				} else if(height == LOWEST_HEIGHT) {
					origins.add(heightMap[rowIndex][colIndex]);
				}
				
				colIndex++;
				
			}
			
			rowIndex++;
		}
		
	}
	
	public long solveA() {
		
		// Uses Dijkstra algorithm to solve the shortest path possible.
		
		origin.setTotalSteps(1);
		PriorityQueue<Node> queue = new PriorityQueue<>();
		queue.add(origin);
		
		while(!queue.isEmpty()) {
			
			Node minNode = queue.poll();
			minNode.setVisited(true);

			Set<Node> adjacentNodes = getAdjacentNodes(minNode);
			
			for(Node adjacentNode : adjacentNodes) {
				if(!adjacentNode.isVisited()) {
					
					// Cost of moving to the adjacent node
					int step = adjacentNode.getStep();
					
					// Cost of moving to the adjacent node + total cost to reach to this node
					int estimatedSteps = minNode.getTotalSteps() + step;
					
					if(adjacentNode.getTotalSteps() > estimatedSteps) {
						adjacentNode.setTotalSteps(estimatedSteps);
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
		for(Node possibleStart : origins) {
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
	
	private Set<Node> getAdjacentNodes(Node fromNode) {
		
		Set<Node> adjacentNodes = new HashSet<>();
		
		int rowIndex = fromNode.getRow();
		int colIndex = fromNode.getCol();
		
		int right = colIndex+1;
		int left = colIndex-1;
		int up = rowIndex-1;
		int down = rowIndex+1;
		
		if(!isOutOfBounds(rowIndex, right)) {
			adjacentNodes.add(heightMap[rowIndex][right]);
		}
		
		if(!isOutOfBounds(rowIndex, left)) {
			adjacentNodes.add(heightMap[rowIndex][left]);
		}
		
		if(!isOutOfBounds(up, colIndex)) {
			adjacentNodes.add(heightMap[up][colIndex]);
		}
		
		if(!isOutOfBounds(down, colIndex)) {
			adjacentNodes.add(heightMap[down][colIndex]);
		}
		
		// Keep only adjacent nodes that height difference is 1 or less.
		return adjacentNodes.stream().filter(toNode -> heightDifference(fromNode, toNode) <= 1).collect(Collectors.toSet());
	}
	
	private boolean isOutOfBounds(int rowIndex, int colIndex) {
		return (rowIndex >= rows || rowIndex < 0) || (colIndex >= cols || colIndex < 0);
	}
	
	private int heightDifference(Node from, Node to) {
		return to.getHeight() - from.getHeight();
	}
}
