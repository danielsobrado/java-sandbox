package HashTables;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * 
 * Implement a Hash Table 
 * - Key is Integer and Value is String
 * - Use Chaining for the values 
 * 
 * @author J. Daniel Sobrado
 * 
 */
public class MyHashTable {
	
	private Integer count;
	private final Integer size;
	private LinkedList<MyHashTable.Entry>[] lists;
	
	/**
	 * We need to store key and value together 
	 */
	private class Entry {
		
		private Integer key;
		private String value;

		public Entry(Integer key, String value) {
			super();
			this.key = key;
			this.value = value;
		}
		
		@Override
		public String toString() {
			return "Key: "+key+" Value: "+value;
		}
		
		@Override
		public int hashCode() {
		    return key % size;
		}
		
	}
	
	public MyHashTable(int size) {
		super();
		this.count = 0;
		this.size = size;
		this.lists = new LinkedList[size];
	}

	public void put(Integer key, String value) {

		Entry entry = findEntry(key);
		if  (entry == null) entry = new Entry(key, value);
		else {
			entry.value = value;  // Update if it exists
			return;
		}
		
		// Create a new list if it doesn't exist
		LinkedList<Entry> list = lists[entry.hashCode()];
		if (list == null) {
			list = new LinkedList<MyHashTable.Entry>();
			lists[entry.hashCode()] = list;
		} 
		list.add(entry);
		
		this.count++;
	}
	
	public String get(Integer key) {		
		if (isListEmpty(key)) throw new NoSuchElementException();
		Entry entry = findEntry(key);
		if (entry == null) throw new NoSuchElementException();
		
		return entry.value;		
	}
	
	public void remove(Integer key) {
		if (isListEmpty(key)) throw new NoSuchElementException();

		LinkedList<Entry> list = lists[getHash(key)];
		Entry entry = findEntry(key);
		list.remove(entry);

		this.count--;
	}

	public Integer size() {
		return this.count;
	}
	
	private Entry findEntry(Integer key) {
		LinkedList<Entry> list = lists[getHash(key)];
		
		if (list == null) return null;
		
		for (Entry entry: list) {
			if (entry.key == key) return entry;
		}
		
		return null;
	}
	
	private Integer getHash(Integer key) {
		return key % this.size; 
	}
	
	private boolean isListEmpty(Integer key) {
		
		Integer pos = key % 10; 
		LinkedList<Entry> list = lists[pos]; 
		
		if (lists[pos].isEmpty()) return true;
		if (list.size() == 0) return true;
		
		return false;
		
	}
	
}