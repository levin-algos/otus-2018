package ru.otus.sort;

/**
 * Hello world!
 *
 */
public class Sort
{

    private static final int MERGE_CONST = 2;

    public static int[] merge(int[] ints) {
        if (ints == null)
            throw new IllegalArgumentException();

        int[] res = new int[ints.length];
        return mergesort(ints, 0, ints.length, res);
    }

    private static int[] mergesort(int[] ints, int from, int to, int[] res) {
        int middle = (from + to) / 2;
        if (to - from > MERGE_CONST) {
            res = mergesort(ints, from, middle-1, res);
            res = mergesort(ints, middle, to, res);
        }
        return _merge(ints, from, middle, to, res);
    }

    private static int[] _merge(int[] ints, int begin, int middle, int end, int[] res) {
        int fst = begin, snd = middle;

        for (int i=begin; i < end; i++) {
            if (fst <= middle && (snd >= end || ints[fst] < ints[snd])) {
                res[i] = ints[fst++];
            } else {
                res[i] = ints[snd++];
            }
        }

        return res;
    }
}
