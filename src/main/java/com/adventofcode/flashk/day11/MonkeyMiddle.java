package com.adventofcode.flashk.day11;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
			displayCount(i);
			for(int monkeyIndex = 0; monkeyIndex < monkeys.length; monkeyIndex++) {
				Monkey currentMonkeyTurn = monkeys[monkeyIndex];
				while(currentMonkeyTurn.hasItems()) {
					Pair<Integer,Integer> result = currentMonkeyTurn.throwItem(useRelief);
					monkeys[result.getFirst()].getItems().add(result.getSecond());
				}
			}
			
			
		}
		
		displayCount(rounds);
		
		// 1. Listar los monos ordenando por countedItems, 
		// 2. Limitar el resultado a los dos monos con un valor más alto
		// 3. Multiplicar el valor de ambos monos de countedItems
		// 4. Devolver resultado.
		

		List<Monkey> monkeyBusinessList = Arrays.stream(monkeys).sorted(Comparator.reverseOrder()).limit(2).collect(Collectors.toList());
		return monkeyBusinessList.get(0).getCountedItems()*monkeyBusinessList.get(1).getCountedItems();
	}

	private void displayCount(int round) {
		
		if(round != 1 && 
				round != 20 && 
				round != 1000 && 
				round != 2000) {
			return;
		}
		
		String phrase = "Monkey %s inspected items %s times.";
		if(round == 1 || round == 20) {
			System.out.println("== After round "+ String.valueOf(round));
			for(int i = 0; i < monkeys.length; i++) {
				System.out.println(String.format(phrase, i, monkeys[i].getCountedItems()));
			}
					
		}
	}
	
	
}
