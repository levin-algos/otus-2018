package ru.otus.sort;

import org.junit.Test;

import static org.junit.Assert.*;

public class RadixSortTest {
    private final int size = 1000000;
    @Test
    public void leastSignificantSort() {
        RadixSort radixSort = new RadixSort();

        int[] array = Common.generateRandom(size, size/2);

        radixSort.sort(array, RadixSort.Modes.LEAST_SIGN);

        assertTrue(Common.isSorted(array));
    }

    @Test
    public void trieSort() {
        RadixSort radixSort = new RadixSort();
        int[] array = Common.generateRandom(size, size/2);

        radixSort.sort(array, RadixSort.Modes.PREFIX);

        assertTrue(Common.isSorted(array));
    }
}