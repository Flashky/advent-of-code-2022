package com.adventofcode.flashk.day04;

import java.util.ArrayList;
import java.util.List;

public class CampCleanup {

	private List<ElvePair> elvePairs = new ArrayList<>();
	
	public CampCleanup(List<String> inputs) {
		for(String input : inputs) {
			elvePairs.add(new ElvePair(input));
		}
	}
	
	public int solve(CampCleanupStrategy strategy) {
		return strategy.solve(elvePairs);
		

	}
	
}
