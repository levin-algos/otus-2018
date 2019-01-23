package ru.otus.sort;

import java.util.Comparator;
import java.util.List;

/**
 * Static class that keeps sorting algorithms implementations.
 */
public class Sort {

    public static <T extends Comparable<T>> void insertion(List<T> list, Comparator<T> comparator) {
        if (list == null || comparator == null)
            throw new IllegalArgumentException();

        System.out.println(_insertion(list, 1, 1, comparator));
    }

    public static <T extends Comparable<T>> void shell(List<T> list, Comparator<T> comparator, int[] gapSequence) {
        if (list == null || comparator == null || gapSequence == null)
            throw new IllegalArgumentException();

        int swaps = 0;
        int halfSize = list.size()/2;
        for (int gap: gapSequence) {
            if (halfSize > gap) {
                System.out.println(gap);
                swaps += _insertion(list, gap, gap, comparator);
            }
        }

        System.out.println(swaps);
    }

    private static <T extends Comparable<T>> int _insertion(List<T> list, int from, int step, Comparator<T> comparator) {
        int swaps = 0;
        for (int i = from; i < list.size(); i+= step) {
            int j = i;
            T tmp = list.get(j);
            while (j > 0 && comparator.compare(list.get(j-step), tmp)  > 0) {
                list.set(j, list.get(j-step));
                swaps++;
                j-=step;
            }
            list.set(j, tmp);
        }
        return swaps;
    }

    public static <T extends Comparable<T>> boolean isSorted(List<T> list, Comparator<T> comparator) {
        if (list == null || comparator == null)
            throw new IllegalArgumentException();

        T current = null;
        for (T el: list) {
            if (current != null) {
                if (comparator.compare(current, el) > 0)
                    return false;
            }
            current = el;
        }

        return true;
    }
}
