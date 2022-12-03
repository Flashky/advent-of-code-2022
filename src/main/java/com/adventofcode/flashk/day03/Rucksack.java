package com.adventofcode.flashk.day03;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Rucksack {

	private Map<String, Integer> priorities = new HashMap<>();
	private Set<String> countedItems = new HashSet<>();
	public Rucksack() {
		
		priorities.put("a", 1);
		priorities.put("b", 2);
		priorities.put("c", 3);
		priorities.put("d", 4);
		priorities.put("e", 5);
		priorities.put("f", 6);
		priorities.put("g", 7);
		priorities.put("h", 8);
		priorities.put("i", 9);
		priorities.put("j", 10);
		priorities.put("k", 11);
		priorities.put("l", 12);
		priorities.put("m", 13);
		priorities.put("n", 14);
		priorities.put("o", 15);
		priorities.put("p", 16);
		priorities.put("q", 17);
		priorities.put("r", 18);
		priorities.put("s", 19);
		priorities.put("t", 20);
		priorities.put("u", 21);
		priorities.put("v", 22);
		priorities.put("w", 23);
		priorities.put("x", 24);
		priorities.put("y", 25);
		priorities.put("z", 26);
		priorities.put("A", 27);
		priorities.put("B", 28);
		priorities.put("C", 29);
		priorities.put("D", 30);
		priorities.put("E", 31);
		priorities.put("F", 32);
		priorities.put("G", 33);
		priorities.put("H", 34);
		priorities.put("I", 35);
		priorities.put("J", 36);
		priorities.put("K", 37);
		priorities.put("L", 38);
		priorities.put("M", 39);
		priorities.put("N", 40);
		priorities.put("O", 41);
		priorities.put("P", 42);
		priorities.put("Q", 43);
		priorities.put("R", 44);
		priorities.put("S", 45);
		priorities.put("T", 46);
		priorities.put("U", 47);
		priorities.put("V", 48);
		priorities.put("W", 49);
		priorities.put("X", 50);
		priorities.put("Y", 51);
		priorities.put("Z", 52);

	}
	public long solveA(List<String> inputs) {
		
		long totalValue = 0;
		for (String rucksack : inputs) {
			countedItems = new HashSet<>();
			String container1 = rucksack.substring(0, (rucksack.length()/2));
			String container2 = rucksack.substring((rucksack.length()/2));
			char[] items = container1.toCharArray();
			
			
			for(char item : items) {
				String itemCharacter = Character.toString(item);
				if((container2.contains(itemCharacter)) && (!countedItems.contains(itemCharacter))){
					totalValue += priorities.get(itemCharacter);
					countedItems.add(itemCharacter);
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
	
	private int findBadgeValue(String[] groupRucksacks) {
		String baseRucksack = groupRucksacks[0];
		char[] items = baseRucksack.toCharArray();
		
		for(char item : items) {
			String itemCharacter = Character.toString(item);
			if((groupRucksacks[1].contains(itemCharacter)) && 
					(groupRucksacks[2].contains(itemCharacter))){
				return priorities.get(itemCharacter);
			}
		}
		
		return 0;
	}
}
