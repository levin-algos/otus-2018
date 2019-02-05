package ru.otus.sort;


public class CountingSort
{

    public void sort(int[] ints, int k) {
        if (k < 1)
            throw new IllegalArgumentException();

        int[] counter = new int[k+1];
        for (int i: ints) {
            counter[i]++;
        }
        for (int i=1; i< counter.length; i++) {
            counter[i] += counter[i-1];
        }

        int[] sorted = new int[ints.length];

        for (int i= ints.length-1; i>= 0; i--) {
            int el = ints[i];
            counter[el]--;
            sorted[counter[el]] = el;
        }

        System.arraycopy(sorted, 0, ints, 0, sorted.length);
    }
}