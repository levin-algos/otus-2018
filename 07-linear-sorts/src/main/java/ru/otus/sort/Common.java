package ru.otus.sort;

import java.util.Random;

class Common {

    static final int[] POWER_OF_10 = new int[]{1, 10, 100, 1000, 10_000, 100_000, 1_000_000,
            10_000_000, 100_000_000, 1_000_000_000};

    static int max(int[] array) {
        int max = Integer.MIN_VALUE;
        for (int i : array) {
            if (max < i)
                max = i;
        }
        return max;
    }

    static int getNumberOfDigits(int n) {
        if (n < 100_000) {
            if (n < 100) {
                return (n < 10) ? 1 : 2;
            } else {
                if (n < 1_000)
                    return 3;
                else {
                    return (n < 10_000) ? 4 : 5;
                }
            }
        } else {
            if (n < 10_000_000) {
                return (n < 1_000_000) ? 6 : 7;
            } else {
                if (n < 100_000_000)
                    return 8;
                else {
                    return (n < 1_000_000_000) ? 9 : 10;
                }
            }
        }
    }

    public static int[] generateRandom(int size, int max) {
        if (size < 0 || max < 1)
            throw new IllegalArgumentException();

        Random random = new Random();
        return random.ints(size, 0, max).toArray();
    }

    public static boolean isSorted(int[] list) {
        if (list == null)
            throw new IllegalArgumentException();

        int current = Integer.MIN_VALUE;
        for (int el : list) {
            if (current > el)
                return false;
            current = el;
        }
        return true;
    }
}
