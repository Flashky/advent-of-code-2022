package com.adventofcode.flashk.day10;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CathodeRayTube {

	private static final int CRT_WIDTH = 40;
	private static final int CRT_HEIGHT = 6;
	
	private List<Instruction> instructions;
	
	private int currentCycle = 1;
	private int x = 1;
	private Set<Integer> interestingSignalStrengths = new HashSet<>();
	
	
	char[][] crt = new char[CRT_WIDTH][CRT_HEIGHT];
	private int rowIndex = 0;
	private int colIndex = 0;
	
	char[] spriteBar = new char[40];
	
	public CathodeRayTube(List<String> inputs) {
		
		instructions = inputs.stream().map(input -> new Instruction(input)).collect(Collectors.toList());
		
		interestingSignalStrengths.add(20);
		interestingSignalStrengths.add(60);
		interestingSignalStrengths.add(100);
		interestingSignalStrengths.add(140);
		interestingSignalStrengths.add(180);
		interestingSignalStrengths.add(220);
		
		updateSpritePosition();
		
	}
	
	private void updateSpritePosition() {
		
		for(int i = 0; i < CRT_WIDTH; i++) {
			spriteBar[i] = '.';
		}
		
		int startPos = x-1;
		int endPos = startPos+3;
		for(int i = startPos; i < endPos; i++) {
			spriteBar[i] = '#';
		}
		
	}

	public int solveA() {
		int result = 0;
		
		for(Instruction instruction : instructions) {
			int waitCycles = instruction.getCycles();
			
			for(int i = 0; i < waitCycles; i++) {
				
				if(interestingSignalStrengths.contains(currentCycle)) {
					int signalStrength = x*currentCycle;
					result += signalStrength;
				}
				currentCycle++;
				
			}
			
			if(Instruction.ADDX_INSTRUCTION.equals(instruction.getAction())) {
				x += instruction.getValue();
			}
		}
		
		return result;
	}
}
