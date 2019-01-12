package ru.otus.algo;

import java.util.Arrays;

public class LongArray {
    public static LongArray of(int nbits) {
        return new LongArray(nbits);
    }

    private LongArray(int nbits) {
        if (nbits ==0)
            throw new IllegalArgumentException();

        words = new long[nbits/64 ==0 ? 1 : nbits/64];
    }
    public void set(int pos) {
        if (pos < 0)
            throw new IllegalArgumentException();

        int bin = calcBin(pos);
        ensureCapacity(bin);

        long tmp = 1L << (pos % 64);
        words[bin] |= tmp;
    }

    public boolean get(int pos) {
        if (pos < 0)
            throw new IllegalArgumentException();

        int bin = calcBin(pos);

        if (words.length <= bin)
            return false;

        return (words[bin] & (1L << (pos % 64))) != 0;
    }

    public int size() {
        int size = 0;
        for (long l: words) {
            size += Long.bitCount(l);
        }
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");

        for (int i = 0; i < words.length*64; i++) {
            if (get(i)) sb.append(i).append(" ");
        }
        return sb.append("]").toString();
    }

    private void ensureCapacity(int bin) {
        if (words.length <= bin) {
            resize(bin);
        }
    }

    private void resize(int bin) {
        words = Arrays.copyOf(words, bin+1);
    }

    private int calcBin(int pos) {
        return pos / 64;
    }

    private long[] words;
}
