package edu.ser222.m01_03;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;
/**
 * CompletedList represents an implementation of a list.
 *
 * @author (Witt), Acuna
 * @version (version)
 */
public class CompletedList<T> implements ListADT<T>, Iterable<T> {
    protected int count;
    protected int modChange;
    protected DoubleLinearNode<T> head, tail;

    public CompletedList(){
        head = tail = null;
        count = 0;
    }

    @Override
    public T removeFirst() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("List is Empty.");
        }
        T temp = head.getItem();
        if(head==tail) {
            head = tail = null;
        }
        else if(head.getNext()== tail) {
            head.remove();
            head = tail;
        }else {
            head = head.getNext();
            head.getPrev().remove();
        }
        count--;
        modChange++;
        return temp;

        }


    @Override
    public T removeLast() throws NoSuchElementException {
        if(isEmpty()) {
            throw new NoSuchElementException("List is Empty.");
        }
        T temp = tail.getItem();
        if(head==tail) {
            head = tail = null;
        }else if(head.getNext() == tail) {
tail.remove();
tail = head;

        }else {
            tail = tail.getPrev();
        }
        count--;
        modChange++;
        return temp;
    }

    @Override
    public T remove(T element) throws NoSuchElementException{
        if(isEmpty()) {
            throw new NoSuchElementException("Element not found in List.");
        }if(head.getItem().equals(element)) {
            return removeFirst();
        }
        if(tail.getItem().equals(element)) {
            return removeLast();
        }
        for(DoubleLinearNode<T> current = head; current != null; current = current.getNext()){
            if(current.getItem().equals(element)){
                    count--;
                    modChange++;
                    return current.remove();
                }
                }
        throw new NoSuchElementException("Element not found.");
            }




    @Override
    public T first() throws NoSuchElementException{
        if(isEmpty()) {
            throw new NoSuchElementException("List is empty.");
        }
        return head.getItem();
    }

    @Override
    public T last() throws NoSuchElementException{
        if(isEmpty()) {
            throw new NoSuchElementException("List is empty.");
        }
        return tail.getItem();
    }

    @Override
    public boolean contains(T target) {
        if(isEmpty()) {
            return false;
        }
        if(head.getItem().equals(target)  || tail.getItem().equals(target)) {
            return true;
        }
        for(DoubleLinearNode<T> current = head; current != null; current.getNext()){
            if(current.getItem().equals(target)) {
                return true;
            }
            }
        return false;
    }

    @Override
    public boolean isEmpty() {
       if (count == 0) {
           return true;
       }
       return false;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator(head);
    }
    public String toString(){
    StringBuilder sb = new StringBuilder();
    for(DoubleLinearNode<T> current = head; current != null; current = current.getNext()) {
        sb.append(current.getItem()).append(" ");
    }
    return sb.toString();
    }


    private class ListIterator implements Iterator<T> {
        private DoubleLinearNode<T> current;
        private int modNum;

        public ListIterator(DoubleLinearNode<T> node) {
            this.modNum = modChange;
            this.current = node;
        }

        public boolean hasNext() {
            if (this.modNum != modChange) {
                throw new ConcurrentModificationException("This has been modified");

            }
            if (this.current != null) {
                return this.current.getNext() != null;
            } else {
                return false;
            }
        }

        public T next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.current = this.current.getNext();
            return this.current.getItem();
        }

        public void remove() {
            if (current == head) {
                modNum++;
                removeFirst();
                current = head;
            } else if (current == tail) {
                modNum++;
                removeLast();
                current = tail;
            } else {
                modNum++;
                modChange++;
                current = current.getNext();
                current.getPrev().remove();
            }
        }
    }
        }




    //The following three variables are a suggested start if you are using a list implementation.
    //protected int count;
    //protected int modChange;
    //protected DoubleLinearNode<T> head, tail;

    //TODO: implement this!
