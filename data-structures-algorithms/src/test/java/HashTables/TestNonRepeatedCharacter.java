package HashTables;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * 
 * Test cases for Non Repeated Character
 * -  Identify the first character that is not repeated in a String
 * 
 * @author J. Daniel Sobrado
 * 
 */
public class TestNonRepeatedCharacter {

	@Test
	public void testNonRepeatedCharacter() {
		String input1 = "this is a long story";
		String input2 = "the heating app";
		String input3 = "this and not that";
		
		assertEquals(new Character('h'), NonRepeatedCharacter.getNonRepeatedCharacter(input1));
		assertEquals(new Character('i'), NonRepeatedCharacter.getNonRepeatedCharacter(input2));
		assertEquals(new Character('i'), NonRepeatedCharacter.getNonRepeatedCharacter(input3));
		assertNull(NonRepeatedCharacter.getNonRepeatedCharacter(""));
		assertNull(NonRepeatedCharacter.getNonRepeatedCharacter("aa"));
		assertNull(NonRepeatedCharacter.getNonRepeatedCharacter(null));
	} 

}
