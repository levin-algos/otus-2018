package ru.otus.sort;

import org.junit.Test;

import static org.junit.Assert.*;

public class RadixSortTest {

    @Test
    public void leastSignificantSort() {
        RadixSort radixSort = new RadixSort();
        int[] array = new int[]{123, 45, 678, 901};

        radixSort.sort(array, RadixSort.Modes.LEAST_SIGN);

        assertArrayEquals(new int[]{45, 123, 678, 901}, array);
    }

    @Test
    public void trieSort() {
        RadixSort radixSort = new RadixSort();
        int[] array = new int[] { 123, 45, 678, 901};

        radixSort.sort(array, RadixSort.Modes.PREFIX);

        assertArrayEquals(new int[]{45, 123, 678, 901}, array);
    }

}