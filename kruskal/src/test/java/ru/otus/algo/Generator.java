package ru.otus.algo;

import java.util.Random;

class Generator {

    private Generator() {}


    /**
     * Generates array of {@code size} filled with values in {@code [0, max]}.
     * @param size - size of array
     * @param max - max element of the array
     * @return - generated array
     */
    static Integer[] generateRandom(int size, int max) {
        if (size < 0 || max < 1)
            throw new IllegalArgumentException();

        Random random = new Random();
        return random.ints(size, max, size).boxed().toArray(Integer[]::new);
    }

    static boolean isSorted(Integer[] list) {
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
