package Queues;

import java.util.ArrayDeque;
import java.util.Stack;
import java.util.Queue;

/**
 * 
 * Reverse a Queue using a Stack
 * 
 * @author J. Daniel Sobrado
 * 
 */
public class ReverseQueue {

	public static Queue<Integer> reverseQueue(Queue<Integer> queue) {

		if (queue.size() <= 1)
			return queue;

		var reversedQueue = new ArrayDeque<Integer>();

		var stack = new Stack<Integer>();

		while (!queue.isEmpty())
			stack.add(queue.remove());

		while (!stack.isEmpty())
			reversedQueue.add(stack.pop());

		return reversedQueue;
	}

}
