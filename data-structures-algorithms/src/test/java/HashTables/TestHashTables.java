package HashTables;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * 
 * Test cases for my implementation of Hash Table
 * 
 * @author J. Daniel Sobrado
 * 
 */
public class TestHashTables {

	@Test
	public void testHashTable() {
		MyHashTable hash = new MyHashTable(10);
		hash.put(1, "Hi");
		hash.put(10, "Hello");
		hash.put(3, "Hola");
		hash.put(55, "Salamat");
		hash.put(25, "Bonjour");
		hash.put(25, "Bonjour2"); // duplicated key
		
		assertEquals(5, hash.size());
		assertEquals("Hi", hash.get(1));
		assertEquals("Salamat", hash.get(55));
		assertEquals("Bonjour2", hash.get(25));
		
		hash.remove(25);
		hash.remove(3);
		assertEquals(3, hash.size());
		assertThrows(NoSuchElementException.class, () -> hash.get(25));
	}

}
