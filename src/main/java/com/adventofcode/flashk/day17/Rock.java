package com.adventofcode.flashk.day17;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.adventofcode.flashk.common.Collider2DL;
import com.adventofcode.flashk.common.Vector2L;

import lombok.Getter;
import lombok.Setter;

public abstract class Rock {

	public static final char CROSS = '+';
	public static final char HORIZONTAL_BAR = '-';
	public static final char VERTICAL_BAR = '|';
	public static final char SQUARE = 'o';
	public static final char CORNER = 'L';
	
	@Getter
	@Setter
	private boolean moving = true;
	
	@Getter
	protected Vector2L position;
	
	@Getter
	protected Set<Collider2DL> colliders = new HashSet<>();
    
	public Rock(Vector2L initialPosition) {
		position = initialPosition;
	}
	
	public void move(Vector2L direction) {
		
		// Only move one unit at once updating both rock position and colliders positions
		direction.normalize(); 
		position.transform(direction);
		
		// TODO algo está mal en esto con el cross acaba con unas coordenadas que no corresponden.
		colliders.stream().forEach(collider -> collider.transform(direction));
		
	}
	
	public boolean collidesWith(Collider2DL other) {
		
		Iterator<Collider2DL> colliderIterator = colliders.iterator();
		boolean collides = false;
		
		while(!collides && colliderIterator.hasNext()) {
			Collider2DL collider = colliderIterator.next();
			collides = collider.collidesWith(other);
		}
		
		return collides;
		
	}
	
	// Abstract methods
	public abstract long getMaxY();


}
