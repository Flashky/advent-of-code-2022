package com.adventofcode.flashk.day11;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Gatherers;

import org.apache.commons.lang3.tuple.Pair;

public class MonkeyMiddle {

	private static final int INPUT_LINES_PER_MONKEY = 7;
	private Monkey[] monkeys;
	
	public MonkeyMiddle(List<String> inputs) {
		
		// Nº monos = número de filas en el fichero / 7
		// Es necesario añadir una fila vacía al final del fichero.
		List<List<String>> monkeyInputs = inputs.stream().gather(Gatherers.windowFixed(INPUT_LINES_PER_MONKEY)).toList();
		monkeys = new Monkey[monkeyInputs.size()];
	
		int i = 0;
		int lcm = 1;
		for(List<String> monkeyInput : monkeyInputs) {
			monkeys[i] = new Monkey(monkeyInput);
			lcm *= monkeys[i].getDivisor();
			i++;
		}
		
		i = 0;
		for(i = 0; i < monkeys.length; i++) {
			monkeys[i].setLcm(lcm);
		}
	}
	
	public long solve(int rounds, boolean useRelief) {
		
		for(int i = 0; i < rounds; i++) {
			for(int monkeyIndex = 0; monkeyIndex < monkeys.length; monkeyIndex++) {
				Monkey currentMonkeyTurn = monkeys[monkeyIndex];
				while(currentMonkeyTurn.hasItems()) {
					Pair<Integer, Long> result = currentMonkeyTurn.throwItem(useRelief);
					monkeys[result.getLeft()].getItems().add(result.getRight());
				}
			}
			
			
		}
		
		// 1. Listar los monos ordenando por countedItems, 
		// 2. Limitar el resultado a los dos monos con un valor más alto
		// 3. Multiplicar el valor de ambos monos de countedItems
		// 4. Devolver resultado.
	
		return Arrays.stream(monkeys)
				.sorted(Comparator.reverseOrder())
				.limit(2)
				.mapToLong(Monkey::getCountedItems)
				.reduce(1, Math::multiplyExact);
	}
	
	
}
