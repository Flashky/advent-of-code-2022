package com.adventofcode.flashk.day01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class CalorieCounting {

	public int solve(List<String> calories) {
		
		List<Integer> elvesCalories = new ArrayList<>();
		
		Integer maxCalories = 0;
		Integer currentElveCalories = 0;
		
		for(String calorie : calories) {
			if(StringUtils.isBlank(calorie)) {
				
				if(currentElveCalories > maxCalories) {
					maxCalories = currentElveCalories;
				}
				
				// Count total and reset elve
				elvesCalories.add(currentElveCalories);
				currentElveCalories = 0;
				
			} else {
				currentElveCalories += Integer.parseInt(calorie);
			}
		}
		
		elvesCalories.add(currentElveCalories);
		
		return maxCalories;
	}
	
	public int solveB(List<String> calories) {
		
		List<Integer> elvesCalories = new ArrayList<>();

		Integer maxCalories = 0;
		Integer currentElveCalories = 0;
		
		for(String calorie : calories) {
			if(StringUtils.isBlank(calorie)) {
				
				if(currentElveCalories > maxCalories) {
					maxCalories = currentElveCalories;
				}
				
				// Count total and reset elve
				elvesCalories.add(currentElveCalories);
				currentElveCalories = 0;
				
			} else {
				currentElveCalories += Integer.parseInt(calorie);
			}
		}
		elvesCalories.add(currentElveCalories);
		List<Integer> sortedCalories = elvesCalories.stream().sorted(Collections.reverseOrder()).limit(3).collect(Collectors.toList());
		
		
		return sortedCalories.get(0) + sortedCalories.get(1) + sortedCalories.get(2);
	}
	
	/*
	private List<Integer> calculateElvesCalories(List<String> calories) {
		
	}*/
}
