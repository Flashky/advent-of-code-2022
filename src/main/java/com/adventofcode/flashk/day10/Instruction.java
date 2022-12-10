package com.adventofcode.flashk.day10;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;

@Getter
public class Instruction {

	public final static String NOOP_INSTRUCTION = "noop";
	public final static String ADDX_INSTRUCTION = "addx";
	
	private final static String ADDX_INSTRUCTION_REGEX = "(addx)[ ](-[0-9]*|[0-9]*)";
	private final static Pattern ADDX_INSTRUCTION_PATTERN = Pattern.compile(ADDX_INSTRUCTION_REGEX);
	
	private String action;
	private int cycles;
	private int value;
	
	public Instruction(String input) {
		
		if(NOOP_INSTRUCTION.equals(input)) {
			this.action = input;
			this.cycles = 1;
		} else {
			Matcher matcher = ADDX_INSTRUCTION_PATTERN.matcher(input);
			
			matcher.find();
			this.action = matcher.group(1);
			this.cycles = 2;
			this.value = Integer.parseInt(matcher.group(2));
		}
	}
}
