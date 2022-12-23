package com.adventofcode.flashk.common;

import java.util.Objects;

import lombok.Getter;

@Getter
public class Collider2DL {

	// Reference for ideas: https://stackoverflow.com/questions/907390/how-can-i-tell-if-a-point-belongs-to-a-certain-line

	private Vector2L start;
	private Vector2L end;
	
	private long minX;
	private long maxX;
	private long minY;
	private long maxY;
	
	public Collider2DL(Vector2L start, Vector2L end) {
		this.start = new Vector2L(start);
		this.end = new Vector2L(end);
	
		calculateMinAndMax(start, end);
	}
	
	public Collider2DL(Vector2L pointCollider) {
		this.start = new Vector2L(pointCollider);
		this.end = start;
		minX = pointCollider.getX();
		maxX = pointCollider.getX();
		minY = pointCollider.getY();
		maxY = pointCollider.getY();
	}
	
	
	public boolean collidesWith(Vector2L point) {
		
		if(point.equals(start) || point.equals(end)) {
			return true;
		}
		
		// Reference for ideas: https://stackoverflow.com/questions/907390/how-can-i-tell-if-a-point-belongs-to-a-certain-line
		
		// check from line segment start perspective
		double reference = Math.atan2(start.getY() - end.getY(), start.getX() - end.getX());
		double aTanTest = Math.atan2(start.getY() - point.getY(), start.getX() - point.getX());
		
		// check from line segment end perspective
		if(reference == aTanTest) {
			reference = Math.atan2(end.getY() - start.getY(), end.getX() - start.getX());
			aTanTest = Math.atan2(end.getY()- point.getY(), end.getX() - point.getX());
		}
		
		// Tested cases at junit: vertical and horizontal
		// Non-tested cases: diagonals
		return reference == aTanTest;
	}
	
	public void transform(Vector2L vector) {
		start.transform(vector);
		end.transform(vector);
		calculateMinAndMax(start, end);
	}

	public boolean collidesWith(Collider2DL other) {
		 return this.minX <= other.maxX && this.maxX >= other.minX && this.minY <= other.maxY && this.maxY >= other.minY;
	}

	private void calculateMinAndMax(Vector2L start, Vector2L end) {
		this.minX = Math.min(start.getX(), end.getX());
		this.maxX = Math.max(start.getX(), end.getX());
		this.minY = Math.min(start.getY(), end.getY());
		this.maxY = Math.max(start.getY(), end.getY());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(maxX, maxY, minX, minY);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Collider2DL other = (Collider2DL) obj;
		return maxX == other.maxX && maxY == other.maxY && minX == other.minX && minY == other.minY;
	}
}
