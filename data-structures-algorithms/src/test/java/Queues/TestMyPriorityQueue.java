package Queues;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import Queues.MyArrayQueue;
import Queues.MyStackQueue;

/**
 * 
 * Test cases for my implementation of Priority Queue
 * - For the Array implementation
 * 
 * @author J. Daniel Sobrado
 * 
 */
public class TestMyPriorityQueue {

	@Test
	public void testEnqueueArray() {
		
	var queue = new MyArrayPriorityQueue(10);
		queue.enqueue(10);
		queue.enqueue(20);
		
		assertEquals(2, queue.getCount());
		
		queue.enqueue(30);
		queue.enqueue(40);
		queue.enqueue(50);

		assertFalse(queue.isEmpty());
		assertEquals(5, queue.getCount());
		
		queue.enqueue(25);
		queue.enqueue(5);
		queue.enqueue(55);
		
		assertEquals(8, queue.getCount());
		assertEquals(5, queue.peek());
		
		Integer[] output = new Integer[] {5, 10, 20, 25, 30, 40, 50, 55, null, null};
		
		assertArrayEquals(output, queue.toArray());
	}

	@Test
	public void testDequeueArray() {
		
		var queue = new MyArrayPriorityQueue(3);
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
		assertEquals(40, queue.peek());
	}
	
}
