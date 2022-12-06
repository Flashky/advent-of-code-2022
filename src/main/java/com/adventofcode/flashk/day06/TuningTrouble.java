package com.adventofcode.flashk.day06;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class TuningTrouble {

	public long solve(String input, int expectedMarkerSize) {
		
		Deque<Character> currentMarker = new LinkedList<>();
		Set<Character> uniqueCharacters = new HashSet<>();
		
		int i = 0;
		while((uniqueCharacters.size() != expectedMarkerSize) && (i < input.length())) {

			Character currentCharacter = input.charAt(i++);
			currentMarker.addLast(currentCharacter);
			uniqueCharacters.add(currentCharacter);
			
			if(currentMarker.size() > expectedMarkerSize) {
				
				Character oldCharacter = currentMarker.pollFirst();
				
				// Remove non duplicated characters
				if(!currentMarker.contains(oldCharacter)) {
					uniqueCharacters.remove(oldCharacter);
				}
			}
			
		}
			
		return i;
		
	}
}
