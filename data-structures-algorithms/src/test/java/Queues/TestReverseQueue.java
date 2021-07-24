package Queues;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayDeque;
import java.util.Queue;

import org.junit.jupiter.api.Test;

/**
 * 
 * Test cases for Reverse a Queue
 * 
 * @author J. Daniel Sobrado
 * 
 */
public class TestReverseQueue {

	@Test
	public void testReverseQueue() {
		ArrayDeque<Integer> queue = new ArrayDeque<>();
		queue.add(10);
		queue.add(20);
		queue.add(30);
		queue.add(40);
		
		Queue<Integer> reversedQueue = ReverseQueue.reverseQueue(queue);
		
		var intArray = new Integer[] {40, 30, 20, 10};
		
		assertArrayEquals(reversedQueue.toArray(), intArray);

	}

}
