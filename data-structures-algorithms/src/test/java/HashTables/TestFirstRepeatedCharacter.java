package HashTables;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * 
 * Test cases for First Repeated Character
 * -  Identify the first character that is repeated in a String
 * 
 * @author J. Daniel Sobrado
 * 
 */
public class TestFirstRepeatedCharacter {

	@Test
	public void testNonRepeatedCharacter() {
		String input1 = "this is a long story";
		String input2 = "he is eating";
		String input3 = "zxyqandnotthat";
		
		assertEquals(new Character('t'), FirstRepeatedCharacter.getFirstRepeatedCharacter(input1));
		assertEquals(new Character('e'), FirstRepeatedCharacter.getFirstRepeatedCharacter(input2));
		assertEquals(new Character('a'), FirstRepeatedCharacter.getFirstRepeatedCharacter(input3));
		assertNull(FirstRepeatedCharacter.getFirstRepeatedCharacter(""));
		assertNull(FirstRepeatedCharacter.getFirstRepeatedCharacter("ab"));
		assertNull(FirstRepeatedCharacter.getFirstRepeatedCharacter(null));

	} 

}
