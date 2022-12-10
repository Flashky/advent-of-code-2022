package com.adventofcode.flashk.day10;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CathodeRayTube {

	// Drawing CRT constants
	private static final int CRT_WIDTH = 40;
	private static final int CRT_HEIGHT = 6;
	private static final char LIT = '#';
	private static final char DARK = '.';
	
	// Problem input
	private List<Instruction> instructions;
	
	// Cycle, register and signal strength variables
	private int currentCycle = 1;
	private int x = 1;
	private Set<Integer> interestingSignalStrengths = new HashSet<>();
	
	// Drawing CRT variables
	private char[][] crt = new char[CRT_HEIGHT][CRT_WIDTH];
	private char[] spriteBar = new char[40];
	private int rowIndex = 0;
	private int colIndex = 0;
	
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

	public int solve() {
		int result = 0;
		
		for(Instruction instruction : instructions) {
			int waitCycles = instruction.getCycles();
			
			for(int i = 0; i < waitCycles; i++) {
				
				if(interestingSignalStrengths.contains(currentCycle)) {
					int signalStrength = x*currentCycle;
					result += signalStrength;
				}
				
				drawPixel();			
				currentCycle++;
				
			}
			
			if(Instruction.ADDX_INSTRUCTION.equals(instruction.getAction())) {
				x += instruction.getValue();
				
				// Update sprite position
				updateSpritePosition();
			}
		}
		
		drawSolution();
		
		return result;
	}

	private void drawSolution() {
		
		System.out.println();
		for(int rowIndex = 0; rowIndex < CRT_HEIGHT; rowIndex++) {
			for(int colIndex = 0; colIndex < CRT_WIDTH; colIndex++) {
				System.out.print(crt[rowIndex][colIndex]);
			}
			System.out.println();
		}
	}

	private void updateSpritePosition() {
		
		// Reset sprite bar
		for(int i = 0; i < CRT_WIDTH; i++) {
			spriteBar[i] = DARK;
		}
		
		int startPos = x-1;
		int endPos = startPos+3;

		// Check out of bounds
		if(startPos < 0) {
			startPos = 0;
		}
		
		if(endPos >= CRT_WIDTH) {
			endPos = CRT_WIDTH - 1;
		}
		
		// Redraw sprite
		for(int i = startPos; i < endPos; i++) {
			spriteBar[i] = LIT;
		}
		
	}
	
	private void drawPixel() {
		int spriteBarIndex = (currentCycle-1) % CRT_WIDTH;
		
		// Check to update rowIndex and colIndex
		// rowIndex depende del ciclo y a su vez la columna
		crt[rowIndex][colIndex] = spriteBar[spriteBarIndex];
		
		colIndex++;
		if(colIndex == 40) {
			colIndex = 0;
			rowIndex++;
		}
	}
}
