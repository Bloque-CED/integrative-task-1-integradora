package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class HashTableTest {

    private HashTable<String, Integer> hashTable;


    public void setUp() {
        hashTable = new HashTable<>();
        hashTable.put("one", 1);
        hashTable.put("two", 2);
        hashTable.put("three", 3);
    }

    @Test
    public void testPut() {
        setUp();

        assertEquals(1, hashTable.get("one"));
        assertEquals(2, hashTable.get("two"));
        assertEquals(3, hashTable.get("three"));
    }

    @Test
    public void testGet() {
        setUp();


        assertEquals(1, hashTable.get("one"));
        assertEquals(2, hashTable.get("two"));
        assertEquals(3, hashTable.get("three"));
    }

    @Test
    public void testRemove() {
        setUp();

        hashTable.remove("two");

        assertNull(hashTable.get("two"));
        assertEquals(2, hashTable.size());
    }

    @Test
    public void testSize() {
        setUp();

        assertEquals(3, hashTable.size());

        hashTable.remove("two");

        assertEquals(2, hashTable.size());
    }
}