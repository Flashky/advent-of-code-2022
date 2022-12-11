package com.adventofcode.flashk.day11;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.adventofcode.flashk.common.Pair;
import com.google.common.collect.Lists;

public class MonkeyMiddle {

	private static final int INPUT_LINES_PER_MONKEY = 7;
	private Monkey[] monkeys;
	
	public MonkeyMiddle(List<String> inputs) {
		
		// Nº monos = número de filas en el fichero / 7
		// Es necesario añadir una fila vacía al final del fichero.
		
		List<List<String>> monkeyInputs = Lists.partition(inputs, INPUT_LINES_PER_MONKEY);
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
					Pair<Integer,Long> result = currentMonkeyTurn.throwItem(useRelief);
					monkeys[result.getFirst()].getItems().add(result.getSecond());
				}
			}
			
			
		}
		
		// 1. Listar los monos ordenando por countedItems, 
		// 2. Limitar el resultado a los dos monos con un valor más alto
		// 3. Multiplicar el valor de ambos monos de countedItems
		// 4. Devolver resultado.
		//ai.accumulateAndGet(y, (x, y) -> x * y);
		AtomicLong monkeyBusinessCount = new AtomicLong(1);
	
		Arrays.stream(monkeys)
				.sorted(Comparator.reverseOrder()).limit(2)
				.map(monkey -> monkey.getCountedItems())
				.forEach(countedItems -> monkeyBusinessCount.set(monkeyBusinessCount.get()*countedItems));
		
		return monkeyBusinessCount.get();
	}
	
	
}
