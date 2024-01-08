package edu.ser222.m03_04;

/**
 * A symbol table implemented using a hashtable with linear probing.
 * 
 * @author Witt, Sedgewick and Wayne, Acuna
 */
import java.util.LinkedList;
import java.util.Queue;

public class CompletedLinearProbingHT<Key, Value> implements ProbingHT<Key, Value> {

    private static final int DEFAULT_CAPACITY = 997; // Default size of the hashtable
    private int M; // Size of the hashtable
    private Key[] keys; // Array to store keys
    private Value[] values; // Array to store values

    // Default constructor
    public CompletedLinearProbingHT() {
        this(DEFAULT_CAPACITY);
    }

    // Constructor with custom capacity
    public CompletedLinearProbingHT(int capacity) {
        M = capacity;
        keys = (Key[]) new Object[M];
        values = (Value[]) new Object[M];
    }

    @Override
    public int hash(Key key, int i) {
        int hash = (key.hashCode() & 0x7fffffff) % M;
        return (hash + i) % M;
    }

    @Override
    public void put(Key key, Value val) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }

        if (val == null) {
            delete(key);
            return;
        }

        int i;
        for (i = hash(key, 0); keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(key)) {
                values[i] = val;
                return;
            }
        }

        keys[i] = key;
        values[i] = val;
    }

    @Override
    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }

        for (int i = hash(key, 0); keys[i] != null; i = (i + 1) % M) {
            if (keys[i].equals(key)) {
                return values[i];
            }
        }

        return null;
    }

    @Override
    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }

        int i = hash(key, 0);
        while (keys[i] != null) {
            if (keys[i].equals(key)) {
                keys[i] = null;
                values[i] = null;

                // Rehash the remaining elements in the cluster
                i = (i + 1) % M;
                while (keys[i] != null) {
                    Key rehashKey = keys[i];
                    Value rehashVal = values[i];
                    keys[i] = null;
                    values[i] = null;
                    put(rehashKey, rehashVal);
                    i = (i + 1) % M;
                }
                return;
            }
            i = (i + 1) % M;
        }
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
        for (int i = 0; i < M; i++) {
            if (keys[i] != null) {
                count++;
            }
        }
        return count;
    }

    @Override
    public Iterable<Key> keys() {
        Queue<Key> queue = new LinkedList<>();
        for (int i = 0; i < M; i++) {
            if (keys[i] != null) {
                queue.add(keys[i]);
            }
        }
        return queue;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // THESE METHODS ARE ONLY FOR GRADING AND COME FROM THE PROBINGHT INTERFACE.

    @Override
    public int getM() {
        return M;
    }

    @Override
    public Object getTableEntry(int i) {
        return keys[i] != null ? keys[i] + ": " + values[i] : null;
    }
}
