package ru.otus.algo.common;

import java.util.Random;

public class Utils {
    private Utils() {}


    public static Integer[] generateRandom(int size, int max) {
        if (size < 0 || max < 1)
            throw new IllegalArgumentException();

        Random random = new Random();
        return random.ints(size, 0, max).boxed().toArray(Integer[]::new);
    }

    public static boolean isSorted(Integer[] list) {
        if (list == null)
            throw new IllegalArgumentException();

        if (list.length == 0)
            return true;

        int current = Integer.MIN_VALUE;
        for (int el : list) {
            if (current > el)
                return false;
            current = el;
        }
        return true;
    }
}