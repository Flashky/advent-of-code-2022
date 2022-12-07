package com.adventofcode.flashk.day07;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeviceSpace {

	private final static String CD_REGEX = "[$] (cd) ([\\/.a-z]*)";
	private final static Pattern CD_PATTERN = Pattern.compile(CD_REGEX);
	
	private final static String DIR_REGEX = "dir ([a-z]*)";
	private final static Pattern DIR_PATTERN = Pattern.compile(DIR_REGEX);
	
	private final static String FILE_REGEX = "^^([0-9]*) ([a-z]*.[a-z]*)";
	private final static Pattern FILE_PATTERN = Pattern.compile(FILE_REGEX);
	
	private final static String LS_COMMAND = "$ ls";
	private final static String PARENT_DIR = "..";
	
	private final static long TOTAL_SPACE = 70000000;
	private final static long MINIMUM_SPACE = 30000000;
	
	private File filesystem;
	private List<File> directories = new ArrayList<>();
	
	private long totalSpacePart1 = 0;
	
	public DeviceSpace(List<String> inputs) {
		
		Stack<File> currentDirectory = new Stack<>();
		
		for(String input : inputs) {
			
			Matcher matcher = CD_PATTERN.matcher(input);
			
			if(matcher.find()) {
			
				// cd command match
				String name = matcher.group(2);
				
				if(filesystem == null) {
					
					// Is root directory
					filesystem = new File("dir", name, 0);
					currentDirectory.add(filesystem);
					
				} else if (!PARENT_DIR.equals(name)){
					
					// Is any other directory - add folder to stack
					File currentDir = currentDirectory.peek().getChild(name).get();
					currentDirectory.add(currentDir);
					directories.add(currentDir);
				
				} else {
					
					// Returns to parent directory
					currentDirectory.pop();
				}
				
				
			} else if(!LS_COMMAND.equals(input)) {
				
				File currentDir = currentDirectory.peek();
				currentDir.addChild(getFile(input));
				
			}
			
		}

	}

	public long solveA() {
		calculateSize(filesystem);
		
		return this.totalSpacePart1;
	}
	
	public long solveB() {
		

		long unusedSpace = TOTAL_SPACE - calculateSize(filesystem);
		long neededSpace = MINIMUM_SPACE - unusedSpace;
	
		long currentMaxSpace = Long.MAX_VALUE;
		for(File directory : directories) {
			long directorySize = directory.getSize();
			if(directorySize < currentMaxSpace && directorySize > neededSpace) {
				currentMaxSpace = directorySize;
			}
		}
		
		return currentMaxSpace;
		/*
		return directories.stream()
							.sorted()
							.filter(directory -> directory.getSize() > neededSpace)
							.findAny()
							.get()
							.getSize();
							*/
	}
	
	private long calculateSize(File currentFile) {
		
		// Caso base, hemos llegado a un fichero
		if(currentFile.isFile()) {
			return currentFile.getSize();
		}
		
		long currentSize = currentFile.getSize();
		for(File childFile : currentFile.getChildFiles()) {
			currentSize += calculateSize(childFile);
			currentFile.setSize(currentSize);
		}
		
		if(currentSize <= 100000) {
			this.totalSpacePart1 += currentSize;
		}
		
		return currentSize;
	}

	private File getFile(String input) {

		Matcher directoryMatcher = DIR_PATTERN.matcher(input);
		Matcher fileMatcher = FILE_PATTERN.matcher(input);
		
		File file = null;
		if(directoryMatcher.find()) {
			file = new File("dir", directoryMatcher.group(1), 0);
		} else if(fileMatcher.find()) {
			long size = Long.parseLong(fileMatcher.group(1));
			String filename = fileMatcher.group(2);
			file = new File("file", filename, size);
		}
		return file;
	}
}
