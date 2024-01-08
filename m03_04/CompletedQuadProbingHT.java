package edu.ser222.m03_04;

/**
 * A symbol table implemented using a hashtable with quadratic probing.
 * 
 * @author Witt, Acuna
 */
public class CompletedQuadProbingHT<Key, Value> extends CompletedLinearProbingHT<Key, Value> {

    // Any constructors must be made public

    // The size of the hashtable
    private int M;

    public CompletedQuadProbingHT() {
        super();
        M = super.getM(); // Get the size from the superclass
    }

    public CompletedQuadProbingHT(int capacity) {
        super(capacity);
        M = super.getM(); // Get the size from the superclass
    }

    // Additional methods and overrides specific to CompletedQuadProbingHT can be added here

    // ...
}