package com.adventofcode.flashk.day07;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeviceSpace {

	private final static String CD_REGEX = "\\$ cd ([\\/.a-z]*)";
	private final static Pattern CD_PATTERN = Pattern.compile(CD_REGEX);
	
	private final static String LS_COMMAND = "$ ls";
	private final static String PARENT_DIR = "..";
	
	private final static int FILTER_FOLDER_MAX_SPACE = 100000;
	private final static long TOTAL_SPACE = 70000000;
	private final static long UPDATE_SIZE = 30000000;
	
	private File filesystem;
	private long occupiedSpace;
	private List<File> directories = new ArrayList<>();

	
	public DeviceSpace(List<String> inputs) {
		
		initializeFileTree(inputs);
		occupiedSpace = calculateSize(filesystem);
		
	}

	private void initializeFileTree(List<String> inputs) {
		Stack<File> currentDirectory = new Stack<>();
		
		for(String input : inputs) {
			
			Matcher matcher = CD_PATTERN.matcher(input);
			
			// cd <dirname>
			if(matcher.find()) {
			
				String name = matcher.group(1);
				
				if(filesystem == null) {
					
					// Change dir to root directory
					filesystem = new File(true, name, 0);
					currentDirectory.add(filesystem);
					directories.add(filesystem);
					
				} else if (!PARENT_DIR.equals(name)){
					
					// Change dir to any other directory
					File currentDir = currentDirectory.peek().getChild(name).get();
					currentDirectory.add(currentDir);
					directories.add(currentDir);
				
				} else {
					
					// Change dir to parent directory (..)
					currentDirectory.pop();
				}
				
			} else if(!LS_COMMAND.equals(input)) {
				
				// Add file or directory to current directory
				currentDirectory.peek().addChild(new File(input));
				
			}
			
		}
	}

	public long solveA() {
		
		AtomicLong totalSize = new AtomicLong(0);
		directories.stream()
					.filter(directory -> directory.getSize() <= FILTER_FOLDER_MAX_SPACE)
					.forEach(directory -> totalSize.getAndAdd(directory.getSize()));
		
		return totalSize.get();
		
	}
	
	public long solveB() {
		
		long unusedSpace = TOTAL_SPACE - occupiedSpace;
		long neededSpace = UPDATE_SIZE - unusedSpace;
		
		return directories.stream()
							.sorted()
							.filter(directory -> directory.getSize() > neededSpace)
							.findAny()
							.get()
							.getSize();

	}
	
	private long calculateSize(File currentFile) {
		
		// Base case: current file is a plain file (not directory)
		if(!currentFile.isDirectory()) {
			return currentFile.getSize();
		}
		
		long currentSize = currentFile.getSize();
		for(File childFile : currentFile.getChildFiles()) {
			currentSize += calculateSize(childFile);
			currentFile.setSize(currentSize);
		}

		return currentSize;
	}

}
