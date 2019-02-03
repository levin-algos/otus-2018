package ru.otus.sort;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Arrays;

public class CountingSortTest
{
    private static final int MAX_VALUE = 5;

    @Test
    public void sorting() {
        int[] ints = new int[] {5, 4, 3, 2, 1};

        CountingSort sort = new CountingSort();
        sort.sort(ints, MAX_VALUE);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, ints);
    }
}
