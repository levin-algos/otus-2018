package ru.otus;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class HeapTest
{
    private Heap h;
    private final Integer[] integers = {1, 2, 3, 4, 5, 6, 7, 8, 9};

    @Before
    public void prepare() {
        h = new Heap<>(Arrays.copyOf(integers, integers.length));
    }

    @Test
    public void heapCreate() {
        assertNotNull(h);
        assertEquals(integers.length, h.size());
    }

    //TODO: add tests for heapify
    @Test
    public void pollMaximum() {
        for (int i = integers.length-1; i>=0; i--) {
            assertEquals(integers[i], h.poll());
            assertEquals(i, h.size());
        }
        assertEquals(0, h.size());
    }

    @Test
    public void sort() {
        Integer[] ints = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        Heap.sort(ints);
        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, ints);
    }
}