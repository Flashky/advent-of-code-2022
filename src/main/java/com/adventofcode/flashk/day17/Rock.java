package com.adventofcode.flashk.day17;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.adventofcode.flashk.common.Collider2D;
import com.adventofcode.flashk.common.Vector2;

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
	protected Vector2 position;
	
	@Getter
	protected Set<Collider2D> colliders = new HashSet<>();
    
	public Rock(Vector2 initialPosition) {
		position = initialPosition;
	}
	
	public void move(Vector2 direction) {
		
		// Only move one unit at once updating both rock position and colliders positions
		direction.normalize(); 
		position.transform(direction);
		
		// TODO algo está mal en esto con el cross acaba con unas coordenadas que no corresponden.
		colliders.stream().forEach(collider -> collider.transform(direction));
		
	}
	
	public boolean collidesWith(Collider2D other) {
		
		Iterator<Collider2D> colliderIterator = colliders.iterator();
		boolean collides = false;
		
		while(!collides && colliderIterator.hasNext()) {
			Collider2D collider = colliderIterator.next();
			collides = collider.collidesWith(other);
		}
		
		return collides;
		
	}
	
	// Abstract methods
	public abstract int getMaxY();


}
