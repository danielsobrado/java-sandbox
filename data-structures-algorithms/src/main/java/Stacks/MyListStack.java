package Stacks;

import java.util.ArrayList;

/**
 * 
 * Implement a Stack using a List using Generics
 * 
 * @author J. Daniel Sobrado
 * 
 */
public class MyListStack<E> {

	private ArrayList<E> myList = null;
	
	private Integer counter;
	
	public MyListStack() {
		super();
		this.myList = new ArrayList<E>();
		this.counter = 0;
	}
	
	public Integer getCount() {
		return this.counter;		
	}
	
	/**
	 * <p>Add a value at the end of the stack
	 * </p>
	 * 
	 * @param value to be stored
	*/
	public void push(E value) {
		
		myList.add(counter, value);
		counter++;
	}

	/**
	 * <p>Get a value from the top of the stack
	 * </p>
	 * 
	 * @return value from the top of the stack
	*/
	public E pop() {
		
		E result = this.peek();

		myList.remove(counter-1);
		counter--;
		
		return result;
	}
	
	/**
	 * <p>View a value from the top of the stack
	 * </p>
	 * 
	 * @return value from the top of the stack
	 * @throws IllegalStateException when the stack is empty
	*/
	public E peek() {
		if (counter == 0) {
			throw new IllegalStateException();
		}
		
		E result = myList.get(counter-1);
		
		return result;
	}

	/**
	 * <p>Check if the stack is empty
	 * </p>
	 * @return true if it is empty  
	*/
	public boolean isEmpty() {
		return (counter == 0);
	}

}
