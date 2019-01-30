package ru.otus.sort;

import java.util.Random;

/**
 * Static class for generating random sequences.
 * Generates three types of sequences:
 * <ul>
 *     <li>1. Random.</li>
 *     <li>2. Nearly sorted.</li>
 * </ul>
 */

@SuppressWarnings("ALL")
public final class Generator {

    /**
     * Generate ascending array with some permutations
     * Maximum delta of permutation is delta/2.
     * @param size - size of array
     * @param delta - permutation delta
     * @return - generated array
     */
    public static Integer[] generateNearlySorted(final int size,
                                                 final int delta) {
        if (size < 1)
            throw new IllegalArgumentException();

        Integer[] integers = new Integer[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            int shift = random.nextInt(delta) - delta / 2;
            integers[i] = i + shift;
        }
        return integers;
    }

    /**
     * Generates array of {@code size} filled with values in {@code [0, max]}.
     * @param size - size of array
     * @param max - max element of the array
     * @return - generated array
     */
    public static int[] generateRandom(int size, int max) {
        if (size < 0 || max < 1)
            throw new IllegalArgumentException();

        Random random = new Random();
        return random.ints(size, max, size).toArray();
    }
}