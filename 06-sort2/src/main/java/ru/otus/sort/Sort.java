package ru.otus.sort;

import java.util.Arrays;

public class Sort {

    private static final int INSERTION_BORDER = 47;

    public static void mergeSort(int[] ints) {
        if (ints == null)
            throw new IllegalArgumentException();

        int[] res = Arrays.copyOf(ints, ints.length);
        mergesort(ints, 0, ints.length, res);
    }

    public static void insertion(int[] list, int begin, int end) {
        if (list == null)
            throw new IllegalArgumentException();

        for (int i = begin + 1; i < end; i++) {
            int j = i;
            int tmp = list[j];
            while (j > begin && list[j - 1] > tmp) {
                list[j] = list[--j];
            }
            list[j] = tmp;
        }
    }

    private static void mergesort(int[] ints, int from, int to, int[] res) {
        if (to - from > INSERTION_BORDER) {
            int middle = (from + to) / 2;
            mergesort(ints, from, middle, res);
            mergesort(ints, middle, to, res);
            merge(ints, from, middle, to, res);
        } else {
            insertion(ints, from, to);
        }
    }

    private static void merge(int[] ints, int begin, int middle, int end, int[] res) {
        int fst = begin, snd = middle;

        for (int j = begin; j < end; j++) {
            if (fst < middle && (snd >= end || ints[fst] < ints[snd])) {
                res[j] = ints[fst++];
            } else {
                res[j] = ints[snd++];
            }
        }
        System.arraycopy(res, begin, ints, begin, end - begin);
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