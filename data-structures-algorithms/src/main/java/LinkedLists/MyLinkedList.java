package LinkedLists;

import java.util.NoSuchElementException;

/**
 * 
 * Implement a LinkedList 
 * - Reverse it
 * - Get the Kth item in the list (Use 2 pointers)
 * 
 * @author J. Daniel Sobrado
 * 
 */
public class MyLinkedList {
	
	class Node {
		private int value;
		private Node next;

		public Node(int i, Node node) {
			this.value = i;
			this.next= node;
		}
		
		public String toString() {
			return "Value: "+value+" isNext: "+(next != null);
		}
		
	}

	private Node first;
	private Node last;

	private int size = 0;
	
	// addFirst
	public void addFirst(int i) {
		if (first == null) {
			var newNode = new Node(i, null);
			this.first = newNode;
			this.last = newNode;
		} else {
			this.first = new Node(i, first);
		}
		size++;
	}

	// addLast
	public void addLast(int i) {
		var newNode = new Node(i, null);
		if (last == null) {
			this.first = newNode;
		} else {
			this.last.next = newNode;
		}
		this.last = newNode;
		size++;
	}

	// deleteFirst
	public void deleteFirst() {
		if (first == null) {
			throw new NoSuchElementException();
		} else {
			var oldFirst = first;
			var second = first.next;
			this.first = second;
			oldFirst.next = null;
		}
		size--;
	}

	// deleteLast
	public void deleteLast() {
		if (last == null) {
			throw new NoSuchElementException();
		} if (first.next == null) {
			this.first = null;
			this.last = null;
		} else {
			var currentNode = first;
			while (currentNode.next != null) {
				var nextNode = currentNode.next;
				if (nextNode.next == null) {
					currentNode.next = null;
				}
				currentNode = nextNode;
			}
		}
		size--;
	}

	// contains
	public boolean contains(int i) {
		if (first == null) return false;
		var currentNode = first;
		if (first.value == i) return true;
		while (currentNode.next != null) {
			currentNode = currentNode.next;
			if (currentNode.value == i) {
				return true;
			}
		};
		return false;
	}

	// indexOf
	public int indexOf(int i) {
		if (first == null) return -1;
		var currentNode = first;
		for (int a=0;a<size;a++) {
			if (currentNode.value == i) return a;
			currentNode = currentNode.next;
		}
		return -1;
	}

	public int size() {
		return size;
	}
	
	public String toString() {
		var str = new StringBuffer();
		if (first == null) return "Empty";
		var currentNode = first;
		str.append(currentNode.value);
		while (currentNode.next != null) {
			currentNode = currentNode.next;
			str.append(" - "+ currentNode.value);
		}
		return str.toString();
	}
	
	public boolean hasLoop() {
		return true;
	}
	
	public void printMiddle() {
		
	}
	
	public int[] toArray() {
		var intArray = new int[this.size];
		var current = first;
		var index = 0;
		while (current != null) {
			intArray[index++] = current.value;
			current = current.next;
		}
		return intArray;
	}

	// Will need O(n) space
	public void reverse() {
		
		if (this.first == null) return;
		
		var reversedList = new MyLinkedList();
		var current = first;
		while (current != null) {
			reversedList.addFirst(current.value);
			current = current.next;
		}
		
		this.first = reversedList.first;
		this.last = reversedList.last;
		this.last.next = null;
	}

	// Will need O(1) space
	public void reverse2() {
		
		if (this.first == null) return;
		
		var previous = this.first;
		var current = this.first.next;
		while (current != null) {
			var next = current.next;
			current.next = previous;
			previous = current;
			current = next;
		}
		
		this.last = this.first;
		this.last.next = null;
		this.first = previous;
	}
	
	// We'll use 2 pointers separated by Kth - 1
	public int getKthFromEnd(int k) {

		if (first == null) throw new IllegalStateException();

		if (k < 0) throw new IllegalArgumentException();
		if (k == 0) {
				return this.last.value;	
		}
		if (k > this.size()-1) throw new IllegalArgumentException();
		
		var current = this.first.next;
		int index = 1;
		Node kthNode = null;
		
		while (current != null) {
			if (kthNode == null) {
				if (index++ == k) {
					kthNode = this.first;
				}
			} else {
				kthNode = kthNode.next;
			}
			current = current.next;
		}
		
		if (kthNode == null) return -1;
		return kthNode.value;
		
	}

}
