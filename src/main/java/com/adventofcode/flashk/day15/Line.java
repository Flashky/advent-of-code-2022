package com.adventofcode.flashk.day15;

import java.util.Optional;

import com.adventofcode.flashk.common.Vector2;

public class Line {

	private Vector2 p;
	private Vector2 q;
	private int m; // Pendiente de la recta
	private int b; // Intersección con y
	
	public Line(Vector2 p, Vector2 q) {
		this.p = p;
		this.q = q;
		this.m = q.getY() - p.getY() / q.getX() - p.getX();
		this.b = p.getY() - m * p.getX();
	}
	
	public Optional<Vector2> intersect(Line line) {
		
		int divisor = (line.m - this.m);
		
		if(divisor == 0) {
			return Optional.empty();
		}
		
		double x = (line.b - this.b) / divisor;
		double y = this.m * x + this.b;
		
		// Si las rectas tienen la misma pendiente, son paralelas y no hay intersección
		if(this.m == line.m) {
			return Optional.empty();
		}
		
		return Optional.of(new Vector2((int) x, (int) y));
	}
	
	/*
	public Optional<Vector2> intersect(Line line){
		
		if
	}*/
}
