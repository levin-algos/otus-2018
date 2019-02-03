package ru.otus.sort;

import java.util.Arrays;
import java.util.concurrent.ForkJoinTask;

public class MergeSort {

    private final int INSERTION_BORDER;
    private final int FORK_BORDER;

    public MergeSort(int insertionBound, int forkBound) {
        INSERTION_BORDER = insertionBound;
        FORK_BORDER = forkBound;
    }

    public void mergeSort(int[] ints) {
        if (ints == null)
            throw new IllegalArgumentException();

        int[] res = Arrays.copyOf(ints, ints.length);
        mergesort(ints, 0, ints.length, res);
    }

    private void mergesort(int[] ints, int from, int to, int[] res) {
        int len = to - from;
        if (len > INSERTION_BORDER) {
            int middle = (from + to) / 2;

            if (len < FORK_BORDER) {
                mergesort(ints, from, middle, res);
                mergesort(ints, middle, to, res);
            } else {
                ForkJoinTask<?> adapt = ForkJoinTask.adapt(() -> mergesort(ints, from, middle, res));
                adapt.fork();
                mergesort(ints, middle, to, res);
                adapt.join();
            }

            merge(ints, from, middle, to, res);
        } else {
            for (int i = from + 1; i < to; i++) {
                int j = i;
                int tmp = ints[j];
                while (j > from && ints[j - 1] > tmp) {
                    ints[j] = ints[--j];
                }
                ints[j] = tmp;
            }
        }
    }

    private void merge(int[] ints, int begin, int middle, int end, int[] res) {
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