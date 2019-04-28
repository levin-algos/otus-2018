package ru.otus.algo;

import org.junit.jupiter.api.Test;
import ru.otus.algo.common.DArray;
import ru.otus.algo.common.DynamicArray;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class DListTest {

    private static final int ARRAY_SIZE = 10000;
    private static final int MAX_VALUE = 10000;

    private static final DynamicArray<Integer> arr = new DArray<>();

    @Test
    void checkIsSorted() {
        Integer[] ints = {1, 2, 3, 4, 4, 5, 6, 7, 8, 9};
        assertTrue(Generator.isSorted(ints));
    }

    @Test
    void checkOnLargeArray() {
        Integer[] random = Generator.generateRandom(ARRAY_SIZE, MAX_VALUE / 2);
        for (Integer i : random) {
            arr.add(i);
        }
        arr.sort(Comparator.naturalOrder());
        assertTrue(Generator.isSorted(arr.toArray(new Integer[0])));
    }

    @Test
    void checkIsSortedWrong() {
        Integer[] ints = {1, 2, 3, 4, 5, 4, 6, 7, 8, 9};
        assertFalse(Generator.isSorted(ints));
    }

    @Test
    void testGenerate() {
        Integer[] res = Generator.generateRandom(100, 50);
        assertEquals(100, res.length);
    }

}
