package com.adventofcode.flashk.day20;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GrovePositioningSystem {


	/*
	 * Hay 3 algoritmos involucrados:
	 * 1. Movimiento de números.
	 * 2. Volcado de números a un array para poder calcular el número.
	 * 3. Cálculo del módulo.
	 */
	
	// 1. [HARD] Algoritmo de mezclado de números
	
	// La duda de este algoritmo es, ¿qué estructura/s de datos usar?
	// En primer lugar, debemos conocer el orden original de los números, por lo que necesitamos una cola de algún tipo para guardar dicho orden.
	// En esa cola solo sacaremos elementos en su orden.
	//
	// Por otro lado, necesitaremos otra estructura donde ir moviendo los números adelante y atrás.
	// Quizá un deque sea lo que buscamos, los deques permiten añadir elementos por delante y por detrás, esto es la definición clásica de una lista circular.
	// El mayor problema es: encontrar donde está el número si lo acabamos de mover.
	
	// 2. [EASY] Algoritmo de volcado de números a array

	// 	2.1 Vamos sacando elementos de la cola inicial y los vamos guardando en una cola auxiliar hasta encontrar el 0
	// 	2.2 Si encontramos el cero, añadimos la cola auxiliar por detrás de la cola inicial
	// 	2.3 Convertimos la cola en un array
	
	// Por ejemplo:
	// [1, 2, -3, 4, 0, 3, -2]
	// 
	// Sacamos el 1: cola principal [2, -3, 4, 0, 3, -2] cola secundaria [1]
	// Sacamos el 2: cola principal [-3, 4, 0, 3, -2] cola secundaria [1, 2]
	// Sacamos el -3: cola principal [4, 0, 3, -2] cola secundaria [1, 2, -3]
	// Sacamos el 4: cola principal [0, 3, -2] cola secundaria [1, 2, -3, 4]
	// Comprobamos que hemos llegado al 0, añadimos la cola secundaria a la principal:
	// [0, 3, -2] + [1, 2, -3, 4] => [0, 3, -2, 1, 2, -3, 4]
	// Convertimos la cola a array.
	
	
	// Premisas:
	// - Los números deben de ser movidos en el orden en el que estaban originalmente.
	// 	 - Esto quiere decir que aunque un número desplace a otro, el siguiente número sigue teniéndose que mover en la mismo orden
	// 	 - Por lo tanto, hay que guardar alguna referencia para saber qué número hay que mover a continuación. Quizá una cola.
	
	// 3. [EASY] Algoritmo de volcado de números.
	
	// En el nuevo array buscamos los números en posición 1000, 2000 y 3000. 
	// 
	// 3.1 Para ello, aplicamos index = N % list.size, para cada N en {1000,2000,3000}
	// 3.2 Para cada index, obtenemos el sumatorio de mixedNumbers[index]
	// 3.3 Sumamos los tres números
	
	// Por ejemplo:
	//
	// 	mixedNumbers = [0, 3, -2, 1, 2, -3, 4]
	//
	//	1000 % 7 = 6 => 4
	//	2000 % 7 = 5 => -3
	//	3000 % 7 = 4 => 2
	//
	// 	Sumamos los tres números: 4 - 3 + 2 = 3
	//
	// 	La solución sería 3.
	
	
	/*
	 Análisis de datos de entrada
	 - El sample tiene 7 números.
	 - El input tiene 5000  números.
	  
	 */

	
	private Deque<MixedNumber> mixedNumbers;
	private int movedNumbers = 0;
	private int currentOrder = 0;
	
	public GrovePositioningSystem(List<Integer> inputs) {
	
		mixedNumbers = inputs.stream().map(MixedNumber::new).collect(Collectors.toCollection(LinkedList::new));
		System.out.println("test");
	}
	
	public int solveA() {
		int result = 0;
		
		moveNumbers();
		orderNumbers(); // Move the circular list until 0 is at first position
		
		MixedNumber[] mixedNumbersResult = new MixedNumber[mixedNumbers.size()];
		mixedNumbersResult = mixedNumbers.toArray(mixedNumbersResult);
		
		int thousandIndex = 1000 % mixedNumbersResult.length;
		int thousandIndex2 = 2000 % mixedNumbersResult.length;
		int thousandIndex3 = 3000 % mixedNumbersResult.length;
		
		int thousandNumber = mixedNumbersResult[thousandIndex].getNumber();
		int thousandNumber2 = mixedNumbersResult[thousandIndex2].getNumber();
		int thousandNumber3 = mixedNumbersResult[thousandIndex3].getNumber();
		
		return thousandNumber + thousandNumber2 + thousandNumber3;
	}

	private void orderNumbers() {
		
		MixedNumber nextNumber = mixedNumbers.peek();
		
		while(nextNumber.getNumber() != 0) {
			mixedNumbers.addLast(mixedNumbers.pollFirst());
			nextNumber = mixedNumbers.peek();
		}
		
	}

	private void moveNumbers() {
		
		Optional<MixedNumber> nextNumber = searchNextToMove();

		while(nextNumber.isPresent()) {
			
			moveNumber(nextNumber.get());
			nextNumber = searchNextToMove();
		}
		
	}

	private void moveNumber(MixedNumber mixedNumber) {
	
		// TODO optimización a tener en cuenta
		// Si un array tiene 2 números y te piden mover un número x posiciones, es posible que el número se vuelva a quedar en la misma posición
		// Por lo que es posible que para números > número de elementos haya que aplicar un módulo.
		
		if(mixedNumber.getNumber() < 0 ) {
			moveToTheLeft(mixedNumber);
		} else {
			moveToTheRight(mixedNumber);
		}
		
		mixedNumber.setHasMoved(true);
		movedNumbers++;
			
	}

	private void moveToTheLeft(MixedNumber mixedNumber) {
		
		Deque<MixedNumber> rightSide = new LinkedList<>();
		
		// int movements = mixedNumber.getMovements();
		int movements = mixedNumber.getMovements() % mixedNumbers.size();
		
		for(int moveCount = 0; moveCount < movements; moveCount++) {
			
			// Sacamos el último número
			MixedNumber anotherNumber = mixedNumbers.pollLast();

			// Lo añadimos a la otra lista
			rightSide.addFirst(anotherNumber);
		}
		
		// Añadimos todos los números juntos de golpe a la derecha.
		rightSide.addFirst(mixedNumber);
		mixedNumbers.addAll(rightSide);
	}
	
	private void moveToTheRight(MixedNumber mixedNumber) {
		
		Deque<MixedNumber> leftSide = new LinkedList<>();
		//leftSide.add(mixedNumber);
		
		//int movements = mixedNumber.getMovements();
		int movements = mixedNumber.getMovements() % mixedNumbers.size();
		
		for(int moveCount = 0; moveCount < movements; moveCount++) {
			
			// Sacamos el último número
			MixedNumber anotherNumber = mixedNumbers.pollFirst();

			// Lo añadimos a la otra lista
			leftSide.addLast(anotherNumber);
		}
		
		leftSide.addLast(mixedNumber);
		
		// Añadimos todos los números a la izquierda, mantiendo el orden que tuvieran en la otra lista
		
		while(!leftSide.isEmpty()) {
			mixedNumbers.addFirst(leftSide.pollLast());
		}
		
	}

	private Optional<MixedNumber> searchNextToMove() {

		// Buscar el elemento cuyo order sea movedNumbers
	
		if(movedNumbers >= MixedNumber.getTotalNumbers()) {
			return Optional.empty();
		}
	
		boolean found = false;
		Optional<MixedNumber> foundNumber = Optional.empty();
		
		while(!found) {
			MixedNumber nextNumber = mixedNumbers.poll();
			if(nextNumber.getOrder() == currentOrder) {
				found = true;
				foundNumber = Optional.of(nextNumber);
				currentOrder++;
			} else {
				mixedNumbers.addLast(nextNumber);
			}
		}

		return foundNumber;
	}
	
	
}
