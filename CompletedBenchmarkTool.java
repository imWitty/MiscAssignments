package edu.ser222.m02_01;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * (basic description of the program or class)
 * 
 * Completion time: (estimation of hours spent on this program)
 *
 * @author (Witt), Acuna, Sedgewick
 * @version (a version number or a date)
 */


public class CompletedBenchmarkTool implements BenchmarkTool {
    
    /***************************************************************************
     * START - SORTING UTILITIES, DO NOT MODIFY (FROM SEDGEWICK)               *
     **************************************************************************/
    
    public static void insertionSort(Comparable[] a) {
        int N = a.length;
        
        for (int i = 1; i < N; i++)
        {
            // Insert a[i] among a[i-1], a[i-2], a[i-3]... ..          
            for (int j = i; j > 0 && less(a[j], a[j-1]); j--)
                exch(a, j, j-1);
        }
    }
    
    
    public static void shellSort(Comparable[] a) {
        int N = a.length;
        int h = 1;
        
        while (h < N/3) h = 3*h + 1; // 1, 4, 13, 40, 121, 364, 1093, ...
        
        while (h >= 1) {
            // h-sort the array.
            for (int i = h; i < N; i++) {
                // Insert a[i] among a[i-h], a[i-2*h], a[i-3*h]... .
                for (int j = i; j >= h && less(a[j], a[j-h]); j -= h)
                exch(a, j, j-h);
            }
            h = h/3;
        }
    }
    
    
    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }
    
    
    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i]; a[i] = a[j]; a[j] = t;
    }
    
    /***************************************************************************
     * END - SORTING UTILITIES, DO NOT MODIFY                                  *
     **************************************************************************/

    //TODO: implement interface methods.

    public static void main(String args[]) {
        BenchmarkTool me = new CompletedBenchmarkTool();
        int size = 4096;
        
        //NOTE: feel free to change size here. all other code must go in the
        //      methods.
        
        me.runBenchmarks(size);
    }

    @Override
    public Integer[] generateTestDataBinary(int size) {
        Integer[] halfZero_halfOne = new Integer[size];
        int index = 0;
        for(int i=0;i<size;i++) {
            if(i==size/2) {
                index++;
            }
            halfZero_halfOne[i] = index;
        }
        return halfZero_halfOne;
    }

    @Override
    public Integer[] generateTestDataHalves(int size) {
        Integer[] increasingHalf = new Integer[size];
        int tempSize = size/2;
        int index = 0;
        for(int i=0;i<size;i++) {
            if(i==tempSize) {
                index++;
                tempSize += ((size-tempSize)/2);
            }
            increasingHalf[i] = index;
        }
        return increasingHalf;
    }

    @Override
    public Integer[] generateTestDataHalfRandom(int size) {
        Integer[] halfZero_halfRandom = new Integer[size];
        int index = 0;
        for(int i=0;i<size;i++) {
            if(i>size/2) {
                Random rand = new Random();
                index = rand.nextInt();
            }
            halfZero_halfRandom[i] = index;
        }
        return halfZero_halfRandom;
    }

    @Override
    public double computeDoublingFormula(double firstTime, double secondTime) {
        return logBase2(firstTime/secondTime);
    }
    private static double logBase2(double totalT) {
        return Math.log(totalT)/Math.log(2.0);
    }
    @Override
    public double benchmarkInsertionSort(Integer[] smallFirst, Integer[] bigSecond) {
        Stopwatch timer = new Stopwatch();
        insertionSort(smallFirst);
        double smallTime = timer.elapsedTime();
        timer = new Stopwatch();
        insertionSort(bigSecond);
        double largeTime = timer.elapsedTime();
        return computeDoublingFormula(largeTime, smallTime);
    }

    @Override
    public double benchmarkShellsort(Integer[] smallFirst, Integer[] bigSecond) {
        Stopwatch timer = new Stopwatch();
        shellSort(smallFirst);
        double smallTime = timer.elapsedTime();
        timer = new Stopwatch();
        shellSort(bigSecond);
        double largeTime = timer.elapsedTime();
        return computeDoublingFormula(largeTime, smallTime);
    }

    @Override
    public void runBenchmarks(int size) {
        Integer[] halfBinary = generateTestDataBinary(size);
        Integer[] halfBinaryLarge = generateTestDataBinary(size*2);

        Integer[] halfIncrement = generateTestDataHalves(size);
        Integer[] halfIncrementLarge = generateTestDataHalves(size*2);

        Integer[] halfRandom = generateTestDataHalfRandom(size);
        Integer[] halfRandomLarge = generateTestDataHalfRandom(size*2);


        DecimalFormat df = new DecimalFormat("#.###");


        double binInsertion = benchmarkInsertionSort(halfBinary,halfBinaryLarge);
        double halfInsertion = benchmarkInsertionSort(halfIncrement,halfIncrementLarge);
        double randIntInsertion = benchmarkInsertionSort(halfRandom,halfRandomLarge);

        double binShell = benchmarkShellsort(halfBinary,halfBinaryLarge);
        double halfShell = benchmarkShellsort(halfIncrement,halfIncrementLarge);
        double randIntShell = benchmarkShellsort(halfRandom,halfRandomLarge);

        System.out.println("        Insertion     ShellSort");
        System.out.println("Bin       " + df.format(binInsertion) + "        " + df.format(binShell));
        System.out.println("Half      " +df.format(halfInsertion) +"        " +df.format(halfShell));
        System.out.println("RanInt   " +df.format(randIntInsertion) +"        " + df.format(randIntShell));
    }
}