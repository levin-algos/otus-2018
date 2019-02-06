package ru.otus.sort;

import static org.junit.Assert.assertArrayEquals;

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
}
