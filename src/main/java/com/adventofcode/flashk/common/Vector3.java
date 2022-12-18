package com.adventofcode.flashk.common;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Vector3 {

	private int x;
	private int y;
	private int z;
	
	/**
	 * Accepts coordinates in String format as "x,y,z" and creates a Vector3 from it.
	 * <pre>
	 * Vector3 vector1 = new Vector2("4,2,1");
	 * Vector3 vector2 = new Vector2(4,2,1);
	 * 
	 * vector.equals(vector2) // true
	 * </pre>
	 * @param coordinates
	 * @return
	 */
	public Vector3(String coordinates) {
		
		String[] values = coordinates.split(",");
		
		this.x = Integer.parseInt(values[0]);
		this.y = Integer.parseInt(values[1]);
		this.z = Integer.parseInt(values[2]);
		
	}
}
