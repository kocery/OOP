package hashtable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;


public class HashTableTest {

    private HashTable<String, Integer> hashTable;

    @BeforeEach
    public void setUp() {
        hashTable = new HashTable<>();
    }

    @Test
    public void testPutAndGet() {
        hashTable.put("One", 1);
        hashTable.put("Two", 2);
        hashTable.put("Three", 3);

        assertEquals(1, hashTable.get("One"));
        assertEquals(2, hashTable.get("Two"));
        assertEquals(3, hashTable.get("Three"));
        assertNull(hashTable.get("Four"));
    }

    @Test
    public void testUpdate() {
        hashTable.put("Key", 1);
        hashTable.update("Key", 2);
        assertEquals(2, hashTable.get("Key"));

        Exception exception = assertThrows(NoSuchElementException.class,
            () -> hashTable.update("NonExistentKey", 3));
        assertEquals("Key not found: NonExistentKey", exception.getMessage());
    }

    @Test
    public void testRemove() {
        hashTable.put("A", 10);
        hashTable.put("B", 20);
        hashTable.put("C", 30);

        assertEquals(20, hashTable.remove("B"));
        assertNull(hashTable.get("B"));
        assertNull(hashTable.remove("B"));
        assertEquals(2, hashTable.size());
    }

    @Test
    public void testContainsKey() {
        hashTable.put("Alpha", 1);
        assertTrue(hashTable.containsKey("Alpha"));
        assertFalse(hashTable.containsKey("Beta"));
    }

    @Test
    public void testIteratorAndConcurrentModification() {
        hashTable.put("Key1", 100);
        hashTable.put("Key2", 200);
        hashTable.put("Key3", 300);

        Iterator<HashTable.Entry<String, Integer>> iterator = hashTable.iterator();

        assertTrue(iterator.hasNext());
        HashTable.Entry<String, Integer> entry = iterator.next();
        assertNotNull(entry);

        hashTable.put("Key4", 400);

        assertThrows(ConcurrentModificationException.class, iterator::next);
    }

    @Test
    public void testEquals() {
        HashTable<String, Integer> otherTable = new HashTable<>();

        hashTable.put("Key1", 1);
        hashTable.put("Key2", 2);

        otherTable.put("Key2", 2);
        otherTable.put("Key1", 1);

        assertEquals(hashTable, otherTable);

        otherTable.put("Key3", 3);
        assertNotEquals(hashTable, otherTable);
    }

    @Test
    public void testToString() {
        hashTable.put("Apple", 1);
        hashTable.put("Banana", 2);

        String result = hashTable.toString();
        assertTrue(result.contains("Apple=1"));
        assertTrue(result.contains("Banana=2"));
    }

    @Test
    public void testResize() {
        for (int i = 0; i < 20; i++) {
            hashTable.put("Key" + i, i);
        }
        assertEquals(20, hashTable.size());
        for (int i = 0; i < 20; i++) {
            assertEquals(i, hashTable.get("Key" + i));
        }
    }

    @Test
    public void testIterator() {
        hashTable.put("A", 1);
        hashTable.put("B", 2);
        hashTable.put("C", 3);

        int count = 0;
        for (HashTable.Entry<String, Integer> entry : hashTable) {
            assertNotNull(entry.getKey());
            assertNotNull(entry.getValue());
            count++;
        }
        assertEquals(3, count);
    }

    @Test
    public void testRemoveDuringIteration() {
        hashTable.put("X", 1);
        hashTable.put("Y", 2);

        Iterator<HashTable.Entry<String, Integer>> iterator = hashTable.iterator();

        assertTrue(iterator.hasNext());
        iterator.next();

        hashTable.remove("Y");

        assertThrows(ConcurrentModificationException.class, iterator::next);
    }

    @Test
    public void testUpdateExistVal() {
        hashTable.put("X", 1);
        hashTable.put("Y", 2);
        hashTable.put("Y", 2);
        hashTable.put("Y", 4);

        assertEquals(4, (int) hashTable.get("Y"));
    }

    @Test
    public void testNullNextValue() {
        hashTable.put("X", 1);
        hashTable.put("Y", 2);

        Iterator<HashTable.Entry<String, Integer>> iterator = hashTable.iterator();

        iterator.next();
        iterator.next();

        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    public void testNullKeysAndValues() {
        hashTable.put(null, 100);
        hashTable.put("NullValue", null);

        assertEquals(100, hashTable.get(null));
        assertNull(hashTable.get("NullValue"));

        hashTable.update(null, 200);
        assertEquals(200, hashTable.get(null));

        assertTrue(hashTable.containsKey(null));
        assertTrue(hashTable.containsKey("NullValue"));

        hashTable.remove(null);
        assertNull(hashTable.get(null));
    }

    @Test
    public void testEqualsAndHashCode() {
        HashTable<String, Integer> otherTable = new HashTable<>();
        otherTable.put("Key2", 2);
        otherTable.put("Key1", 1);

        hashTable.put("Key1", 1);
        hashTable.put("Key2", 2);

        assertEquals(hashTable, otherTable);
        assertEquals(hashTable.hashCode(), otherTable.hashCode());

        otherTable.put("Key3", 3);
        assertNotEquals(hashTable, otherTable);
        assertNotEquals(hashTable.hashCode(), otherTable.hashCode());
    }

    /**
     * Tests the equals and hashCode methods of the Entry class.
     */
    @Test
    public void testEntryEqualsAndHashCode() {
        HashTable.Entry<String, Integer> entry1 = new HashTable.Entry<>("Key", 1, null);
        HashTable.Entry<String, Integer> entry2 = new HashTable.Entry<>("Key", 1, null);
        assertEquals(entry1, entry2);
        assertEquals(entry1.hashCode(), entry2.hashCode());

        HashTable.Entry<String, Integer> entry3 = new HashTable.Entry<>("Key", 2, null);
        HashTable.Entry<String, Integer> entry4 = new HashTable.Entry<>("AnotherKey", 1, null);
        assertNotEquals(entry1, entry3);
        assertNotEquals(entry1.hashCode(), entry3.hashCode());
        assertNotEquals(entry1, entry4);
        assertNotEquals(entry1.hashCode(), entry4.hashCode());

        HashTable.Entry<String, Integer> entry5 = new HashTable.Entry<>(null, null, null);
        HashTable.Entry<String, Integer> entry6 = new HashTable.Entry<>(null, null, null);
        assertEquals(entry5, entry6);
        assertEquals(entry5.hashCode(), entry6.hashCode());
        assertNotEquals(entry1, entry5);
    }
}