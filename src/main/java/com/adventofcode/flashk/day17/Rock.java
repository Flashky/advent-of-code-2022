package com.adventofcode.flashk.day17;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import com.adventofcode.flashk.common.Collider2D;
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
	protected Set<Collider2D> colliders = new HashSet<>();
    
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
	
	public boolean collidesWith(Collider2D other) {
		
		Iterator<Collider2D> colliderIterator = colliders.iterator();
		boolean collides = false;
		
		while(!collides && colliderIterator.hasNext()) {
			Collider2D collider = colliderIterator.next();
			collides = collider.collidesWith(other);
		}
		
		return collides;
		
	}
	
	// TODO no sé si necesito esto o si me vale con el método anterior
	public boolean collidesWith(Rock other) {
		
		// Two rocks collide if any of the colliders between two rocks collide between them
		
		Iterator<Collider2D> colliderIterator = colliders.iterator();
		boolean collides = false;
		
		while(!collides && colliderIterator.hasNext()) {
			
			Collider2D collider = colliderIterator.next();
			
			Optional<Collider2D> collidedLine = other.colliders.stream()
													.filter(anotherCollider ->  anotherCollider.collidesWith(collider))
													.findAny();
			
			collides = collidedLine.isPresent();
		}
		
		
		return collides;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(position);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rock other = (Rock) obj;
		return Objects.equals(position, other.position);
	}
	
	// Abstract methods
	public abstract long getMaxY();


}
