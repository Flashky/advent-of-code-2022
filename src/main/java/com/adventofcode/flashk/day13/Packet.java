package com.adventofcode.flashk.day13;

import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Packet implements Comparable<Packet> {

	private static final int OK = 1;
	private static final int UNDETERMINED = 0;
	private static final int NOT_OK = -1;
	
	private JsonNode root;
	private String packetCode;
	
	@Setter
	private int packetIndex;
	
	public Packet(String input) {
		root = buildTree(input);
		packetCode = input;
	}
	
	/**
	 * Compares left tree node with right tree node
	 * @param left the left side tree node
	 * @param right the right side tree node
	 * @return An integer indicating the comparison result between left and right: 
	 * <ul>
	 * 	<li><code> 1</code>: if <code>left</code> is smaller than <code>right</code>.</li> 
	 * 	<li><code>-1</code>: if <code>left</code> is bigger than <code>right></code>.</li>
	 * 	<li><code>0</code>: if undetermined or equal.</li>
	 * </ul>
	 */
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
			return UNDETERMINED;
			
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

	@Override
	public int compareTo(Packet other) {
		return compare(root, other.root);
	}


	@Override
	public String toString() {
		return "Packet [packetCode=" + packetCode + "]";
	}

}
