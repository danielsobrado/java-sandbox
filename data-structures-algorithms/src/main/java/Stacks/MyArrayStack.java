package Stacks;

/**
 * 
 * Implement a Stack using an Array
 * 
 * @author J. Daniel Sobrado
 * 
 */
public class MyArrayStack {

	private Integer[] myArray = null;
	
	private Integer counter;
	
	public MyArrayStack(Integer size) {
		super();
		this.myArray = new Integer[size];
		this.counter = 0;
	}
	
	public Integer getCount() {
		return this.counter;		
	}
	
	/**
	 * <p>Add a value at the end of the stack
	 * </p>
	 * 	Error if it is full
	 * 
	 * @param value to be stored
	 * @throws StackOverflowError when the stack is full
	*/
	public void push(Integer value) {
		
		if (counter == myArray.length) {
			throw new StackOverflowError();
		}
		
		myArray[counter] = value;
		counter++;
	}

	/**
	 * <p>Get a value from the top of the stack
	 * </p>
	 * 
	 * @return value from the top of the stack
	*/
	public Integer pop() {
		
		int result = this.peek();

		myArray[counter-1] = null;
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
	public Integer peek() {
		if (counter == 0) {
			throw new IllegalStateException();
		}
		
		int result = myArray[counter-1];
		
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
