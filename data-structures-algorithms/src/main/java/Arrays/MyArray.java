package Arrays;
import java.util.Arrays;

/**
 * 
 * Implement Array functions
 * - Only on Integers
 * - Keep track of the size in the class itself
 * 
 * @author J. Daniel Sobrado
 * 
 */
public class MyArray {
	
	private Integer[] array;
	private int size;

	public MyArray(int i) {
		this.array = new Integer[i];
		this.size = i;
	}

	/**
	 * <p>Insert an integer at an index location 
	 * </p>
	 * @param i index to insert
	 * @return insert on the MyArray itself
	*/
	public void insert(int i) {
		for (int a=0;a<array.length;a++) {
			if (array[a]==null) {
				array[a] = i;
				return;
			}
		}
		this.size = this.size++;
		Integer[] newArray = Arrays.copyOf(array, array.length + 1);
		array = newArray;
		array[this.size] = i;
	}
	
	/**
	 * <p>Remove an integer at an index location 
	 * </p>
	 * - Shift the values to remove empty spaces 
	 * 
	 * @param i index to remove
	 * @return remove on the MyArray itself
	*/
	public void removeAt(int i) {
		array[i] = null;
		for(int a=0;a<array.length;a++) {
			if ((array[a] == null) && (a != array.length-1)) array[a] = array[a+1];
		}
	}
	
	/**
	 * <p>Find a value in the array and return its index 
	 * </p>
	 * @param i index 
	 * @return index location of the integer
	*/
	public Integer indexOf(int i) {
		for(int a=0;a<array.length;a++) {
			if ((array[a] != null) && (array[a] == i)) return a;
		}
		return -1;
	}

	/**
	 * <p>Print the array in the standard output 
	 * </p>
	*/

	public void print() {
		Arrays.stream(array).forEach(System.out::println);
	}

	/**
	 * <p>Get the Size of the array 
	 * </p>
	 * @return The size of the array
	*/
	public Integer getSize() {
		return this.size;
	}

}
