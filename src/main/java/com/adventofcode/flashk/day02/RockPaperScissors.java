package com.adventofcode.flashk.day02;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RockPaperScissors {

	public final static String ROUND_PATTERN = "(A|B|C) (X|Y|Z)";
	private final static Pattern PATTERN = Pattern.compile(ROUND_PATTERN);
	
	public long solveA(List<String> inputs) {
	
		long totalScore = 0;
		for(String round : inputs) {
			
			Matcher matcher = PATTERN.matcher(round);
			matcher.find();

			
			String elveChoice = decrypt(matcher.group(1));
			String myChoice = decrypt(matcher.group(2));
			
			
			switch(elveChoice) {
				case "Rock": totalScore += elvePlaysRock(myChoice); break;
				case "Paper": totalScore += elvePlaysPaper(myChoice); break;
				case "Scissors": totalScore += elvePlaysScissors(myChoice); break;
				default:
					throw new IllegalArgumentException("Invalid choice");
			}
			
		}
	
		return totalScore;
	}
	
	public long solveB(List<String> inputs) {
		
		long totalScore = 0;
		for(String round : inputs) {
			
			Matcher matcher = PATTERN.matcher(round);
			matcher.find();

			
			String elveChoice = decrypt(matcher.group(1));
			String expectedResult = decryptExpectedResult(matcher.group(2));
			
			
			switch(expectedResult) {
				case "Lose": totalScore += needToLose(elveChoice); break;
				case "Draw": totalScore += needToDraw(elveChoice); break;
				case "Win": totalScore += needToWin(elveChoice); break;
				default:
					throw new IllegalArgumentException("Invalid choice");
			}
			
		}
	
		return totalScore;
	}
	
	private int needToLose(String elveChoice) {
		
		int score = 0;
		
		int rockScore = 1;
		int paperScore = 2;
		int scissorsScore = 3;
		
		switch(elveChoice) {
			case "Rock": score = scissorsScore; break;
			case "Paper": score = rockScore; break;
			case "Scissors": score = paperScore; break;
			default:
				throw new IllegalArgumentException("Invalid choice");
		}
		
		return score;
	}
	
	private int needToDraw(String elveChoice) {
		
		int score = 3;
		
		int rockScore = 1;
		int paperScore = 2;
		int scissorsScore = 3;
		
		switch(elveChoice) {
			case "Rock": score += rockScore; break;
			case "Paper": score += paperScore; break;
			case "Scissors": score += scissorsScore; break;
			default:
				throw new IllegalArgumentException("Invalid choice");
		}
		
		return score;
	}
	
	private int needToWin(String elveChoice) {
		
		int score = 6;
		
		int rockScore = 1;
		int paperScore = 2;
		int scissorsScore = 3;
		
		switch(elveChoice) {
			case "Rock": score += paperScore; break;
			case "Paper": score += scissorsScore; break;
			case "Scissors": score += rockScore; break;
			default:
				throw new IllegalArgumentException("Invalid choice");
		}
		
		return score;
	}
	
	private String decrypt(String choice) {
		
		switch(choice) {
			case "A":
			case "X":
				return "Rock";
			case "B":
			case "Y":
				return "Paper";
			case "C":
			case "Z":
				return "Scissors";
			default:
				throw new IllegalArgumentException("Invalid choice");
		}
		
	}
	
	private String decryptExpectedResult(String myChoice) {
		
		switch(myChoice) {
			case "X":
				return "Lose";
			case "Y":
				return "Draw";
			case "Z":
				return "Win";
			default:
				throw new IllegalArgumentException("Invalid choice");
		}
		
	}
	
	private int elvePlaysRock(String myChoice) {
		int score = 0;
		
		switch(myChoice) {
			case "Rock": score = 1+3; break;
			case "Paper": score = 2+6; break;
			case "Scissors": score = 3+0; break;
			default:
				throw new IllegalArgumentException("Invalid choice");
		}
		
		return score;
	}
	
	
	private int elvePlaysPaper(String myChoice) {
		int score = 0;
		
		switch(myChoice) {
			case "Rock": score = 1+0; break;
			case "Paper": score = 2+3; break;
			case "Scissors": score = 3+6; break;
			default:
				throw new IllegalArgumentException("Invalid choice");
		}
		
		return score;
	}
	
	private int elvePlaysScissors(String myChoice) {
		int score = 0;
		
		switch(myChoice) {
			case "Rock": score = 1+6; break;
			case "Paper": score = 2+0; break;
			case "Scissors": score = 3+3; break;
			default:
				throw new IllegalArgumentException("Invalid choice");
		}
		
		return score;
	}
}
