package com.adventofcode.flashk.day25;

import java.util.Map;

public class Snafu {

	
	private static Map<Character, Long> decimalMap = Map.of(
					'0', 0L,
					'1', 1L,
					'2', 2L,
					'-', -1L,
					'=', -2L);
	
	private static Map<Long, Character> snafuMap = Map.of(
			0L, '0',
			1L, '1',
			2L, '2',
			3L, '=',
			4L,'-');
	
	public static Long decValue(String number) {
		
		char[] numberDigits = number.toCharArray();
		
		long value = 0;
		for(int i = 0; i < numberDigits.length; i++) {
			int pow = numberDigits.length - i - 1;
			value += decimalMap.get(numberDigits[i]) * Math.pow(5, pow);
		}
		
		return value;
	}
	
	public static String snafuValue(long number) {
		
		if(number == 0) {
			return ""; // Or 0, but it is a left side 0
		}
		
		long mod = number % 5;
		
		Character snafuDigit = snafuMap.get(mod);
		String nextSnafuDigit = "";
		if(mod < 3) {
			nextSnafuDigit = snafuValue(number / 5);
		} else {
			nextSnafuDigit = snafuValue(number / 5 + 1);
		}
		
		return nextSnafuDigit + snafuDigit;
		
		
	}
}

