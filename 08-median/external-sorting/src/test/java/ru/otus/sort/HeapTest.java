package ru.otus.sort;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class HeapTest {
    private Heap<Integer> h;
    private final Integer[] integers = {9, 8, 7, 6, 5, 4, 3, 2, 1};

    @Test
    public void heapCreate() {
        Integer[] ints = Arrays.copyOf(this.integers, integers.length);
        h = new Heap<>(ints);
        assertNotNull(h);
        assertEquals(integers.length, h.size());
        testInvariant(ints);
    }

    @Test
    public void heapCreateWithEmptyConstructor() {
        h = new Heap<>();
        assertNotNull(h);
        for (Integer i: integers) {
            h.add(i);
            assertTrue(h.toString(), testInvariant(h.toArray(new Integer[0])));
        }
        assertEquals(integers.length, h.size());
        testInvariant(integers);

        for (int i = integers.length-1; i >= 0; i--) {
            assertEquals(integers[i], h.poll());
            assertTrue(h.toString(), testInvariant(h.toArray(new Integer[0])));
            assertEquals(i, h.size());
        }
        assertEquals(0, h.size());

    }

    @Test
    public void poll() {
        h = new Heap<>(integers);

        for (int i = integers.length-1; i >=0; i--) {
            assertEquals(integers[i], h.poll());
            assertTrue(testInvariant(h.toArray(new Integer[0])));
            assertEquals(i, h.size());
        }
        assertEquals(0, h.size());
    }

    @Test
    public void remove() {
        Heap<Integer> heap = new Heap<>(integers);
        assertTrue(heap.remove(1));
        assertFalse(heap.remove(15));
        assertTrue(testInvariant(heap.toArray(new Integer[0])));
        System.out.println(heap);
        for (int i = integers.length-2; i >= 0 ; i--) {
            assertEquals(integers[i], heap.poll());
            assertTrue(testInvariant(heap.toArray(new Integer[0])));
            assertEquals(i, heap.size());
        }
        assertEquals(0, heap.size());
    }

    @Test
    public void testDelete() {
        Integer[] arr = {33, 25, 12, 17, 24, 1, 7, 13, 15};
        Heap<Integer> heap = new Heap<>(arr);
        assertTrue(testInvariant(heap.toArray(new Integer[0])));

        heap.remove(1);
        assertTrue(testInvariant(heap.toArray(new Integer[0])));
    }

    private boolean testInvariant(Integer[] arr) {
        int size = arr.length;
        for (int i = 0; i < (size - 1) / 2; i++) {
            int left = i * 2 + 1, right = i * 2 + 2;

            if (left < size && arr[left] != null)
                if (arr[left].compareTo(arr[i]) < 0)
                    return false;

            if (right < size && arr[right] != null)
                if (arr[right].compareTo(arr[i]) < 0)
                    return false;
        }
        return true;
    }
}