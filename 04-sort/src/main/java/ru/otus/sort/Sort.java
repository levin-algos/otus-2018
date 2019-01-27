package ru.otus.sort;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Static class that keeps sorting algorithms implementations.
 */
public class Sort {

    public enum GapSequence {
        CIURA,
        SEDGEWICK,
        HIBBARD
    }

    public static int swaps = 0;

    private static final int[] ciuraSeq = new int[]{1, 4, 10, 23, 57, 132, 301, 701, 1750};

    public static int[] generateGapSequence(GapSequence name, int arrayLength) {
        ArrayList<Integer> integers = new ArrayList<>();
        int cur = 1, n = 1;
        while (cur <= arrayLength / 2) {
            integers.add(cur);
            if (GapSequence.CIURA == name) {
                if (n < ciuraSeq.length) {
                    cur = ciuraSeq[n];
                } else
                    break;
            } else if (GapSequence.SEDGEWICK == name) {
                cur = genSedgewick(n);
            } else if(GapSequence.HIBBARD == name) {
                cur = genHibbard(n+1);
            }
            n++;
        }

        return invertArray(integers);
    }

    private static int genHibbard(int n) {
        return (2 << (n-1)) - 1;
    }

    private static int genSedgewick(int n) {
        return ((n & 1) == 0) ? (9 * (2 << (n - 1)) - 9 * (2 << (n / 2 - 1)) + 1) : (8 * (2 << (n - 1)) - 6 * (2 << ((n + 1) / 2 - 1)) + 1);
    }

    private static int[] invertArray(ArrayList<? extends Integer> integers) {
        int size = integers.size();
        int[] res = new int[size];
        for (int i = size - 1; i >= 0; i--) {
            res[size - i - 1] = integers.get(i);
        }

        return res;
    }

    public static <T extends Comparable<T>> void insertion(T[] list, Comparator<T> comparator) {
        if (list == null || comparator == null)
            throw new IllegalArgumentException();

//        _insertion(list, 1, 1, comparator);
        for (int i = 1; i < list.length; i++) {
            int j = i;
            T tmp = list[j];
            while (j > 0 && comparator.compare(list[j - 1], tmp) > 0) {
                list[j] = list[--j];
            }
            list[j] = tmp;
        }
    }

    public static <T extends Comparable<T>> void shell(T[] list, Comparator<T> comparator, int[] gapSequence) {
        if (list == null || comparator == null || gapSequence == null)
            throw new IllegalArgumentException();

        for (int gap : gapSequence) {
            for (int i = gap; i < list.length; i += 1) {
                int j = i;
                T tmp = list[j];
                while (j >= gap && comparator.compare(list[j - gap], tmp) > 0) {
                    list[j] = list[j - gap];
                    j -= gap;
                    swaps++;
                }
                list[j] = tmp;
            }
        }
    }

    public static <T extends Comparable<T>> boolean isSorted(T[] list, Comparator<T> comparator) {
        if (list == null || comparator == null)
            throw new IllegalArgumentException();

        T current = null;
        for (T el : list) {
            if (current != null) {
                if (comparator.compare(current, el) > 0)
                    return false;
            }
            current = el;
        }

        return true;
    }
}
