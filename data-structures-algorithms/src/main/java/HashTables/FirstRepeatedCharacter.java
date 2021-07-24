package HashTables;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * Identify the first character that is repeated in a String
 * 
 * @author J. Daniel Sobrado
 * 
 */
public class FirstRepeatedCharacter {

	public static Character getFirstRepeatedCharacter(String str) {
		
		if ((str == null) || (str.isEmpty())) return null;
		
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		
		for (Character character:str.toCharArray()) {
			if (map.containsKey(character)) map.put(character, map.get(character)+1);
			else map.put(character, 1);
		}
		
		for (Character character:str.toCharArray()) {
			if (map.get(character) > 1) return character;
		}
		
		return null;
	}

}
