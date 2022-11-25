package com.adventofcode.flashk.common.test.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Util {

	private Util() {}
	
	public static List<Integer> readIntegerLines(String inputFolder, String inputFile) {
		
		Stream<String> lines = null;
		List<Integer> input = null;
		
		try {
			Path path = Paths.get("src", "test", "resources", inputFolder, inputFile).toAbsolutePath();
			input = Files.lines(path).map(Integer::parseInt).collect(Collectors.toList());
			
		} catch (IOException e) {
			input = new ArrayList<>();
			e.printStackTrace();
		} finally {
			if(lines != null) {
				lines.close();
			}
		}
		
		return input;
	}
	
	public static List<String> readStringLines(String inputFolder, String inputFile) {
		
		Stream<String> lines = null;
		List<String> input = null;
		
		try {
			Path path = Paths.get("src", "test", "resources", inputFolder, inputFile).toAbsolutePath();
			input = Files.lines(path).collect(Collectors.toList());
			
		} catch (IOException e) {
			input = new ArrayList<>();
			e.printStackTrace();
		} finally {
			if(lines != null) {
				lines.close();
			}
		}
		
		return input;
	}

}
