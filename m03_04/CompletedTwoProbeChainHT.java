package edu.ser222.m03_04;

/**
 * A symbol table implemented using a hashtable with chaining.
 * Does not support load balancing or resizing.
 * 
 * @author Witt, Sedgewick and Wayne, Acuna
 */
import java.util.LinkedList;
import java.util.Queue;


public class CompletedTwoProbeChainHT<Key, Value> implements TwoProbeChainHT<Key, Value> {
    private int M; // Length of the internal array
    private LinkedList<Entry<Key, Value>>[] entries; // Array of linked lists for separate chaining

    // Default constructor
    public CompletedTwoProbeChainHT() {
        this.M = 997; // Default array size
        this.entries = new LinkedList[M];
        for (int i = 0; i < M; i++) {
            entries[i] = new LinkedList<>();
        }
    }

    @Override
    public int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    @Override
    public int hash2(Key key) {
        return (((key.hashCode() & 0x7fffffff) % M) * 31) % M;
    }

    @Override
    public void put(Key key, Value val) {
        int index1 = hash(key);
        int index2 = hash2(key);

        LinkedList<Entry<Key, Value>> list1 = entries[index1];
        LinkedList<Entry<Key, Value>> list2 = entries[index2];

        if (list1.size() <= list2.size()) {
            list1.add(new Entry<>(key, val));
        } else {
            list2.add(new Entry<>(key, val));
        }
    }

    @Override
    public Value get(Key key) {
        int index1 = hash(key);
        int index2 = hash2(key);

        LinkedList<Entry<Key, Value>> list1 = entries[index1];
        LinkedList<Entry<Key, Value>> list2 = entries[index2];

        for (Entry<Key, Value> entry : list1) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }

        for (Entry<Key, Value> entry : list2) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }

        return null; // Key not found
    }

    @Override
    public void delete(Key key) {
        int index1 = hash(key);
        int index2 = hash2(key);

        LinkedList<Entry<Key, Value>> list1 = entries[index1];
        LinkedList<Entry<Key, Value>> list2 = entries[index2];

        list1.removeIf(entry -> entry.getKey().equals(key));
        list2.removeIf(entry -> entry.getKey().equals(key));
    }

    @Override
    public boolean contains(Key key) {
        return get(key) != null;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        int count = 0;
        for (LinkedList<Entry<Key, Value>> list : entries) {
            count += list.size();
        }
        return count;
    }

    @Override
    public Iterable<Key> keys() {
        LinkedList<Key> keys = new LinkedList<>();
        for (LinkedList<Entry<Key, Value>> list : entries) {
            for (Entry<Key, Value> entry : list) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }

    // Implementation for the methods in the TwoProbeChainHT interface

    @Override
    public int getM() {
        return M;
    }

    @Override
    public int getChainSize(int i) {
        return entries[i].size();
    }

    // Entry class for key-value pairs
    private static class Entry<Key, Value> {
        private Key key;
        private Value value;

        public Entry(Key key, Value value) {
            this.key = key;
            this.value = value;
        }

        public Key getKey() {
            return key;
        }

        public Value getValue() {
            return value;
        }
    }
}