package Stacks;

import java.util.Stack;

/**
 * 
 * Reverse a String using a Stack
 * 
 * @author J. Daniel Sobrado
 * 
 */
public class ReverseString {

	public static String reverseString(String str) {
		
		if (str == null) throw new IllegalArgumentException();
		if (str.isEmpty()) return "";
		
		Stack<Character> stackChars = new Stack<>();
		
		var chars = str.toCharArray();
		for (char character:chars) 
			stackChars.push(character);
		
		var result = new StringBuffer();
		while(!stackChars.empty()) 
			result.append(stackChars.pop());
		
		return result.toString();
	}
		
}
