package com.adventofcode.flashk.day04;

import java.util.List;

public class CampCleanupBasicStrategy extends CampCleanupStrategy {

	@Override
	public int solve(List<ElvePair> elvePairs) {
		
		int result = 0;
		
		for(ElvePair pair : elvePairs) {
			if((pair.getFirstElveStart() >= pair.getSecondElveStart()) 
					&& (pair.getFirstElveEnd() <= pair.getSecondElveEnd())) {
				result++;
			} else if ((pair.getSecondElveStart() >= pair.getFirstElveStart()) 
					&& (pair.getSecondElveEnd() <= pair.getFirstElveEnd())) {
				result++;
			}
		}
		
		return result;
	}

}
