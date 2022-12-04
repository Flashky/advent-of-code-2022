package com.adventofcode.flashk.day04;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ElvePair {

	public final static String ASSIGNMENT_PATTERN = "([0-9]*)[-]([0-9]*)[,]([0-9]*)[-]([0-9]*)";
	private final static Pattern PATTERN = Pattern.compile(ASSIGNMENT_PATTERN);
	
	private int firstElveStart;
	private int firstElveEnd;
	private int secondElveStart;
	private int secondElveEnd;
	
	public ElvePair(String input) {
		Matcher matcher = PATTERN.matcher(input);
		matcher.find();
		
		firstElveStart = Integer.parseInt(matcher.group(1)); 
		firstElveEnd = Integer.parseInt(matcher.group(2));
		secondElveStart = Integer.parseInt(matcher.group(3));
		secondElveEnd = Integer.parseInt(matcher.group(4));
	}
}
