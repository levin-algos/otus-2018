package ru.otus.sort;


class CountingSort
{

    void sort(int[] array, int k) {
        if (k < 1)
            throw new IllegalArgumentException();

        int[] counter = new int[k+1];
        for (int i: array) {
            counter[i]++;
        }
        for (int i=1; i< counter.length; i++) {
            counter[i] += counter[i-1];
        }

        int[] sorted = new int[array.length];

        for (int i= array.length-1; i>= 0; i--) {
            int el = array[i];
            counter[el]--;
            sorted[counter[el]] = el;
        }

        System.arraycopy(sorted, 0, array, 0, sorted.length);
    }
}