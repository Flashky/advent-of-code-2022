package com.adventofcode.flashk.day03;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class Rucksack {

	private static final int NOT_FOUND = -1;
	private static final int CHARACTER_LOWER_OFFSET = 9;
	private static final int CHARACTER_UPPER_OFFSET = 17;
	
	public long solveA(List<String> inputs) {
		
		long totalValue = 0;
		for (String rucksack : inputs) {
			Set<Character> countedItems = new HashSet<>();
			
			String container1 = rucksack.substring(0, (rucksack.length()/2));
			String container2 = rucksack.substring((rucksack.length()/2));
			
			char[] items = container1.toCharArray();
			for(char item : items) {
				
				if(container2.indexOf(item) != NOT_FOUND && (!countedItems.contains(item))){
					totalValue += getValue(item);
					countedItems.add(item);
				}
			}
		}
		return totalValue;
	}

	public long solveB(List<String> inputs) {
		long totalValue = 0;
		int elveCount = 0;
		String[] groupRucksacks = new String[3];
		for (String rucksack : inputs) {
			
			
			if(elveCount == 0) {
				groupRucksacks = new String[3];
			}
			
			groupRucksacks[elveCount++] = rucksack;
			
			if(elveCount == 3) {
				totalValue += findBadgeValue(groupRucksacks);
				elveCount = 0;
			}
			
		}
		
		return totalValue;
	}
	
	private int getValue(char item) {

		int baseValue = Character.getNumericValue(item);
		String itemCharacter = Character.toString(item);		
		if(StringUtils.isAllLowerCase(itemCharacter)) {
			return baseValue - CHARACTER_LOWER_OFFSET;
		} 
		
		return baseValue + CHARACTER_UPPER_OFFSET;
	}
	
	private int findBadgeValue(String[] groupRucksacks) {
		String baseRucksack = groupRucksacks[0];
		char[] items = baseRucksack.toCharArray();
		
		for(char item : items) {
			if(groupRucksacks[1].indexOf(item) != NOT_FOUND && 
				groupRucksacks[2].indexOf(item) != NOT_FOUND){
				return getValue(item);
			}
		}
		
		return 0;
	}
}
