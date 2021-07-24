package LinkedLists;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * 
 * Test cases for my implementation of LinkedList
 * 
 * @author J. Daniel Sobrado
 * 
 */
public class TestLinkedLists {

	@Test
	public void testAddFirst() {
		var list = new MyLinkedList();
		list.addFirst(10);
		
		assertTrue(list.size() == 1);
		assertTrue(list.indexOf(10) == 0);

		list.addFirst(20);

		assertTrue(list.size() == 2);
		assertTrue(list.indexOf(20) == 0);
		assertTrue(list.indexOf(10) == 1);
	}

	@Test
	public void testaddLast() {
		var list = new MyLinkedList();
		list.addLast(10);
		list.addLast(20);
		list.addLast(30);
		
		assertTrue(list.size() == 3);
		assertTrue(list.indexOf(20) == 1);
		
		list.addFirst(1);
		assertTrue(list.indexOf(1) == 0);
		assertTrue(list.indexOf(30) == 3);
	}
	
	@Test
	public void testDelete() {
		var list = new MyLinkedList();
		list.addLast(10);
		list.addLast(20);
		list.addLast(30);
		list.addLast(40);
		list.addLast(50);
		list.deleteFirst();
		
		assertTrue(list.size() == 4);
		assertTrue(list.indexOf(20) == 0);
		assertFalse(list.contains(10));
		assertTrue(list.contains(50));
		
		list.deleteFirst();
		assertFalse(list.contains(10));
		assertFalse(list.contains(20));
		assertTrue(list.contains(50));
		assertTrue(list.size() == 3);
		
		list.deleteLast();
		assertFalse(list.contains(10));
		assertFalse(list.contains(50));
		assertTrue(list.contains(40));
		assertTrue(list.size() == 2);
	}
	
	@Test
	public void testArray() {
		var list = new MyLinkedList();
		list.addLast(10);
		list.addLast(20);
		list.addLast(30);
		
		var intArray = new int[] {10, 20, 30};
		assertArrayEquals(list.toArray(), intArray);

		list.addFirst(5);
		assertFalse(list.toArray().length == intArray.length);

	}
	
	@Test
	public void testReverse() {
		var list = new MyLinkedList();
		list.addLast(10);
		list.addLast(20);
		list.addLast(30);
		list.addLast(40);

		var intArrayOriginal = list.toArray();

		list.reverse();

		var intArray = new int[] {40, 30, 20, 10};
		assertArrayEquals(list.toArray(), intArray);
		assertTrue(list.toArray().length == intArray.length);
		assertTrue(list.indexOf(10) == 3);
		assertTrue(list.indexOf(40) == 0);
		
		list.reverse2();

		assertArrayEquals(list.toArray(), intArrayOriginal);
		assertTrue(list.toArray().length == intArrayOriginal.length);
		assertTrue(list.indexOf(10) == 0);
		assertTrue(list.indexOf(40) == 3);

		var emptyList = new MyLinkedList();
		assertDoesNotThrow(emptyList::reverse);
		assertDoesNotThrow(emptyList::reverse2);
	}
	
	@Test
	public void testKthFromEnd() {
		var list = new MyLinkedList();
		list.addLast(10);
		list.addLast(20);
		list.addLast(30);
		list.addLast(40);
		list.addLast(50);
		list.addLast(60);
		
		int kth = list.getKthFromEnd(3);
		
		assertEquals(kth,30);
		assertEquals(list.getKthFromEnd(0), 60);
		assertEquals(list.getKthFromEnd(1), 50);
		assertEquals(list.getKthFromEnd(2), 40);
		assertThrows(IllegalArgumentException.class, () -> list.getKthFromEnd(6));
		assertThrows(IllegalArgumentException.class, () -> list.getKthFromEnd(10));
		assertThrows(IllegalArgumentException.class, () -> list.getKthFromEnd(-2));
		
		var emptyList = new MyLinkedList();
		assertThrows(IllegalStateException.class, () -> emptyList.getKthFromEnd(2));
		
	}

}
