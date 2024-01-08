package edu.ser222.m02_02;

/**
 * Implements various divide and conquer algorithms.
 *
 * Last updated 4/2/2022.
 *
 * Completion time: (1 sec, 115 ms)
 *
 * @author (Witt), Acuna, Sedgewick and Wayne
 * @verison (version)
 */

public class CompletedMerging implements MergingAlgorithms {

    //TODO: implement interface methods.
    public <T extends Comparable> Queue<T> mergeQueues(Queue<T> q1, Queue<T> q2) {
        Queue<Comparable> mergedQueue = new ListQueue<>();

        while (!q1.isEmpty() && !q2.isEmpty()) {

            Comparable item1 = q1.peek();
            Comparable item2 = q2.peek();

            if (less(item1, item2)) {
                mergedQueue.enqueue(q1.dequeue());
            } else {
                mergedQueue.enqueue(q2.dequeue());
            }
        }
        while (!q1.isEmpty()) {
            mergedQueue.enqueue(q1.dequeue());
        }
        while (!q2.isEmpty()) {
            mergedQueue.enqueue(q2.dequeue());
        }

        return (Queue<T>) mergedQueue;
    }




    public void sort(Comparable[] a) {
        int n = a.length;
        Comparable[] extra = new Comparable[n];
        sort(a,extra, 0 ,n-1);
    }






//fix this one
    @Override
    public Comparable[] mergesort(Comparable[] a) {
        int n = a.length;
    Comparable[] extra = new Comparable[n];
        sort(a,extra, 0 ,n-1);
return a;
    }
//fix this one
    @Override
    public Comparable[] merge(Comparable[] a, Comparable[] b) {
        int n = a.length;
        Comparable[] extra = new Comparable[n];
        sort(a,extra, 0 ,n-1);
       return a;
    }







    private static void sort (Comparable[] a, Comparable[] extra, int low, int high){
        if(high <= low){
            return;
        }
        int mid = low + (high-low)/2;
        sort(a,extra,low,mid);
        sort(a,extra,mid +1, high);
        merge(a,extra,low,mid,high);
    }


    public static void merge(Comparable[] a, Comparable[] extra, int low, int mid, int high) {
        for(int k = low; k<= high; k++){
            extra[k] = a[k];

        }
        int i = low;
        int j = mid +1;

        for(int k = low; k<= high; k++){
            if(i>mid) {
                a[k] = extra[j++];
            }else if(j>high) {
                a[k] = extra[i++];
            }else if( less(extra[i], extra[j])) {
                a[k] = extra[i++];
            }else{
                a[k] = extra[j++];
            }
        }
    }


    public void shuffle(Object[] a) {
        shuffle(a, 0, a.length-1);
    }
    private static void shuffle( Object[] a, int low, int high){
        if(high <= low){
            return;
        }
    }
     
    /**
     * entry point for sample output.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Queue<String> q1 = new ListQueue<>(); q1.enqueue("E"); q1.enqueue("L"); q1.enqueue("O"); q1.enqueue("R"); q1.enqueue("T");
        Queue<String> q2 = new ListQueue<>(); q2.enqueue("A"); q2.enqueue("E"); q2.enqueue("M"); q2.enqueue("P"); q2.enqueue("S"); q2.enqueue("X");
        Queue<Integer> q3 = new ListQueue<>(); q3.enqueue(5); q3.enqueue(12); q3.enqueue(15); q3.enqueue(17); q3.enqueue(20);
        Queue<Integer> q4 = new ListQueue<>(); q4.enqueue(1); q4.enqueue(4); q4.enqueue(12); q4.enqueue(13); q4.enqueue(16); q4.enqueue(18);

        MergingAlgorithms ma =  new CompletedMerging();

        //Q1 - sample test cases
        Queue merged1 = ma.mergeQueues(q1, q2);
        System.out.println(merged1.toString());
        Queue merged2 = ma.mergeQueues(q3, q4);
        System.out.println(merged2.toString());
        
        //Q2 - sample test cases
        String[] a = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        ma.sort(a);
        assert isSorted(a);
        show(a);
        
        //Q3 - sample test cases
        String[] b = {"S", "O", "R", "T", "E", "X", "A", "M", "P", "L", "E"};
        ma.shuffle(b);
        show(b);
        
        ma.shuffle(b);
        show(b);
    }

    //below are utilities functions, please do not change them.
        
    //sorting helper from text
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    //sorting helper from text
    private static void show(Comparable[] a) {
        for (Comparable a1 : a)
            System.out.print(a1 + " ");

        System.out.println();
    }
    
    //sorting helper from text
    public static boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++)
            if (less(a[i], a[i-1]))
                return false;
        
        return true;
    }
}