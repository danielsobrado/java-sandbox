package Queues;

import java.util.Stack;

/**
 * 
 * Implement a Queue using two Stacks and Generics
 * 
 * @author J. Daniel Sobrado
 * 
 */
public class MyStackQueue<E> {
	
	private Stack<E> stack = new Stack<E>();
	private Stack<E> stackTemp = new Stack<E>();
	
	public MyStackQueue() {
		super();
	}
	
	public Integer getCount() {
		return this.stack.size() + this.stackTemp.size();
	}

	/**
	 * <p>
	 * Add a value at the end of the queue
	 * </p>
	 * 
	 * @param value to be stored
	 */
	public void enqueue(E value) {		
		stack.push(value);
	}

	/**
	 * <p>
	 * Return the first value in the queue
	 * </p>
	 * 
	 * @return first value in the queue
	 * @throws IllegalAccessException when the array is empty
	 */
	public E dequeue() throws IllegalStateException {

		if (this.isEmpty())
			throw new IllegalStateException();
		
		if (stackTemp.isEmpty()) {
			while(!stack.isEmpty()) {
				stackTemp.push(stack.pop());
			}
		} 		
		
		E value = stackTemp.pop();
				
		return value;
	}

	/**
	 * <p>
	 * View a value from the front of the queue
	 * </p>
	 * 
	 * @return value from the front of the queue
	 * @throws IllegalStateException when the queue is empty
	 */
	public E peek() {
		if (this.isEmpty()) {
			throw new IllegalStateException();
		}

		return stackTemp.peek();
	}

	/**
	 * <p>
	 * <p>Check if the queue is empty
	 * </p>
	 * 
	 * @return true if it is empty
	 */
	public boolean isEmpty() {
		return (stack.isEmpty() && stackTemp.isEmpty());
	}

}
