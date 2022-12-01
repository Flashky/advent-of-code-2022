package com.adventofcode.flashk.day01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;

public class CalorieCounting {

	private List<Integer> elvesCalories = new ArrayList<>();
	
	public CalorieCounting(List<String> inputs) {

		Integer currentElveCalories = 0;
		
		for(String calorie : inputs) {
			if(StringUtils.isBlank(calorie)) {
				elvesCalories.add(currentElveCalories);
				currentElveCalories = 0;
			} else {
				currentElveCalories += Integer.parseInt(calorie);
			}
		}
		
		elvesCalories.add(currentElveCalories);

	}
	
	public int solve(int numberOfElves) {
		
		// Use an AtomicInteger as I need wrapper object in forEach
		AtomicInteger totalCalories = new AtomicInteger(0);
		elvesCalories.stream().sorted(Collections.reverseOrder()).limit(numberOfElves).forEach(elveCalories -> totalCalories.getAndAdd(elveCalories));
		return totalCalories.get();
		
	}
	
}
