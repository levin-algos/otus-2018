package ru.otus.algo;

import java.util.Arrays;

public class QuickSelect {
    public static int select(int[] ints, int from, int to, int k) {
        int pivot = getMedian(ints, from, to, k);
        int center = partition(ints, from, to, pivot);
        if (k > center) {
            return select(ints, center, to, k);
        } else if (k == center) {
            return ints[k];
        } else {
            return select(ints, from, center, k);
        }
    }

    private static int partition(int[] ints, int from, int to, int pivot) {
        int pId = from;
        while (ints[pId] != pivot && pId < to) {
            pId++;
        }
        swap(ints, pId, to - 1);
        int i = from - 1;
        for (int j = from; j <= to - 2; j++) {
            if (ints[j] < pivot) {
                i++;
                swap(ints, j, i);
            }
        }
        swap(ints, i + 1, to - 1);
        return i + 1;
    }

    private static void swap(int[] ints, int left, int right) {
        int tmp = ints[right];
        ints[right] = ints[left];
        ints[left] = tmp;
    }

    private static int getMedian(int[] ints, int from, int to, int k) {
        int i;
        int interval = 5;
        for (i = from + interval; i < to; i += interval) {
            Arrays.sort(ints, i - interval, i);
        }
        Arrays.sort(ints, i - interval, to);

        int length = to - from;
        if (length < interval) {
            return ints[(length / 2) + from];
        }
        int[] A = new int[(length + 2) / interval];
        int count = 0;
        for (int j = from + 2; j < to; j += interval) {
            A[count++] = ints[j];
        }
        return getMedian(A, 0, A.length, A.length / 2);
    }
}