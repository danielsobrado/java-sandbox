package Queues;

import java.util.Arrays;

/**
 * 
 * Implement a Priority Queue using an Array
 * 
 * @author J. Daniel Sobrado
 * @param <E>
 * 
 */
public class MyArrayPriorityQueue {
	
	private Integer[] myArray = null;

	private int counter;
	private int size;

	public MyArrayPriorityQueue(int size) {
		super();
		this.myArray = new Integer[size];
		this.counter = 0;
		this.size = size;
	}
	
	public Integer getCount() {
		return this.counter;
	}

	/**
	 * <p>
	 * Add a value at the end of the queue
	 * </p>
	 * 
	 * @param value to be stored
	 */
	public void enqueue(int value) {
		
		if (this.isFull()) throw new IllegalStateException();
		
		if (this.isEmpty()) {
			myArray[counter++] = value;
			return;
		}
		
		// Shift all values by one starting from the end
		for (int i=counter-1;i>=0;i--) {
			if (value >= myArray[i]) {
				myArray[i+1] = value;
				counter++;
				break;
			} else if ((i==0) && value <= myArray[0]) {
				myArray[1] = myArray[0]; 
				myArray[0] = value;
				counter++;
			} else {
				myArray[i+1] = myArray[i];
			}
		}

	}

	/**
	 * <p>
	 * Return the first integer in the queue
	 * </p>
	 * 
	 * @return first integer in the queue
	 * @throws IllegalAccessException when the array is empty
	 */
	public int dequeue() throws IllegalStateException {

		if (counter == 0)
			throw new IllegalStateException();

		int value = myArray[0];
		
		// Shift all values by one
		for (int i=0;i<size-1;i++) myArray[i] = myArray[i+1]; 
		
		myArray[size-1] = null;
		
		counter--;
		
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
	public Integer peek() {
		if (counter == 0) {
			throw new IllegalStateException();
		}

		return myArray[0];
	}

	/**
	 * <p>
	 * <p>Check if the queue is empty
	 * </p>
	 * 
	 * @return true if it is empty
	 */
	public boolean isEmpty() {
		return (counter == 0);
	}

	/**
	 * <p>
	 * <p>Check if the queue is full
	 * </p>
	 * 
	 * @return true if it is full
	 */
	public boolean isFull() {
		return (counter == size);
	}
	
	/**
	 * <p>Convert to Array
	 * </p>
	 * 
	 * For a generic implementation, take into account that:
	 * You can not create generic arrays in Java because compiler does not know exactly what E represents. 
	 * Creation of array of a non-reifiable type is not allowed in Java.
	 * 
	 * @return Array of Integers
	 */
	public Integer[] toArray() {
	    return myArray;
	}
	
	@Override
	public String toString() {
		return Arrays.toString(myArray);
	}
}
