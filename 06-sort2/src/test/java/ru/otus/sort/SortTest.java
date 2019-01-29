package ru.otus.sort;


import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * Unit test for simple Sort.
 */
public class SortTest {

    @Test
    public void mergeSort() {
        int[] ints = new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1};

        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, Sort.merge(ints));
    }

}
