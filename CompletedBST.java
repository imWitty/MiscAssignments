package edu.ser222.m03_02;

/**
 * A binary search tree based implementation of a symbol table.
 *
 * Completion time: (your completion time)
 *
 * @author (Witt), Sedgewick, Acuna
 * @version (version)
 */

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class CompletedBST<Key extends Comparable<Key>, Value> implements BST<Key, Value> {
    private Node<Key, Value> root;
    private Key lo;
    private Key hi;



    @Override
    public Value get(Key key) {
        Node<Key, Value> iter = root;

        while (iter != null) {
            int cmp = key.compareTo(iter.key);

            if (cmp < 0)
                iter = iter.left;
            else if (cmp > 0)
                iter = iter.right;
            else
                return iter.val;
        }

        return null;
    }

    private Value get(Node<Key, Value> x, Key key) {
        if (x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            return get(x.left, key);
        else if (cmp > 0)
            return get(x.right, key);
        else
            return x.val;
    }

    @Override
    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    private Node<Key, Value> put(Node<Key, Value> x, Key key, Value val) {
        if (x == null)
            return new Node<>(key, val, 1);

        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = put(x.left, key, val);
        else if (cmp > 0)
            x.right = put(x.right, key, val);
        else
            x.val = val;
        x.N = size(x.left) + size(x.right) + 1;

        return x;
    }

    @Override
    public Key min() {
        if (root == null)
            throw new NoSuchElementException();
        return min(root).key;
    }

    private Node<Key, Value> min(Node<Key, Value> x) {
        if (x.left == null)
            return x;
        return min(x.left);
    }

    @Override
    public Key max() {
        if (root == null)
            throw new NoSuchElementException();
        return max(root).key;
    }

    private Node<Key, Value> max(Node<Key, Value> x) {
        if (x.right == null)
            return x;
        return max(x.right);
    }

    @Override
    public Key floor(Key key) {
        if (root == null)
            throw new NoSuchElementException();

        Node<Key, Value> x = floor(root, key);
        if (x == null)
            return null;
        return x.key;
    }

    private Node<Key, Value> floor(Node<Key, Value> x, Key key) {
        if (x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0)
            return x;
        if (cmp < 0)
            return floor(x.left, key);
        Node<Key, Value> t = floor(x.right, key);
        if (t != null)
            return t;
        else
            return x;
    }

    @Override
    public Key select(int k) {
        return select(root, k).key;
    }

    private Node<Key, Value> select(Node<Key, Value> x, int k) {
        if (x == null)
            return null;
        int t = size(x.left);
        if (t > k)
            return select(x.left, k);
        else if (t < k)
            return select(x.right, k - t - 1);
        else
            return x;
    }

    @Override
    public int rank(Key key) {
        return rank(key, root);
    }

    private int rank(Key key, Node<Key, Value> x) {
        if (x == null)
            return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            return rank(key, x.left);
        else if (cmp > 0)
            return 1 + size(x.left) + rank(key, x.right);
        else
            return size(x.left);
    }

    @Override
    public void deleteMin() {
        if (root == null)
            throw new NoSuchElementException();
        root = deleteMin(root);
    }

    private Node<Key, Value> deleteMin(Node<Key, Value> x) {
        if (x.left == null)
            return x.right;
        x.left = deleteMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    @Override
    public void delete(Key key) {
        root = delete(root, key);
    }

    private Node<Key, Value> delete(Node<Key, Value> x, Key key) {
        if (x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = delete(x.left, key);
        else if (cmp > 0)
            x.right = delete(x.right, key);
        else {
            if (x.right == null)
                return x.left;
            if (x.left == null)
                return x.right;
            Node<Key, Value> t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    @Override
    public Iterable<Key> keys() {
        if (root == null)
            return new LinkedList<>();
        else
            return keys(min(), max());
    }

    @Override
    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> queue = new LinkedList<>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node<Key, Value> x, Queue<Key> queue, Key lo, Key hi) {
        if (x == null)
            return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0)
            keys(x.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0)
            queue.add(x.key);
        if (cmphi > 0)
            keys(x.right, queue, lo, hi);
    }

    public Key ceiling(Key key) {
        //SKIP, UNNEEDED
        return null;
    }
    @Override
    public void putFast(Key key, Value val) {
        root = putFast(root, key, val);
    }

    private Node<Key, Value> putFast(Node<Key, Value> x, Key key, Value val) {
        if (x == null)
            return new Node<>(key, val, 1);

        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = putFast(x.left, key, val);
        else if (cmp > 0)
            x.right = putFast(x.right, key, val);
        else
            x.val = val;
        x.N = size(x.left) + size(x.right) + 1;

        return x;
    }

    @Override
    public Value getFast(Key key) {
        return getFast(root, key);
    }



    private Value getFast(Node<Key, Value> x, Key key) {
        if (x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            return getFast(x.left, key);
        else if (cmp > 0)
            return getFast(x.right, key);
        else
            return x.val;
    }



    public String displayLevel(Key key) {
        if (root == null)
            return "empty";

        Node nod = root;
        while (nod != null) {
            int value = key.compareTo((Key) nod.key);
            if (value < 0)
                nod = nod.left;
            else if (value > 0)
                nod = nod.right;
            else
                break;
        }

        if (nod == null)
            return null;

        StringBuilder sb = new StringBuilder();
        Queue<Node> queue = new LinkedList<>();
        queue.add(nod);
        while (!queue.isEmpty()) {
            nod = queue.poll();
            sb.append(convertToWrittenForm((Integer) nod.key)).append(" ");

            if (nod.left != null) {
                queue.add(nod.left);
            }

            if (nod.right != null) {
                queue.add(nod.right);
            }
        }
        return sb.toString();
    }

    private String convertToWrittenForm(int number) {
        if (number == 0) {
            return "ZERO";
        } else if (number < 0) {
            return "MINUS " + convertToWrittenForm(-number);
        } else {
            String[] units = {
                    "", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEN",
                    "ELEVEN", "TWELVE", "THIRTEEN", "FOURTEEN", "FIFTEEN", "SIXTEEN", "SEVENTEEN",
                    "EIGHTEEN", "NINETEEN"
            };

            String[] tens = {
                    "", "", "TWENTY", "THIRTY", "FORTY", "FIFTY", "SIXTY", "SEVENTY", "EIGHTY", "NINETY"
            };

            String result;

            if (number < 20) {
                result = units[number];
            } else if (number < 100) {
                result = tens[number / 10] + " " + units[number % 10];
            } else if (number < 1000) {
                result = units[number / 100] + " HUNDRED " + convertToWrittenForm(number % 100);
            } else {
                result = "NUMBER OUT OF RANGE";
            }

            return result.toUpperCase();
        }
    }



    @Override
    public void balance() {
        Queue<Node<Key, Value>> nodes = new LinkedList<>();
        inOrderTraversal(root, nodes);
        root = balance(nodes, 0, nodes.size() - 1);
    }

    private Node<Key, Value> balance(Queue<Node<Key, Value>> nodes, int start, int end) {
        if (start > end)
            return null;

        int mid = (start + end) / 2;
        Node<Key, Value> left = balance(nodes, start, mid - 1);
        Node<Key, Value> x = nodes.poll();
        Node<Key, Value> right = balance(nodes, mid + 1, end);

        x.left = left;
        x.right = right;
        x.N = size(x.left) + size(x.right) + 1;

        return x;
    }

    private void inOrderTraversal(Node<Key, Value> x, Queue<Node<Key, Value>> nodes) {
        if (x == null)
            return;
        inOrderTraversal(x.left, nodes);
        nodes.offer(x);
        inOrderTraversal(x.right, nodes);
    }
    public Node<Key, Value> getRoot() {
        return root;
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return size(root);
    }
    private int size(Node<Key, Value> x) {
        if (x == null)
            return 0;
        return x.N;
    }

    public void deleteMax() {
        if (root == null)
            throw new NoSuchElementException();
        root = deleteMax(root);
    }

    @Override
    public int size(Key lo, Key hi) {
        return 0;
    }

    private Node<Key, Value> deleteMax(Node<Key, Value> x) {
        if (x.right == null)
            return x.left;
        x.right = deleteMax(x.right);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }


    /**
     * entry point for testing.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BST<Integer, String> bst = new CompletedBST();

        bst.put(10, "TEN");
        bst.put(3, "THREE");
        bst.put(1, "ONE");
        bst.put(5, "FIVE");
        bst.put(2, "TWO");
        bst.put(7, "SEVEN");

        System.out.println("Before balance:");
        System.out.println(bst.displayLevel(10)); //root

        System.out.println("After balance:");
        bst.balance();
        System.out.println(bst.displayLevel(5)); //root
    }
}