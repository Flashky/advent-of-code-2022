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
		Vector2 start = new Vector2();
		Vector2 end = new Vector2(0,2);
		
		Collider2D line = new Collider2D(start, end);
		
		assertTrue(line.getStart().equals(start));
		assertTrue(line.getEnd().equals(end));
	}

	@Test
	void testIntersectsVerticalNonZero() {
		
		Vector2 start = new Vector2(1,2);
		Vector2 end = new Vector2(1,5);
		
		Collider2D line = new Collider2D(start, end);
		
		assertTrue(line.intersects(new Vector2(1,2)));
		assertTrue(line.intersects(new Vector2(1,3)));
		assertTrue(line.intersects(new Vector2(1,4)));
		assertTrue(line.intersects(new Vector2(1,5)));
		
		// False cases
		assertFalse(line.intersects(new Vector2(1,1)));
		assertFalse(line.intersects(new Vector2(1,6)));
		assertFalse(line.intersects(new Vector2(2,2)));
		assertFalse(line.intersects(new Vector2()));
		
	}
	
	@Test
	void testIntersectsVerticalStartAtZero() {
		
		Vector2 start = new Vector2();
		Vector2 end = new Vector2(0,3);
		
		Collider2D line = new Collider2D(start, end);
		
		assertTrue(line.intersects(new Vector2(0,0)));
		assertTrue(line.intersects(new Vector2(0,1)));
		assertTrue(line.intersects(new Vector2(0,2)));
		assertTrue(line.intersects(new Vector2(0,3)));
		
		// False cases
		assertFalse(line.intersects(new Vector2(0,-1)));
		assertFalse(line.intersects(new Vector2(0,4)));
		assertFalse(line.intersects(new Vector2(1,1)));
		
	}
	
	@Test
	void testIntersectsVerticalEndsAtZero() {
		
		Vector2 start = new Vector2(0,3);
		Vector2 end = new Vector2();
		
		Collider2D line = new Collider2D(start, end);
		
		assertTrue(line.intersects(new Vector2(0,0)));
		assertTrue(line.intersects(new Vector2(0,1)));
		assertTrue(line.intersects(new Vector2(0,2)));
		assertTrue(line.intersects(new Vector2(0,3)));
		
		// False cases
		assertFalse(line.intersects(new Vector2(0,-1)));
		assertFalse(line.intersects(new Vector2(0,4)));
		assertFalse(line.intersects(new Vector2(1,1)));
	}
	
	@Test
	void testIntersectsVerticalCrossesZero() {
		
		Vector2 start = new Vector2(0,-2);
		Vector2 end = new Vector2(0,2);
		
		Collider2D line = new Collider2D(start, end);
		
		assertTrue(line.intersects(new Vector2(0,-2)));
		assertTrue(line.intersects(new Vector2(0,-1)));
		assertTrue(line.intersects(new Vector2(0,0)));
		assertTrue(line.intersects(new Vector2(0,1)));
		assertTrue(line.intersects(new Vector2(0,2)));
		
		// False cases
		assertFalse(line.intersects(new Vector2(0,3)));
		assertFalse(line.intersects(new Vector2(0,-3)));
		assertFalse(line.intersects(new Vector2(1,1)));
	}
	
	@Test
	void testIntersectsHorizontalNonZero() {
		
		Vector2 start = new Vector2(2,1);
		Vector2 end = new Vector2(5,1);
		
		Collider2D line = new Collider2D(start, end);
		
		assertTrue(line.intersects(new Vector2(2,1)));
		assertTrue(line.intersects(new Vector2(3,1)));
		assertTrue(line.intersects(new Vector2(4,1)));
		assertTrue(line.intersects(new Vector2(5,1)));
		
		// False cases
		assertFalse(line.intersects(new Vector2(1,1)));
		assertFalse(line.intersects(new Vector2(6,1)));
		assertFalse(line.intersects(new Vector2(2,2)));
		assertFalse(line.intersects(new Vector2()));
		
	}
	
	@Test
	void testIntersectsHorziontalStartAtZero() {
		
		Vector2 start = new Vector2();
		Vector2 end = new Vector2(3,0);
		Collider2D line = new Collider2D(start, end);
		
		assertTrue(line.intersects(new Vector2(0,0)));
		assertTrue(line.intersects(new Vector2(1,0)));
		assertTrue(line.intersects(new Vector2(2,0)));
		assertTrue(line.intersects(new Vector2(3,0)));
		
		// False cases
		assertFalse(line.intersects(new Vector2(-1,0)));
		assertFalse(line.intersects(new Vector2(4,0)));
		assertFalse(line.intersects(new Vector2(1,1)));
	}
	
	@Test
	void testIntersectsStartsEndsAtZero() {
		
		Vector2 start = new Vector2(3,0);
		Vector2 end = new Vector2();
		
		Collider2D line = new Collider2D(start, end);
		
		assertTrue(line.intersects(new Vector2(0,0)));
		assertTrue(line.intersects(new Vector2(1,0)));
		assertTrue(line.intersects(new Vector2(2,0)));
		assertTrue(line.intersects(new Vector2(3,0)));
		
		// False cases
		assertFalse(line.intersects(new Vector2(-1,0)));
		assertFalse(line.intersects(new Vector2(4,0)));
		assertFalse(line.intersects(new Vector2(1,1)));
	}
	
	@Test
	void testIntersectsHorizontalCrossesZero() {
		
		Vector2 start = new Vector2(-2,0);
		Vector2 end = new Vector2(2,0);
		
		Collider2D line = new Collider2D(start, end);
		
		assertTrue(line.intersects(new Vector2(-2,0)));
		assertTrue(line.intersects(new Vector2(-1,0)));
		assertTrue(line.intersects(new Vector2(0,0)));
		assertTrue(line.intersects(new Vector2(1,0)));
		assertTrue(line.intersects(new Vector2(2,0)));
		
		// False cases
		assertFalse(line.intersects(new Vector2(3,0)));
		assertFalse(line.intersects(new Vector2(-3,0)));
		assertFalse(line.intersects(new Vector2(1,1)));
	}
	
	@Test
	void testCollidesNoHorizontalCollide() {
	
		Vector2 start = new Vector2(2,0);
		Vector2 end = new Vector2(4,0);
		Collider2D collider = new Collider2D(start,end);
		
		// Horizontal collider at the left
		start = new Vector2();
		end = new Vector2(1,0);
		Collider2D otherCollider = new Collider2D(start, end);
		
		assertFalse(collider.collidesWith(otherCollider));
		
		// Horizontal collider at the right
		start = new Vector2(5,0);
		end = new Vector2(6,0);
		otherCollider = new Collider2D(start, end);
		
		assertFalse(collider.collidesWith(otherCollider));
		
		// Parallel collider on top of current collider
		start = new Vector2(2,1);
		end = new Vector2(4,1);
		otherCollider = new Collider2D(start,end);
		
		assertFalse(collider.collidesWith(otherCollider));
		
		// Parallel collider on bottom of current collider
		start = new Vector2(2,-1);
		end = new Vector2(4,-1);
		otherCollider = new Collider2D(start,end);
		
		assertFalse(collider.collidesWith(otherCollider));
	}

	@Test
	void testCollidesNoVerticalCollide() {
	
		// Collider
		Vector2 start = new Vector2(0,2);
		Vector2 end = new Vector2(0,4);
		Collider2D collider = new Collider2D(start,end);
		
		// Horizontal collider at the left
		start = new Vector2();
		end = new Vector2(0,1);
		Collider2D otherCollider = new Collider2D(start, end);
		
		assertFalse(collider.collidesWith(otherCollider));
		
		// Horizontal collider at the right
		start = new Vector2(0,5);
		end = new Vector2(0,6);
		otherCollider = new Collider2D(start, end);
		
		assertFalse(collider.collidesWith(otherCollider));
		
		// Parallel collider on top of current collider
		start = new Vector2(1,2);
		end = new Vector2(1,4);
		otherCollider = new Collider2D(start,end);
		
		assertFalse(collider.collidesWith(otherCollider));
		
		// Parallel collider on bottom of current collider
		start = new Vector2(-1,2);
		end = new Vector2(-1,4);
		otherCollider = new Collider2D(start,end);
		
		assertFalse(collider.collidesWith(otherCollider));
	}
	
	@Test
	void testCollidesHorizontalWithHorizontal() {
		
		// Collider
		Vector2 start = new Vector2(2,0);
		Vector2 end = new Vector2(4,0);
		Collider2D collider = new Collider2D(start,end);
		
		// Collides with a collider at its left
		start = new Vector2(-1,0);
		end = new Vector2(3,0);
		Collider2D otherCollider = new Collider2D(start, end);
		
		assertTrue(collider.collidesWith(otherCollider));
		
		// Collides with a collider at its right
		start = new Vector2(3,0);
		end = new Vector2(5,0);
		otherCollider = new Collider2D(start, end);
		
		assertTrue(collider.collidesWith(otherCollider));
		
		// Order does not matter
		start = new Vector2(4,0);
		end = new Vector2(2,0);
		collider = new Collider2D(start,end);
		
		// Collides with a collider at its left
		start = new Vector2(-1,0);
		end = new Vector2(3,0);
		otherCollider = new Collider2D(start, end);
		
		assertTrue(collider.collidesWith(otherCollider));
		
		
	}
}
