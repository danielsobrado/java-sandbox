package Trees;

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
		while (hasChildren(current)) {
			if (current.value == value) {
				return;
			} else if (current.value > value) {
				current = current.left;
			} else {
				current = current.right;
			}
		}
		
		if (current.value < value) {
			current.right = new Node(value);
		} else {
			current.left = new Node(value);
		}
	}

	public boolean find(Integer value) {
		var current = this.root;
		while (hasChildren(current)) {
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
	

	public void traversePreOrder() {
		
	}

	public void traverseInOrder() {
		
	}

	public void traversePostOrder() {
		
	}

	private void traversePreOrder(Node node) {
		if (node == null) return;
		
	}
	
	private void traverseInOrder(Node node) {
		if (node == null) return;
		
	}
	
	private void traversePostOrder(Node node) {
		if (node == null) return;
		
	}
	
	public boolean hasChildren(Node node) {
		return (!(node.left == null) && !(node.right == null));
	}

	public Node getRoot() {
		return this.root;
	}

	
}