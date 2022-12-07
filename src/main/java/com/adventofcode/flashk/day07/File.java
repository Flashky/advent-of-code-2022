package com.adventofcode.flashk.day07;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class File implements Comparable<File> {

	private final static String DIR_REGEX = "dir ([a-z]*)";
	private final static Pattern DIR_PATTERN = Pattern.compile(DIR_REGEX);
	
	private final static String FILE_REGEX = "^([0-9]*) ([a-z]*.[a-z]*)";
	private final static Pattern FILE_PATTERN = Pattern.compile(FILE_REGEX);
	
	private boolean isDirectory;
	private String name;
	
	@Setter
	private long size = 0;
	private List<File> childFiles;
	
	public File(boolean isDirectory, String name, long size) {
		this.isDirectory = isDirectory;
		this.name = name;
		this.size = size;
		
		if(this.isDirectory) {
			childFiles = new ArrayList<>();
		}
	}
	
	public File(String input) {
		
		Matcher directoryMatcher = DIR_PATTERN.matcher(input);
		Matcher fileMatcher = FILE_PATTERN.matcher(input);
		
		if(directoryMatcher.find()) {
			isDirectory = true;
			name = directoryMatcher.group(1);
			size = 0;
			childFiles = new ArrayList<>();
		} else if(fileMatcher.find()) {
			isDirectory = false;
			name = fileMatcher.group(2);
			size = Long.parseLong(fileMatcher.group(1));
		}
		
	}

	public void addChild(File file) {
		
		if(!isDirectory) {
			throw new IllegalArgumentException("Current file is not a directory");
		}
		
		this.childFiles.add(file);
		
	}
	
	public Optional<File> getChild(String name) {
		return childFiles.stream().filter(file -> file.name.equals(name)).findAny();
	}

	@Override
	public int compareTo(File otherFile) {
		return Long.compare(this.size, otherFile.size);
	}

}
