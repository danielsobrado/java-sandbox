package Stacks;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

/**
 * 
 *  Given an expression string exp, write a program to examine whether 
 * 		the pairs and the orders of “(“, “)”, “[“, “]” are correct.
 * 
 * @author J. Daniel Sobrado
 * 
 */
public class BalancedExpressions {

	public static boolean balancedExpressions(String str) {
		
		List<Character> open = Arrays.asList('(', '[');
		List<Character> close = Arrays.asList(')', ']');
		
		if (str.isEmpty()) return true;
		
		var stack = new Stack<Character>();
		
		try {
			Character last = null;
			for (Character character: str.toCharArray()) {
				if (open.contains(character)) {
					stack.push(character);
				}
				else if (close.contains(character)) {
					last = stack.pop();
					// Use index of the list to match open and close for the same type
					if (((open.indexOf(last)) != close.indexOf(character)) && (open.contains(last))) {
						return false;
					}
				}
			}
		} catch (EmptyStackException ex) {
			return false;
		}
		
		if (stack.size()+stack.size() > 0) return false;
		return true;
	}
		
}
