package Queues;

/**
 * 
 * Implement a Queue using an Array
 * 
 * @author J. Daniel Sobrado
 * 
 */
public class MyArrayQueue {

	private Integer[] myArray = null;

	private int counter;
	private int size;

	public MyArrayQueue(int size) {
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
		
		myArray[counter++] = value;
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

		if (counter-- == 0)
			throw new IllegalStateException();

		int value = myArray[0];
		
		// Shift all values by one
		for (int i=0;i<size-1;i++) myArray[i] = myArray[i+1]; 
		
		myArray[size-1] = null;
		
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


}
