package Trees;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Implement a Binary Search Tree
 * 
 * @author J. Daniel Sobrado
 * 
 */
public class MyBinarySearchTree {
	
	private Integer count;
	
	private Node root;
	
	private class Node {
		
		private Node left;
		private Node right;
		
		private Integer value; 

		public Node(Integer value) {
			super();
			this.value = value;
		}
		
		@Override
		public String toString() {
			StringBuffer str = new StringBuffer("Value: "+value);
			if (this.left != null) {
				str.append(" Left: "+this.left.value);
			}
			if (this.right != null) {
				str.append(" Right: "+this.right.value);
			}
			return str.toString();
		}
		
	}
	
	public MyBinarySearchTree() {
		super();
		this.count = 0;
	}
	
	public void insert(Integer value) {
		
		if (this.root == null) {
			this.root = new Node(value);
			return;
		}
		
		var current = this.root;
		while (true) {
			if (current.value == value) {
				return;
			} else if (current.value > value) {
				if (current.left == null) {
					current.left = new Node(value);
					return;
				}
				current = current.left;
			} else {
				if (current.right == null) {
					current.right = new Node(value);
					return;
				}
				current = current.right;
			}
		}
	}

	public boolean find(Integer value) {
		var current = this.root;
		while (current != null) {
			if (current.value == value) {
				return true;
			} else if (current.value > value) {
				if (!value.equals(current.left.value)) current = current.left;
				else return true;
			} else {
				if (!value.equals(current.right.value)) current = current.right;
				else return true;				
			}
		}
		return false;
	}
	

	public int[] traversePreOrder() {
		List<Integer> results = new ArrayList<Integer>();
		this.traversePreOrder(this.root, results);
		int[] arr = results.stream().mapToInt(i -> i).toArray();
		return arr;
	}

	public int[] traverseInOrder() {
		List<Integer> results = new ArrayList<Integer>();
		this.traverseInOrder(this.root, results);
		int[] arr = results.stream().mapToInt(i -> i).toArray();
		return arr;
	}

	public int[] traversePostOrder() {
		List<Integer> results = new ArrayList<Integer>();
		this.traversePostOrder(this.root, results);
		int[] arr = results.stream().mapToInt(i -> i).toArray();
		return arr;
	}

	private void traversePreOrder(Node node, List<Integer> results) {
		if (node == null) return;
		results.add(node.value);
		traversePreOrder(node.left, results);
		traversePreOrder(node.right, results);
	}
	
	private void traverseInOrder(Node node, List<Integer> results) {
		if (node == null) return;
		traverseInOrder(node.left, results);
		results.add(node.value);		
		traverseInOrder(node.right, results);
	}
	
	private void traversePostOrder(Node node, List<Integer> results) {
		if (node == null) return;
		traversePostOrder(node.left, results);
		traversePostOrder(node.right, results);
		results.add(node.value);
	}
	
	public boolean hasChildren(Node node) {
		return (!(node.left == null) && !(node.right == null));
	}

	public Node getRoot() {
		return this.root;
	}

	
}