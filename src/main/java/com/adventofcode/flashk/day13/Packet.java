package com.adventofcode.flashk.day13;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import lombok.Getter;

@Getter
public class Packet {

	private static final char OPEN_BRACKET = '[';
	private static final char CLOSE_BRACKET = ']';
	private static final char SEPARATOR = ',';
	
	private static final int OK = 1;
	private static final int UNDETERMINED = 0;
	private static final int NOT_OK = -1;
	
	private String DIGIT_REGEX = "(\\d*)[,\\]]";
	private Pattern DIGIT_PATTERN = Pattern.compile(DIGIT_REGEX);
	
	private JsonNode rootLeft;
	private JsonNode rootRight;

	public Packet(List<String> inputs) {
		rootLeft = buildTree(inputs.get(0));
		rootRight = buildTree(inputs.get(1));
		
	}
	
	/**
	 * Compares the left and right side of the packet.
	 * @return true if left side is smaller than right side. Returns false otherwise.
	 */
	public boolean compare() {
		int result = compare(rootLeft, rootRight);
		
		if(result >= 0) {
			return true;
		}
		
		return false; 

	}
	
	// 1 ok
	// 0 undetermined
	// -1 not ok
	private int compare(JsonNode left, JsonNode right) {
		
		// Both nodes are numbers
		if(left.isNumber() && right.isNumber()) {
			
			// If the left integer is lower than the right integer, the inputs are in the right order.
			if(left.intValue() < right.intValue()) {
				return OK;
			}
			
			// If the left integer is higher than the right integer, the inputs are not in the right order.
			if(left.intValue() > right.intValue()) {
				return NOT_OK;
			}
			
			// Otherwise, the inputs are the same integer; continue checking the next part of the input.
			return UNDETERMINED; // Go to the next recursive call, but for now keep it as valid
			
		}

		// Both nodes are arrays
		if (left.isArray() && right.isArray()) {
			return compareArray(left.elements(), right.elements());
		} else if(left.isNumber() && right.isArray()) {
			
			// Left is number, right is array
			
			// Convert integer to list and retry comparison
			ObjectMapper mapper = new ObjectMapper();
			ArrayNode array = mapper.createArrayNode();
			array.add(left.intValue());
		
			return compare(array, right);
			
		} else {

			// Left is array, right is number
			
			// Convert integer to list and retry comparison
			ObjectMapper mapper = new ObjectMapper();
			ArrayNode array = mapper.createArrayNode();
			array.add(right.intValue());
			
			return compare(left, array);
		}
		
	}
	

	private int compareArray(Iterator<JsonNode> leftElements, Iterator<JsonNode> rightElements) {
		
		//int lastComparison = UNDETERMINED;
		
		// Compare the first value of each list, then the second value, and so on.
		while(leftElements.hasNext()) {
			
			JsonNode nextLeft = leftElements.next();
			
			if(!rightElements.hasNext()) {
				// If the right list runs out of items first, the inputs are not in the right order.
				return NOT_OK;
			}
			
			JsonNode nextRight = rightElements.next();
			
			int lastComparison = compare(nextLeft, nextRight);
			
			if(lastComparison != UNDETERMINED) {
				return lastComparison;
			}
		}
		
		if(!rightElements.hasNext()) {
			// Same length
			return UNDETERMINED;
		} else {
			// If the left list runs out of items first, the inputs are in the right order.
			return OK;
		}

	}
	
	private JsonNode buildTree(String input) {
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readTree(input);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	

}
