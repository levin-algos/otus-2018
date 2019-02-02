package ru.otus.sort;

import org.junit.Test;

import static org.junit.Assert.*;

public class MergeSortTest {

    private static final int ARRAY_SIZE = 10000;
    private static final int MAX_VALUE = 10000;

    private static final MergeSort sorter = new MergeSort(47, 8192);

    @Test
    public void checkIsSorted() {
        int[] ints = {1, 2, 3, 4, 4, 5, 6, 7, 8, 9};
        assertTrue(MergeSort.isSorted(ints));
    }

    @Test
    public void checkOnLargeArray() {
        int[] random = Generator.generateRandom(ARRAY_SIZE, MAX_VALUE/2);
        sorter.mergeSort(random);
        assertTrue(MergeSort.isSorted(random));
    }

    @Test
    public void checkIsSortedWrong() {
        int[] ints = {1, 2, 3, 4, 5, 4, 6, 7, 8, 9};
        assertFalse(MergeSort.isSorted(ints));
    }

    @Test
    public void testGenerate() {
        int[] res = Generator.generateRandom(100, 50);
        assertEquals(100, res.length);
    }

}