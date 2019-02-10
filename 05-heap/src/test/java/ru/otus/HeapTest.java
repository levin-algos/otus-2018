package ru.otus;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class HeapTest {
    private Heap h;
    private final Integer[] integers = {1, 2, 3, 4, 5, 6, 7, 8, 9};

    @Test
    public void heapCreate() {
        Integer[] ints = Arrays.copyOf(this.integers, integers.length);
        h = new Heap<>(ints);
        assertNotNull(h);
        assertEquals(integers.length, h.size());
        testInvariant(ints);
    }

    @Test
    public void pollMaximum() {
        Integer[] ints = Arrays.copyOf(this.integers, integers.length);
        h = new Heap<>(ints);

        for (int i = integers.length - 1; i >= 0; i--) {
            assertEquals(integers[i], h.poll());
            assertTrue(testInvariant(ints));
            assertEquals(i, h.size());
        }
        assertEquals(0, h.size());
    }

    @Test
    public void remove() {
        Integer[] data = {1, 2, 3, 4, 1, 5, 6, 7, 8, 9};
        Heap<Integer> heap = new Heap<>(data);
        assertTrue(heap.remove(1));
        assertFalse(heap.remove(15));
        assertTrue(testInvariant(data));
        System.out.println(heap);
        for (int i = integers.length - 1; i >= 0; i--) {
            assertEquals(integers[i], heap.poll());
            assertTrue(testInvariant(data));
            assertEquals(i, heap.size());
        }
        assertEquals(0, heap.size());
    }

    @Test
    public void sort() {
        Integer[] ints = {9, 7, 5, 6, 8, 4, 3, 2, 1};
        Heap.sort(ints);
        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, ints);
    }

    @Test
    public void testDelete() {
        Integer[] arr = {33, 25, 12, 17, 24, 1, 7, 13, 15};
        Heap<Integer> heap = new Heap<>(arr);
        assertTrue(testInvariant(arr));

        heap.remove(1);
        assertTrue(testInvariant(arr));
    }

    private boolean testInvariant(Integer[] arr) {
        int size = arr.length;
        for (int i = 0; i < (size - 1) / 2; i++) {
            int left = i * 2 + 1, right = i * 2 + 2;

            if (left < size && arr[left] != null)
                if (arr[left].compareTo(arr[i]) > 0)
                    return false;

            if (right < size && arr[right] != null)
                if (arr[right].compareTo(arr[i]) > 0)
                    return false;
        }
        return true;
    }
}