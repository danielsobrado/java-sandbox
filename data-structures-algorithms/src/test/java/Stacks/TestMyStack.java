package Stacks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * 
 * Test cases for my implementation of Stack
 * - For the Array implementation
 * - For the ArrayList implementation
 * 
 * @author J. Daniel Sobrado
 * 
 */
public class TestMyStack {

	@Test
	public void testPushArray() {
		
		MyArrayStack stack = new MyArrayStack(5);
		stack.push(10);
		stack.push(20);
		
		assertEquals(stack.getCount(), 2);
		
		stack.push(30);
		stack.push(40);
		stack.push(50);
		
		assertFalse(stack.isEmpty());
		assertEquals(stack.getCount(), 5);
		assertThrows(StackOverflowError.class, () -> stack.push(60));
	}

	@Test
	public void testPopArray() {
		
		MyArrayStack stack = new MyArrayStack(3);
		stack.push(10);
		stack.push(20);
		stack.push(30);
		
		stack.pop();
		var value = stack.pop();
		assertEquals(value, 20);
		
		stack.pop();
		
		assertTrue(stack.isEmpty());
		assertThrows(IllegalStateException.class, () -> stack.pop());

	}
	
	@Test
	public void testPushList() {
		
		var stack = new MyListStack<Integer>();
		stack.push(10);
		stack.push(20);
		
		assertEquals(stack.getCount(), 2);
		
		stack.push(30);
		stack.push(40);
		stack.push(50);
		
		assertFalse(stack.isEmpty());
		assertEquals(stack.getCount(), 5);
		
		stack.push(60);
		assertEquals(stack.getCount(), 6);
	}

	@Test
	public void testPopList() {
		
		var stack = new MyListStack<Integer>();
		stack.push(10);
		stack.push(20);
		stack.push(30);
		
		stack.pop();
		var value = stack.pop();
		assertEquals(value, 20);
		
		stack.pop();
		
		assertTrue(stack.isEmpty());
		assertThrows(IllegalStateException.class, () -> stack.pop());

	}	

}
