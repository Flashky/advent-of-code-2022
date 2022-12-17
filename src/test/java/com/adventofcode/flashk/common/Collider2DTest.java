package com.adventofcode.flashk.common;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Collider2DTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testLine() {
		Vector2L start = new Vector2L();
		Vector2L end = new Vector2L(0,2);
		
		Collider2D line = new Collider2D(start, end);
		
		assertTrue(line.getStart().equals(start));
		assertTrue(line.getEnd().equals(end));
	}

	@Test
	void testIntersectsVerticalNonZero() {
		
		Vector2L start = new Vector2L(1,2);
		Vector2L end = new Vector2L(1,5);
		
		Collider2D line = new Collider2D(start, end);
		
		assertTrue(line.collidesWith(new Vector2L(1,2)));
		assertTrue(line.collidesWith(new Vector2L(1,3)));
		assertTrue(line.collidesWith(new Vector2L(1,4)));
		assertTrue(line.collidesWith(new Vector2L(1,5)));
		
		// False cases
		assertFalse(line.collidesWith(new Vector2L(1,1)));
		assertFalse(line.collidesWith(new Vector2L(1,6)));
		assertFalse(line.collidesWith(new Vector2L(2,2)));
		assertFalse(line.collidesWith(new Vector2L()));
		
	}
	
	@Test
	void testIntersectsVerticalStartAtZero() {
		
		Vector2L start = new Vector2L();
		Vector2L end = new Vector2L(0,3);
		
		Collider2D line = new Collider2D(start, end);
		
		assertTrue(line.collidesWith(new Vector2L(0,0)));
		assertTrue(line.collidesWith(new Vector2L(0,1)));
		assertTrue(line.collidesWith(new Vector2L(0,2)));
		assertTrue(line.collidesWith(new Vector2L(0,3)));
		
		// False cases
		assertFalse(line.collidesWith(new Vector2L(0,-1)));
		assertFalse(line.collidesWith(new Vector2L(0,4)));
		assertFalse(line.collidesWith(new Vector2L(1,1)));
		
	}
	
	@Test
	void testIntersectsVerticalEndsAtZero() {
		
		Vector2L start = new Vector2L(0,3);
		Vector2L end = new Vector2L();
		
		Collider2D line = new Collider2D(start, end);
		
		assertTrue(line.collidesWith(new Vector2L(0,0)));
		assertTrue(line.collidesWith(new Vector2L(0,1)));
		assertTrue(line.collidesWith(new Vector2L(0,2)));
		assertTrue(line.collidesWith(new Vector2L(0,3)));
		
		// False cases
		assertFalse(line.collidesWith(new Vector2L(0,-1)));
		assertFalse(line.collidesWith(new Vector2L(0,4)));
		assertFalse(line.collidesWith(new Vector2L(1,1)));
	}
	
	@Test
	void testIntersectsVerticalCrossesZero() {
		
		Vector2L start = new Vector2L(0,-2);
		Vector2L end = new Vector2L(0,2);
		
		Collider2D line = new Collider2D(start, end);
		
		assertTrue(line.collidesWith(new Vector2L(0,-2)));
		assertTrue(line.collidesWith(new Vector2L(0,-1)));
		assertTrue(line.collidesWith(new Vector2L(0,0)));
		assertTrue(line.collidesWith(new Vector2L(0,1)));
		assertTrue(line.collidesWith(new Vector2L(0,2)));
		
		// False cases
		assertFalse(line.collidesWith(new Vector2L(0,3)));
		assertFalse(line.collidesWith(new Vector2L(0,-3)));
		assertFalse(line.collidesWith(new Vector2L(1,1)));
	}
	
	@Test
	void testIntersectsHorizontalNonZero() {
		
		Vector2L start = new Vector2L(2,1);
		Vector2L end = new Vector2L(5,1);
		
		Collider2D line = new Collider2D(start, end);
		
		assertTrue(line.collidesWith(new Vector2L(2,1)));
		assertTrue(line.collidesWith(new Vector2L(3,1)));
		assertTrue(line.collidesWith(new Vector2L(4,1)));
		assertTrue(line.collidesWith(new Vector2L(5,1)));
		
		// False cases
		assertFalse(line.collidesWith(new Vector2L(1,1)));
		assertFalse(line.collidesWith(new Vector2L(6,1)));
		assertFalse(line.collidesWith(new Vector2L(2,2)));
		assertFalse(line.collidesWith(new Vector2L()));
		
	}
	
	@Test
	void testIntersectsHorziontalStartAtZero() {
		
		Vector2L start = new Vector2L();
		Vector2L end = new Vector2L(3,0);
		Collider2D line = new Collider2D(start, end);
		
		assertTrue(line.collidesWith(new Vector2L(0,0)));
		assertTrue(line.collidesWith(new Vector2L(1,0)));
		assertTrue(line.collidesWith(new Vector2L(2,0)));
		assertTrue(line.collidesWith(new Vector2L(3,0)));
		
		// False cases
		assertFalse(line.collidesWith(new Vector2L(-1,0)));
		assertFalse(line.collidesWith(new Vector2L(4,0)));
		assertFalse(line.collidesWith(new Vector2L(1,1)));
	}
	
	@Test
	void testIntersectsStartsEndsAtZero() {
		
		Vector2L start = new Vector2L(3,0);
		Vector2L end = new Vector2L();
		
		Collider2D line = new Collider2D(start, end);
		
		assertTrue(line.collidesWith(new Vector2L(0,0)));
		assertTrue(line.collidesWith(new Vector2L(1,0)));
		assertTrue(line.collidesWith(new Vector2L(2,0)));
		assertTrue(line.collidesWith(new Vector2L(3,0)));
		
		// False cases
		assertFalse(line.collidesWith(new Vector2L(-1,0)));
		assertFalse(line.collidesWith(new Vector2L(4,0)));
		assertFalse(line.collidesWith(new Vector2L(1,1)));
	}
	
	@Test
	void testIntersectsHorizontalCrossesZero() {
		
		Vector2L start = new Vector2L(-2,0);
		Vector2L end = new Vector2L(2,0);
		
		Collider2D line = new Collider2D(start, end);
		
		assertTrue(line.collidesWith(new Vector2L(-2,0)));
		assertTrue(line.collidesWith(new Vector2L(-1,0)));
		assertTrue(line.collidesWith(new Vector2L(0,0)));
		assertTrue(line.collidesWith(new Vector2L(1,0)));
		assertTrue(line.collidesWith(new Vector2L(2,0)));
		
		// False cases
		assertFalse(line.collidesWith(new Vector2L(3,0)));
		assertFalse(line.collidesWith(new Vector2L(-3,0)));
		assertFalse(line.collidesWith(new Vector2L(1,1)));
	}
	
	@Test
	void testCollidesNoHorizontalCollide() {
	
		Vector2L start = new Vector2L(2,0);
		Vector2L end = new Vector2L(4,0);
		Collider2D collider = new Collider2D(start,end);
		
		// Horizontal collider at the left
		start = new Vector2L();
		end = new Vector2L(1,0);
		Collider2D otherCollider = new Collider2D(start, end);
		
		assertFalse(collider.collidesWith(otherCollider));
		
		// Horizontal collider at the right
		start = new Vector2L(5,0);
		end = new Vector2L(6,0);
		otherCollider = new Collider2D(start, end);
		
		assertFalse(collider.collidesWith(otherCollider));
		
		// Parallel collider on top of current collider
		start = new Vector2L(2,1);
		end = new Vector2L(4,1);
		otherCollider = new Collider2D(start,end);
		
		assertFalse(collider.collidesWith(otherCollider));
		
		// Parallel collider on bottom of current collider
		start = new Vector2L(2,-1);
		end = new Vector2L(4,-1);
		otherCollider = new Collider2D(start,end);
		
		assertFalse(collider.collidesWith(otherCollider));
	}

	@Test
	void testCollidesNoVerticalCollide() {
	
		// Collider
		Vector2L start = new Vector2L(0,2);
		Vector2L end = new Vector2L(0,4);
		Collider2D collider = new Collider2D(start,end);
		
		// Horizontal collider at the left
		start = new Vector2L();
		end = new Vector2L(0,1);
		Collider2D otherCollider = new Collider2D(start, end);
		
		assertFalse(collider.collidesWith(otherCollider));
		
		// Horizontal collider at the right
		start = new Vector2L(0,5);
		end = new Vector2L(0,6);
		otherCollider = new Collider2D(start, end);
		
		assertFalse(collider.collidesWith(otherCollider));
		
		// Parallel collider on top of current collider
		start = new Vector2L(1,2);
		end = new Vector2L(1,4);
		otherCollider = new Collider2D(start,end);
		
		assertFalse(collider.collidesWith(otherCollider));
		
		// Parallel collider on bottom of current collider
		start = new Vector2L(-1,2);
		end = new Vector2L(-1,4);
		otherCollider = new Collider2D(start,end);
		
		assertFalse(collider.collidesWith(otherCollider));
	}
	
	@Test
	void testCollidesHorizontalWithHorizontal() {
		
		// Collider
		Vector2L start = new Vector2L(2,0);
		Vector2L end = new Vector2L(4,0);
		Collider2D collider = new Collider2D(start,end);
		
		// Collides with a collider at its left
		start = new Vector2L(-1,0);
		end = new Vector2L(3,0);
		Collider2D otherCollider = new Collider2D(start, end);
		
		assertTrue(collider.collidesWith(otherCollider));
		
		// Collides with a collider at its right
		start = new Vector2L(3,0);
		end = new Vector2L(5,0);
		otherCollider = new Collider2D(start, end);
		
		assertTrue(collider.collidesWith(otherCollider));
		
		// Order does not matter
		start = new Vector2L(4,0);
		end = new Vector2L(2,0);
		collider = new Collider2D(start,end);
		
		// Collides with a collider at its left
		start = new Vector2L(-1,0);
		end = new Vector2L(3,0);
		otherCollider = new Collider2D(start, end);
		
		assertTrue(collider.collidesWith(otherCollider));
		
		
	}
}
