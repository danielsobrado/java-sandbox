package Trees;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * 
 * Test cases for my implementation of Binary Search Tree
 * 
 * @author J. Daniel Sobrado
 * 
 */
public class TestBinarySearchTree {

	@Test
	public void testInsertFind() {
		MyBinarySearchTree tree = new MyBinarySearchTree();
		tree.insert(7);
		tree.insert(1);
		tree.insert(10);
		tree.insert(3);
		tree.insert(5);
		tree.insert(15);
		tree.insert(30);
		
		assertTrue(tree.find(7));
		assertTrue(tree.find(10));
		assertFalse(tree.find(17));
		assertTrue(tree.find(15));
		assertTrue(tree.find(30));
	}
	
	@Test
	public void testTraverse() {

		MyBinarySearchTree tree = new MyBinarySearchTree();
		tree.insert(70);
		tree.insert(10);
		tree.insert(100);
		tree.insert(30);
		tree.insert(12);
		tree.insert(106);
		tree.insert(33);
		tree.insert(19);
		tree.insert(23);
		tree.insert(43);
		
		int[] resultPreOrder = tree.traversePreOrder();
		
		int[] resultInOrder = tree.traverseInOrder();
		
		int[] resultPostOrder = tree.traversePostOrder();

	}


}
