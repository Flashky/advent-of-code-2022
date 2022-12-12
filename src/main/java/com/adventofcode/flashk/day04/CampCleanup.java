package com.adventofcode.flashk.day04;

import java.util.List;
import java.util.stream.Collectors;

public class CampCleanup {

	private List<ElvePair> elvePairs;
	
	public CampCleanup(List<String> inputs) {
		
		elvePairs = inputs.stream().map(ElvePair::new).collect(Collectors.toList());

	}
	
	public int solve(CampCleanupStrategy strategy) {
		return strategy.solve(elvePairs);
		

	}
	
}
