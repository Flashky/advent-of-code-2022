package com.adventofcode.flashk.day25;

import java.util.List;

public class FullOfHotAir {

	private List<String> snafuNumbers;
	
	public FullOfHotAir(List<String> inputs) {
		this.snafuNumbers = inputs;
	}
	
	public String solve() {
		long sum = snafuNumbers.stream().map(Snafu::decValue).reduce(0L, Long::sum);
		return Snafu.snafuValue(sum);
	}
	
}
