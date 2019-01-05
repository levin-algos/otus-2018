package ru.otus.algo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BArrayTest {

    @Test
    void testSplit() {
        BArray<String> array = new BArray<>("1", "2", "3");

        BArray<String> tail = array.split(1);
        assertArrayEquals(new String[]{"1", "2"}, array.toArray(new String[0]));
        assertArrayEquals(new String[]{"3"}, tail.toArray(new String[0]));
    }

    @Test
    void wrongSplit() {
        BArray<String> array = new BArray<>("1", "2", "3");

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> array.split(-1));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> array.split(2));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> array.split(3));
    }
}
