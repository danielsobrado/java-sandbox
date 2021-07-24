package Stacks;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * 
 * Test cases for Reverse String using a Stack
 * 
 * @author J. Daniel Sobrado
 * 
 */
public class TestReverseString {

	@Test
	public void testReverseString() {
		
		String input1 = "abc";
		String output1 = "cba";
		
		assertTrue(ReverseString.reverseString(input1).equals(output1));
		
		String input2 = "daniel";
		String output2 = "leinad";
		
		assertTrue(ReverseString.reverseString(input2).equals(output2));
		assertTrue(ReverseString.reverseString("").equals(""));
		assertThrows(IllegalArgumentException.class, () -> ReverseString.reverseString(null));

	}

}
