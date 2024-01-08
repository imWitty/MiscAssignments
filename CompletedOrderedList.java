package edu.ser222.m01_03;

/**
 * CompletedOrderedList represents an implementation of an ordered list that builds on
 * CompletedList.
 *
 * @author (Witt), Acuna
 * @version (version)
 */
public class CompletedOrderedList<T extends Comparable<T>> extends CompletedList<T>
         implements OrderedListADT<T> {
    public CompletedOrderedList() {
        super();
    }

    @Override
    public void add(T element) throws NullPointerException {
        if (element == null) {
            throw new NullPointerException("Input not Accepted.");
        }
        if (isEmpty()) {
            head = new DoubleLinearNode<>(element);
            tail = head;
            count++;
            modChange++;
        } else {
            count++;
            modChange++;
            DoubleLinearNode<T> front = head;
            DoubleLinearNode<T> back = tail;
            DoubleLinearNode<T> newNode = new DoubleLinearNode<>(element);

            if (element.compareTo(front.getItem()) <= 0) {
                newNode.setNext(front);
                head = newNode;
                return;

            } else if (element.compareTo(back.getItem()) >= 0) {
                newNode.setPrev(back);
                tail = newNode;
                return;
            }
            if (back.getPrev() == null) {
                System.out.println("Back is null");
            }

            switch (element.compareTo(front.getItem())) {
                case -1:
                    front.getPrev().setNext(newNode);
                    newNode.setNext(front);
                    break;
                case 0:
                    newNode.setPrev(front.getPrev());
                    front.setPrev(newNode);
                    newNode.setNext(front);
                    break;
                case 1:
                    front.getNext().setPrev(newNode);
                    newNode.setPrev(back);
                    break;
            }
        }
    }
    public boolean contains(T target){
        if(isEmpty()) {
            return false;
        }
            DoubleLinearNode<T> front = head;
            DoubleLinearNode<T> back = tail;
            if(target.compareTo(front.getItem())==0){
                return true;
            }
            if(target.compareTo(back.getItem())==0){
                return true;
            }
            while((front = front.getNext()) != (back = back.getPrev())){
                if(target.compareTo(front.getItem())==0){
                    return true;
                }if(target.compareTo(back.getItem())==0){
                    return true;
                }
            }

        return false;
    }
}
    //TODO: implement this!


