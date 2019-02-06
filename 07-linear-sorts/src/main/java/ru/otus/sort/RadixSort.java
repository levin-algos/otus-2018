package ru.otus.sort;


public class RadixSort {

    public enum Modes {
        LEAST_SIGN,
        PREFIX
    }

    public void sort(int[] array, RadixSort.Modes mode) {
        if (Modes.LEAST_SIGN == mode) {
            leastSort(array);
        } else if (Modes.PREFIX == mode) {
            prefixSort(array);
        } else {
            throw new IllegalArgumentException("Wrong mode");
        }

    }
    private void leastSort(int[] array) {

        int max = Common.max(array);

        for (int exp = 1; max / exp > 0; exp *= 10) {
            int[] counter = new int[10];
            for (int i : array) {
                counter[(i / exp) % 10]++;
            }

            for (int i = 1; i < counter.length; i++) {
                counter[i] += counter[i - 1];
            }

            int[] sorted = new int[array.length];

            for (int i = array.length - 1; i >= 0; i--) {
                int el = array[i];
                counter[(el / exp) % 10]--;
                sorted[counter[(el / exp) % 10]] = el;
            }

            System.arraycopy(sorted, 0, array, 0, sorted.length);
        }
    }

    private void prefixSort(int[] array) {
        PrefixTree trie = new PrefixTree(array);

        int[] traverse = trie.traverse();
        System.arraycopy(traverse, 0, array, 0, traverse.length);

    }
}