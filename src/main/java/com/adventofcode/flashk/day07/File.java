package com.adventofcode.flashk.day07;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class File {

	private String type; // file/dir
	private String name;
	private long size = 0;
	private List<File> childFiles;
	
	public File(String type, String name, long size) {
		this.type = type;
		this.name = name;
		this.size = size;
		
		if(type == "dir") {
			childFiles = new ArrayList<>();
		}
	}

	public void addChild(File file) {
		
		if(this.type != "dir") {
			throw new IllegalArgumentException("Current file is not a directory");
		}
		
		this.childFiles.add(file);
		
	}
	
	public Optional<File> getChild(String name) {
		return childFiles.stream().filter(file -> file.name.equals(name)).findAny();
	}
	
	public boolean isFile() {
		return "file".equals(type);
	}
}
