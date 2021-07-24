package Stacks;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * 
 * Test cases for Balanced Expressions
 * - Given an expression string exp, write a program to examine whether 
 * 		the pairs and the orders of “(“, “)”, “[“, “]” are correct.
 * 
 * @author J. Daniel Sobrado
 * 
 */
public class TestBalancedExpressions {

	@Test
	public void testBalancedExpressions() {
		
		assertTrue(BalancedExpressions.balancedExpressions(""));
		
		String input1 = "(";
		assertFalse(BalancedExpressions.balancedExpressions(input1));

		String input2 = "()";
		assertTrue(BalancedExpressions.balancedExpressions(input2));

		String input3 = ")";
		assertFalse(BalancedExpressions.balancedExpressions(input3));

		String input4 = "())";
		assertFalse(BalancedExpressions.balancedExpressions(input4));

		String input5 = "([])";
		assertTrue(BalancedExpressions.balancedExpressions(input5));

		String input6 = "([]";
		assertFalse(BalancedExpressions.balancedExpressions(input6));

		String input7 = "()[]";
		assertTrue(BalancedExpressions.balancedExpressions(input7));

		String input8 = "())[]";
		assertFalse(BalancedExpressions.balancedExpressions(input8));

		String input9 = "([][][])";
		assertTrue(BalancedExpressions.balancedExpressions(input9));

		String input10 = "([()][]())";
		assertTrue(BalancedExpressions.balancedExpressions(input10));

		String input11 = "[(])";
		assertFalse(BalancedExpressions.balancedExpressions(input11));
		
	}

}
