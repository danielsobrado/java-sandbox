package Queues;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import Queues.MyArrayQueue;
import Queues.MyStackQueue;

/**
 * 
 * Test cases for my implementation of Queue
 * - For the Array implementation
 * - For the Stack implementation
 * 
 * @author J. Daniel Sobrado
 * 
 */
public class TestMyQueue {

	@Test
	public void testEnqueueArray() {
		
	var queue = new MyArrayQueue(5);
		queue.enqueue(10);
		queue.enqueue(20);
		
		assertEquals(queue.getCount(), 2);
		
		queue.enqueue(30);
		queue.enqueue(40);
		queue.enqueue(50);

		assertFalse(queue.isEmpty());
		assertTrue(queue.isFull());
		assertEquals(queue.getCount(), 5);
		assertThrows(IllegalStateException.class, () -> queue.enqueue(60));
	}

	@Test
	public void testDequeueArray() {
		
		var queue = new MyArrayQueue(3);
		queue.enqueue(10);
		queue.enqueue(20);
		queue.enqueue(30);
		
		queue.dequeue();
		var value = queue.dequeue();
		assertEquals(value, 20);
		
		queue.dequeue();
		
		assertTrue(queue.isEmpty());
		assertThrows(IllegalStateException.class, () -> queue.dequeue());
		
		queue.enqueue(40);
		queue.enqueue(50);

		assertEquals(2, queue.getCount());
		assertEquals(50, queue.peek());
	}
	
	@Test
	public void testEnqueueStack() {
		
		var queue = new MyStackQueue<Integer>();
		queue.enqueue(10);
		queue.enqueue(20);
		
		assertEquals(queue.getCount(), 2);
		
		queue.enqueue(30);
		queue.enqueue(40);
		queue.enqueue(50);
		
		assertFalse(queue.isEmpty());
		assertEquals(queue.getCount(), 5);
		
		queue.enqueue(60);
		assertEquals(queue.getCount(), 6);
	}

	@Test
	public void testDequeueStack() {
		
		var queue = new MyStackQueue<Integer>();
		queue.enqueue(10);
		queue.enqueue(20);
		queue.enqueue(30);
		
		queue.dequeue();
		var value = queue.dequeue();
		assertEquals(value, 20);
		
		queue.dequeue();
		
		assertTrue(queue.isEmpty());
		assertThrows(IllegalStateException.class, () -> queue.dequeue());

		queue.enqueue(40);
		queue.enqueue(50);

		assertEquals(2, queue.getCount());
		assertEquals(50, queue.peek());
	}	

}
