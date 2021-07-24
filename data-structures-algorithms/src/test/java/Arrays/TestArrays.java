package Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * 
 * Test cases for my implementation of Array
 * 
 * @author J. Daniel Sobrado
 * 
 */
public class TestArrays {
	
	@Test
	public void testArray() {
		MyArray numbers = new MyArray(4);
		numbers.insert(10);
		numbers.insert(20);
		numbers.insert(30);
		numbers.insert(40);
		
		assertEquals(4, numbers.getSize());
		assertEquals(1, numbers.indexOf(20));
		assertEquals(3, numbers.indexOf(40));

		numbers.removeAt(2);
		assertEquals(2, numbers.indexOf(40));
	}
}
