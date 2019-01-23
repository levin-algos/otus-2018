package ru.otus.sort;

import java.util.Comparator;
import java.util.List;

/**
 * Static class that keeps sorting algorithms implementations.
 */
public class Sort {


    public static <T extends Comparable<T>> void inversion(List<T> list, Comparator<T> comparator) {

    }

    public static <T extends Comparable<T>> boolean isSorted(List<T> list, Comparator<T> comparator) {
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
