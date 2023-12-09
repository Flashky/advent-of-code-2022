package com.adventofcode.flashk.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonUtil {

	private JsonUtil() {}
	
	/**
	 * Builds a json tree.
	 * <p>Input accepts basic json and even simple arrays such as:</p>
	 * <pre>
	 * [4]
	 * [1,1,3,1,1]
	 * [[[],[],8,3],[10]]
	 * [[2],[3,[[],[1]],[],[0,[10,7]]],[[]],[7,[6],8,[9,0],[2]]]
	 * </pre>
	 * <p>This is useful for AoC puzzles such as 2021 day 18 and 2022 day 13.</p>
	 * @param input the input string
	 * @return The parsed <code>JsonNode</code> tree.
	 */
	public static JsonNode buildTree(String input) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readTree(input);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
