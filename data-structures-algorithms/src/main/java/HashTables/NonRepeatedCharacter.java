package HashTables;

import java.util.HashMap;

/**
 * 
 * Identify the first character that is not repeated in a String
 * 
 * @author J. Daniel Sobrado
 * 
 */
public class NonRepeatedCharacter {

	public static Character getNonRepeatedCharacter(String str) {
		
		if ((str == null) || (str.isEmpty())) return null;
		
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		
		for (char character:str.toCharArray()) {
			if (map.containsKey(character)) map.put(character, map.get(character)+1);
			else map.put(character, 1);
		}
		
		for (char character:str.toCharArray()) {
			if (map.get(character) == 1) return character;
		}
		
		return null;
	}
}
