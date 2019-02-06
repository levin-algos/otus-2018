package ru.otus.sort;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CountingSortTest
{
    private static final int MAX_VALUE = 5;

    @Test
    public void sorting() {
        int[] data = new int[] {5, 4, 3, 2, 1};

        CountingSort sort = new CountingSort();
        sort.sort(data, MAX_VALUE);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, data);
    }

    @Test
    public void largeData() {
        CountingSort sort = new CountingSort();
        int[] data = Common.generateRandom(100000, MAX_VALUE);
        sort.sort(data, MAX_VALUE);
        assertTrue(Common.isSorted(data));
    }
}