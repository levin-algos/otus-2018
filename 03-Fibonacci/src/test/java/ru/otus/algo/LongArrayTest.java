package ru.otus.algo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LongArrayTest {

    @Test
    void wordsAdd() {
        LongArray arr = LongArray.of(128);

        for (int i = 0; i < 127; i+=2) {
            arr.set(i);
        }

        for (int i = 0; i < 127; i+=2) {
            assertTrue(arr.get(i));
            assertFalse(arr.get(i+1));
        }
    }
}
