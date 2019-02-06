package ru.otus.sort;

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
            // 5 or less
            if (n < 100) {
                // 1 or 2
                return (n < 10) ? 1 : 2;
            } else {
                // 3 or 4 or 5
                if (n < 1_000)
                    return 3;
                else {
                    // 4 or 5
                    return (n < 10_000) ? 4 : 5;
                }
            }
        } else {
            // 6 or more
            if (n < 10_000_000) {
                // 6 or 7
                return (n < 1_000_000) ? 6 : 7;
            } else {
                // 8 to 10
                if (n < 100_000_000)
                    return 8;
                else {
                    // 9 or 10
                    return (n < 1_000_000_000) ? 9 : 10;
                }
            }
        }
    }
}
