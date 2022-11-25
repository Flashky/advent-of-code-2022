package com.adventofcode.flashk.common.test.utils;

public class Timer {

	private Long startTime = null;
	private boolean running = false;
	
	// TODO https://www.reddit.com/r/adventofcode/comments/rdrewg/2021_my_aim_is_for_all_of_this_years_solutions_to/
	// Just a basic fixed-width formatting. String Format for the table: $"Year {d.Year}, Day {d.DayNumber,2}, Type {d.CodeType,9} : {d.TotalMicroseconds,13:N0} μs"
	
	public void start() {
		
		this.running = true;
		this.startTime = System.currentTimeMillis();
	}
	
	public void stop() {
		
		if(!running) {
			throw new IllegalStateException("Timer is not running.");
		}

		Long stopTime = System.currentTimeMillis();
		Long elapsedTime = stopTime - startTime;
		running = false;		
		
		System.out.println(elapsedTime + "ms");
		
	}
	
	public static void printHeader(String day) {
		System.out.println();
		System.out.println("========= "+ day + " ==========");
		System.out.println("---------------------------");
		System.out.println("# | Input  | Elapsed time");
		System.out.println("---------------------------");
	}
}
