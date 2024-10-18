package hashtable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
import java.util.Objects;

/**
 * A generic hash table implementation that maps keys to values.
 *
 * @param <K> the type of keys maintained by this hash table.
 * @param <V> the type of mapped values.
 */
public class HashTable<K, V> implements Iterable<HashTable.Entry<K, V>> {

    private static final float LOAD_FACTOR = 0.75f;

    private Entry<K, V>[] table;
    private int size;
    private int capacity;
    private int modCount;

    /**
     * Represents a key-value pair in the hash table.
     *
     * @param <K> the type of keys maintained by this entry.
     * @param <V> the type of mapped values.
     */
    public static class Entry<K, V> {

        final K key;
        V value;
        Entry<K, V> next;

        /**
         * Constructs a new entry with the specified key, value, and next entry.
         *
         * @param key   the key.
         * @param value the value.
         * @param next  the next entry in the chain.
         */
        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        /**
         * Returns the key corresponding to this entry.
         *
         * @return the key corresponding to this entry.
         */
        public K getKey() {
            return key;
        }

        /**
         * Returns the value corresponding to this entry.
         *
         * @return the value corresponding to this entry.
         */
        public V getValue() {
            return value;
        }

        /**
         * Returns a string representation of this entry.
         *
         * @return a string in the form "key=value".
         */
        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    /**
     * Constructs an empty hash table with the default initial capacity (16).
     */
    public HashTable() {
        this.capacity = 16;
        this.table = new Entry[capacity];
    }

    /**
     * Computes the index in the table array for the given key.
     *
     * @param key the key.
     * @return the index corresponding to the key.
     */
    private int hashKey(Object key) {
        return (key == null) ? 0 : Math.abs(key.hashCode()) % capacity;
    }

    /**
     * Adds a key-value pair to the hash table. If the key already exists, its value is updated.
     *
     * @param key   the key.
     * @param value the value.
     */
    public void put(K key, V value) {
        int index = hashKey(key);
        Entry<K, V> head = table[index];

        // Update value if key already exists
        for (Entry<K, V> curr = head; curr != null; curr = curr.next) {
            if (Objects.equals(curr.key, key)) {
                curr.value = value;
                return;
            }
        }

        Entry<K, V> newEntry = new Entry<>(key, value, head);
        table[index] = newEntry;
        size++;
        modCount++;

        if (size >= capacity * LOAD_FACTOR) {
            resize();
        }
    }

    /**
     * Retrieves the value associated with the specified key.
     *
     * @param key the key.
     * @return the value corresponding to the key, or {@code null} if not found.
     */
    public V get(Object key) {
        int index = hashKey(key);
        for (Entry<K, V> curr = table[index]; curr != null; curr = curr.next) {
            if (Objects.equals(curr.key, key)) {
                return curr.value;
            }
        }
        return null;
    }

    /**
     * Removes the key-value pair associated with the specified key.
     *
     * @param key the key.
     * @return the value that was associated with the key, or {@code null} if not found.
     */
    public V remove(Object key) {
        int index = hashKey(key);
        Entry<K, V> curr = table[index];
        Entry<K, V> prev = null;

        while (curr != null) {
            if (Objects.equals(curr.key, key)) {
                if (prev == null) {
                    table[index] = curr.next;
                } else {
                    prev.next = curr.next;
                }
                size--;
                modCount++;
                return curr.value;
            }
            prev = curr;
            curr = curr.next;
        }
        return null;
    }

    /**
     * Updates the value associated with the specified key.
     *
     * @param key   the key.
     * @param value the new value.
     * @throws NoSuchElementException if the key does not exist.
     */
    public void update(K key, V value) {
        int index = hashKey(key);
        for (Entry<K, V> curr = table[index]; curr != null; curr = curr.next) {
            if (Objects.equals(curr.key, key)) {
                curr.value = value;
                modCount++;
                return;
            }
        }
        throw new NoSuchElementException("Key not found: " + key);
    }

    /**
     * Checks if the hash table contains the specified key.
     *
     * @param key the key.
     * @return {@code true} if the key exists, {@code false} otherwise.
     */
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    /**
     * Returns the number of key-value pairs in the hash table.
     *
     * @return the size of the hash table.
     */
    public int size() {
        return size;
    }

    /**
     * Resizes the table when the load factor is exceeded.
     */
    private void resize() {
        capacity *= 2;
        Entry<K, V>[] oldTable = table;
        table = new Entry[capacity];
        size = 0;
        modCount++;

        for (Entry<K, V> head : oldTable) {
            for (Entry<K, V> curr = head; curr != null; curr = curr.next) {
                put(curr.key, curr.value);
            }
        }
    }

    /**
     * Compares the specified object with this hash table for equality.
     *
     * @param o object to be compared for equality with this hash table.
     * @return {@code true} if the specified object is equal to this hash table.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HashTable<?, ?> other)) {
            return false;
        }

        if (size != other.size) {
            return false;
        }

        for (Entry<K, V> entry : this) {
            Object key = entry.key;
            Object value = entry.value;
            Object otherValue = other.get(key);

            if (!Objects.equals(value, otherValue)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a string representation of the hash table.
     *
     * @return a string in the form "{key1=value1, key2=value2, ...}".
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        boolean first = true;

        for (Entry<K, V> entry : this) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(entry.toString());
            first = false;
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * Returns an iterator over the entries in the hash table.
     *
     * @return an iterator over the entries in the hash table.
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new HashTableIterator();
    }

    /**
     * Iterator implementation for the hash table.
     */
    private class HashTableIterator implements Iterator<Entry<K, V>> {

        private int bucketIndex;
        private Entry<K, V> currentEntry;
        private Entry<K, V> nextEntry;
        private final int expectedModCount;

        /**
         * Constructs a new iterator over the hash table.
         */
        public HashTableIterator() {
            expectedModCount = modCount;
            currentEntry = null;
            nextEntry = null;
            bucketIndex = -1;
            advance();
        }

        /**
         * Advances the iterator to the next entry.
         */
        private void advance() {
            if (nextEntry != null && nextEntry.next != null) {
                nextEntry = nextEntry.next;
                return;
            }

            nextEntry = null;
            while (++bucketIndex < capacity) {
                if (table[bucketIndex] != null) {
                    nextEntry = table[bucketIndex];
                    break;
                }
            }
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         *
         * @return {@code true} if the iterator has more entries.
         */
        @Override
        public boolean hasNext() {
            return nextEntry != null;
        }

        /**
         * Returns the next entry in the iteration.
         *
         * @return the next entry.
         * @throws ConcurrentModificationException if the hash table was modified after the iterator
         *                                         was created.
         * @throws NoSuchElementException          if the iteration has no more elements.
         */
        @Override
        public Entry<K, V> next() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
            if (nextEntry == null) {
                throw new NoSuchElementException();
            }

            currentEntry = nextEntry;
            advance();
            return currentEntry;
        }
    }
}
