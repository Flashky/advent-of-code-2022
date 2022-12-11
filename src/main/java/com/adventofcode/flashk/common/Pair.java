package com.adventofcode.flashk.common;

import lombok.Getter;

@Getter
public class Pair<T, U> {

    private T first;
    private U second;
    
    public Pair(T first, U second) {
    	this.first = first;
    	this.second = second;
    }
}
