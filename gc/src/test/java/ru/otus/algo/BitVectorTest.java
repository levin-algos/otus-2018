package ru.otus.algo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BitVectorTest {

    @Test
    void wordsAdd() {
        BitVector arr = BitVector.of(1);

        for (int i = 0; i < 127; i+=2) {
            arr.set(i);
        }

        for (int i = 0; i < 127; i+=2) {
            assertTrue(arr.get(i));
            assertFalse(arr.get(i+1));
        }
    }

    @Test
    void clear() {
        BitVector arr = BitVector.of(256);

        for (int i=0; i < 256; i++) {
            arr.set(i);
        }

        for (int i = 0; i < 256; i+=2) {
            arr.clear(i);
        }

        for (int i = 0; i< 256; i+=2) {
            assertFalse(arr.get(i));
            assertTrue(arr.get(i+1));
        }
    }


    @Test
    void insert64Test() {
        BitVector arr = BitVector.of(256);

        for (int i=0; i < 256; i++) arr.set(i);

        arr.insertBlock(128, 64);

        checkTrue(0, 128, arr);
        checkFalse(128, 192, arr);
        checkTrue(192, 320, arr);
    }

    @Test
    void insertTest() {
        BitVector arr = BitVector.of(256);

        for (int i=0; i < 256; i++) arr.set(i);
//        arr.insertBlock(32, 2);

        arr.insertBlock(0, 31);

        checkFalse(0, 31, arr);
        checkTrue(32, 287, arr);
        checkFalse(287, 300, arr);

    }

    @Test
    void isZeroBitTest() {
        BitVector arr = BitVector.of(1024);

        arr.set(1025);

        assertTrue(arr.isZeroBits(0, 63));
        assertTrue(arr.isZeroBits(0, 64));
        assertTrue(arr.isZeroBits(13, 116));
        assertTrue(arr.isZeroBits(0, 1024));
        assertFalse(arr.isZeroBits(0, 1025));

    }

    private void checkTrue(int from, int to, BitVector v) {
        for (int i = from; i< to; i++) {
            assertTrue(v.get(i));
        }
    }

    private void checkFalse(int from, int to, BitVector v) {
        for (int i = from; i< to; i++) {
            assertFalse(v.get(i));
        }
    }
}
