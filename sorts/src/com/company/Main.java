package com.company;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        //array to sort
        int[] arr = CreateArray(1000000);

        long start = System.currentTimeMillis();
        new QuickSort().run(arr);
        long finish = System.currentTimeMillis();
        System.out.println("Quick sort lasts " + (finish - start) + " ms");

        start = System.currentTimeMillis();
        new MergeSort().run(arr);
        finish = System.currentTimeMillis();
        System.out.println("Merge sort lasts " + (finish - start) + " ms");

        start = System.currentTimeMillis();
        new HeapSort().run(arr);
        finish = System.currentTimeMillis();
        System.out.println("Heap sort lasts " + (finish - start) + " ms");
    }

    static Random rnd = new Random();

    private static int[] CreateArray(int length){
        int[] arr = new int[length];
        for (int i = 0; i < length; i++){
            arr[i] = rnd.nextInt();
        }
        return arr;
    }

}
