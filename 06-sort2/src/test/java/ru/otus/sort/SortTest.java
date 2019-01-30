package ru.otus.sort;

import org.junit.Test;

import static org.junit.Assert.*;

public class SortTest {

    private static final int ARRAY_SIZE = 10000;
    private static final int MAX_VALUE = 10000;

    @Test
    public void checkIsSorted() {
        int[] ints = {1, 2, 3, 4, 4, 5, 6, 7, 8, 9};
        assertTrue(Sort.isSorted(ints));
    }

    @Test
    public void checkOnLargeArray() {
        int[] random = Generator.generateRandom(ARRAY_SIZE, MAX_VALUE/2);
        Sort.mergeSort(random);
        assertTrue(Sort.isSorted(random));
    }

    @Test
    public void insertionTest() {
        int[] random = Generator.generateRandom(ARRAY_SIZE, MAX_VALUE/2);
        Sort.insertion(random, 0, ARRAY_SIZE);
        assertTrue(Sort.isSorted(random));
    }

    @Test
    public void checkIsSortedWrong() {
        int[] ints = {1, 2, 3, 4, 5, 4, 6, 7, 8, 9};
        assertFalse(Sort.isSorted(ints));
    }
}