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
        Sort.times = 0;
        int[] random = Generator.generateRandom(ARRAY_SIZE, MAX_VALUE/2);
        Sort.mergeSort(random);
        System.out.println(Sort.times);
        assertTrue(Sort.isSorted(random));
    }

    @Test
    public void insertionTest() {
        int[] random = Generator.generateRandom(ARRAY_SIZE, MAX_VALUE/2);
        Sort.times = 0;
        Sort.insertion(random, 0, ARRAY_SIZE);
        System.out.println(Sort.times);
        assertTrue(Sort.isSorted(random));
    }

    @Test
    public void checkIsSortedWrong() {
        int[] ints = {1, 2, 3, 4, 5, 4, 6, 7, 8, 9};
        assertFalse(Sort.isSorted(ints));
    }

    @Test
    public void testGenerate() {
        int[] res = Generator.generateRandom(100, 50);
        assertEquals(100, res.length);
    }

}