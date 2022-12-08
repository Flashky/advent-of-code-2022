package com.adventofcode.flashk.day08;

import java.util.List;

public class TreeHouse {

	private int[][] treeMap;
	private int cols;
	private int rows;
	
	public TreeHouse(List<String> inputs) {
		
		// Initialize tree map values
		cols = inputs.get(0).length();
		rows = inputs.size();
	
		treeMap = new int[rows][cols];
		
		for(int row = 0; row < inputs.size(); row++) {
			treeMap[row] = inputs.get(row).chars().map(Character::getNumericValue).toArray();
		}

	}
	
	public int solveA() {
		int result = 0;
		
		for(int rowIndex = 0; rowIndex < rows; rowIndex++) {
			for(int colIndex = 0; colIndex < cols; colIndex++) {
				if(isVisible(rowIndex, colIndex)) {
					result++;
				}
			}
		}
		return result;
	}
	
	public long solveB() {
		
		long maxScenicScore = Long.MIN_VALUE;
		
		for(int rowIndex = 0; rowIndex < rows; rowIndex++) {
			for(int colIndex = 0; colIndex < cols; colIndex++) {
				
				long currentTreeScenicScore = calculateScore(rowIndex,colIndex);
				
				if(currentTreeScenicScore > maxScenicScore) {
					maxScenicScore = currentTreeScenicScore;
				}
			}
		}
		return maxScenicScore;
	}


	private boolean isVisible(int rowIndex, int colIndex) {

		// Grid border
		
		if((rowIndex == 0) || (rowIndex == rows-1)) {
			return true;
		}
		
		if((colIndex == 0) || (colIndex == cols-1)) {
			return true;
		}
		
		// Grid center
		
		if(isVisibleLeft(rowIndex, colIndex)) {
			return true;
		}
		
		if(isVisibleRight(rowIndex, colIndex)) {
			return true;
		}
		
		if(isVisibleTop(rowIndex, colIndex)) {
			return true;
		}
		
		if(isVisibleBottom(rowIndex, colIndex)) {
			return true;
		}
		
		return false;
	}

	private boolean isVisibleLeft(int rowIndex, int colIndex) {
		
		int treeHeight = treeMap[rowIndex][colIndex];
		
		for(int i = colIndex-1; i >= 0; i--) {
			if(treeMap[rowIndex][i] >= treeHeight) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean isVisibleRight(int rowIndex, int colIndex) {
		
		int treeHeight = treeMap[rowIndex][colIndex];
		// rowIndex = 2 colIndex = 2, must be right
		for(int i = colIndex+1; i < cols; i++) {
			if(treeMap[rowIndex][i] >= treeHeight) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean isVisibleTop(int rowIndex, int colIndex) {
		
		int treeHeight = treeMap[rowIndex][colIndex];
		
		for(int i = rowIndex-1; i >= 0; i--) {
			if(treeMap[i][colIndex] >= treeHeight) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean isVisibleBottom(int rowIndex, int colIndex) {
		
		int treeHeight = treeMap[rowIndex][colIndex];
		
		for(int i = rowIndex+1; i < rows; i++) {
			if(treeMap[i][colIndex] >= treeHeight) {
				return false;
			}
		}
		
		return true;
	}
	
	private long calculateScore(int rowIndex, int colIndex) {
		
		// Grid border
		
		if((rowIndex == 0) || (rowIndex == rows-1)) {
			return Long.MIN_VALUE;
		}
		
		if((colIndex == 0) || (colIndex == cols-1)) {
			return Long.MIN_VALUE;
		}
		
		// Grid center
		
		long leftScore = calculateScoreLeft(rowIndex, colIndex);
		long rightScore = calculateScoreRight(rowIndex, colIndex);
		long topScore = calculateScoreTop(rowIndex, colIndex);
		long bottomScore =calculateScoreBottom(rowIndex, colIndex);
		
		
		return leftScore * rightScore * topScore * bottomScore;
	}

	private long calculateScoreLeft(int rowIndex, int colIndex) {
		
		int treeHeight = treeMap[rowIndex][colIndex];
		
		long currentScore = 0;
		for(int i = colIndex-1; i >= 0; i--) {
			currentScore++;
			if(treeMap[rowIndex][i] >= treeHeight) {
				return currentScore;
			}
		}
		
		return currentScore;
	}
	

	private long calculateScoreRight(int rowIndex, int colIndex) {
		
		int treeHeight = treeMap[rowIndex][colIndex];

		long currentScore = 0;
		for(int i = colIndex+1; i < cols; i++) {
			currentScore++;
			if(treeMap[rowIndex][i] >= treeHeight) {
				return currentScore;
			}
		}
		
		return currentScore;
	}
	
	private long calculateScoreTop(int rowIndex, int colIndex) {
		
		int treeHeight = treeMap[rowIndex][colIndex];
		
		long currentScore = 0;
		for(int i = rowIndex-1; i >= 0; i--) {
			currentScore++;
			if(treeMap[i][colIndex] >= treeHeight) {
				return currentScore;
			}
		}
		
		return currentScore;
	}
	
	private long calculateScoreBottom(int rowIndex, int colIndex) {
		
		int treeHeight = treeMap[rowIndex][colIndex];
		
		long currentScore = 0;
		for(int i = rowIndex+1; i < rows; i++) {
			currentScore++;
			if(treeMap[i][colIndex] >= treeHeight) {
				return currentScore;
			}
		}
		
		return currentScore;
	}
}
