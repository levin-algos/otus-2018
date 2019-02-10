package ru.otus.algo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Arrays;

public class QuickSelectTest
{
    @Test
    public void simpleTest()
    {
        int size = 10000;
        int[] ints = generateDecreasingSeq(size);
        for (int i = 0; i < size; i++) {
            assertEquals(""+i, i, QuickSelect.select(Arrays.copyOf(ints, size), 0, size,i));
        }
    }

    private int[] generateDecreasingSeq(int i) {
        int[] ints = new int[i];
        for (int j = i-1; j > 0; j--) {
            ints[i-j-1] = j;
        }
        return ints;
    }
}
