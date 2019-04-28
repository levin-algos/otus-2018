package ru.otus.algo;

import java.util.Arrays;

/**
 * Array of booleans compressed into long array.
 * Each bit in long array represents boolean at specified position.
 *
 * Example:
 * BitVector vec = BitVector.of(12);
 * vec.set(10);
 *
 * is equal to:
 * boolean[] vec = new boolean[12];
 * vec[10] = true;
 *
 */
public class BitVector {
    public static BitVector of(int nbits) {
        return new BitVector(nbits);
    }

    private BitVector(int nbits) {

        if (nbits == 0)
            throw new IllegalArgumentException();

        words = new long[nbits / WORD_LENGTH == 0 ? 1 : nbits / WORD_LENGTH];
    }

    /**
     * Set true at {@code pos} position of bit vector.
     * If {@code pos} &lt; 0 IllegalArgumentException will be thrown.
     * Function has no upper bound for {@code pos}.
     * @param pos - position to write true in bit vector.
     */
    public void set(int pos) {
        if (pos < 0)
            throw new IllegalArgumentException();

        int wordNum = getBin(pos);
        ensureCapacity(wordNum);

        long tmp = 1L << pos % WORD_LENGTH;
        words[wordNum] |= tmp;
    }

    /**
     * Gets boolean value at {@code pos} position from bit vector.
     * If {@code pos} &lt; 0 IllegalArgumentException will be thrown.
     * Function has no upper bound for {@code pos}.
     * @param pos - position to write true in bit vector.
     * @return true if bit at {@code pos} is 1, else false
     */
    public boolean get(int pos) {
        if (pos < 0)
            throw new IllegalArgumentException();

        int bin = getBin(pos);

        if (words.length <= bin)
            return false;

        return (words[bin] & (1L << (pos % WORD_LENGTH))) != 0;
    }

    /**
     * Writes 0 bit at {@code pos} in bit vector.
     * If {@code pos} &lt; 0 IllegalArgumentException will be thrown.
     * @param pos - position on bit to clear.
     */
    public void clear(int pos) {
        if (pos < 0)
            throw new IllegalArgumentException();

        int bin = getBin(pos);

        if (words.length <= bin)
            return;

        words[bin] &= ~(1L << (pos % WORD_LENGTH));
    }

    /**
     * Shifts elements staring from {@code pos} to the right and inserts {@code blockSize} 0 bits in bit vector.
     * If {@code pos} or {@code blockSize} &lt; 0 IllegalArgumentException will be thrown.
     * @param pos - starting position of insertion
     * @param blockSize - size of 0 bit block.
     */
    public void insertBlock(int pos, int blockSize) {
        if (pos < 0 || blockSize < 1)
            throw new IllegalArgumentException();

        int bin = getBin(pos);

        if (words.length <= bin)
            return;

        int modSize = blockSize % WORD_LENGTH;
        int modPos = pos % WORD_LENGTH;

        if (modPos != 0 || modSize != 0) {
            long mask = getMask(words[bin], modSize);
            words[bin] = insertZeroBits(words[bin], modPos, modSize);

            for (int i = bin + 1; i < words.length; i++) {
                long cur = words[i];
                words[i] = (cur << modSize) | mask;
                mask = getMask(cur, modSize);
            }
            if (mask != 0) {
                words = Arrays.copyOf(words, words.length + 1);
                words[words.length - 1] = mask;
            }
        }

        if (blockSize >= WORD_LENGTH) {
            int blocks = blockSize / WORD_LENGTH;
            long[] newArr = new long[words.length + blocks];
            System.arraycopy(words, 0, newArr, 0, bin);
            System.arraycopy(words, bin, newArr, bin + blocks, words.length - bin);
            words = newArr;
        }
    }

    private long getMask(long word, int size) {
        return word >>> (WORD_LENGTH - size);
    }

    // 0001 0000 0000
    /*
        8 2
        i = 10
        left =   0
        right =
     */
    private long insertZeroBits(long word, int pos, int size) {
        long left = (word >>> pos) << (pos +size);
        int j = WORD_LENGTH - pos;
        long right = pos == 0 ? 0 : (word << j) >>> j;
        return left | right;
    }

    public boolean isZeroBits(int from, int to) {
        checkRange(from, to);

        if (from == to)
            return true;

        int fromBin = getBin(from), toBin = getBin(to);

        if (fromBin > words.length)
            return true;

        int modFrom = from % WORD_LENGTH;
        int modTo = to % WORD_LENGTH;
        if (fromBin == toBin) {
            return (words[fromBin] << (WORD_LENGTH - modTo)) >>> (WORD_LENGTH - modTo + modFrom) == 0;
        } else {
            long f = words[fromBin] >>> modFrom;
            int bin = fromBin+1;
            if (f != 0)
                return false;
            while (bin < toBin) {
                if (words[bin++] != 0)
                    return false;
            }
            if (toBin >= words.length)
                return true;
            return (words[toBin] << (63 - modTo) == 0);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");

        for (int i = 0; i < words.length * WORD_LENGTH; i++) {
            if (get(i)) sb.append(i).append(" ");
        }
        return sb.append("]").toString();
    }

    private void checkRange(int from, int to) {
        if (from < 0 || to < 0 || from > to)
            throw new IllegalArgumentException();
    }

    private void ensureCapacity(int bin) {
        if (words.length <= bin) {
            resize(bin + 1);
        }
    }

    private void resize(int newSize) {
        words = Arrays.copyOf(words, newSize);
    }

    private int getBin(int pos) {
        return pos / WORD_LENGTH;
    }

    private long[] words;
    private static final int WORD_LENGTH = 64;
}