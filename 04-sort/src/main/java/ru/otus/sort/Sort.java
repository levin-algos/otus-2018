package ru.otus.sort;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Static class that keeps sorting algorithms implementations.
 */
public class Sort {


    public static <T extends Comparable<T>> void insertion(List<T> list, Comparator<T> comparator) {
        if (list == null || comparator == null)
            throw new IllegalArgumentException();

        for (int i = 0; i < list.size(); i++) {
            int j = i;
            while (j > 0 && comparator.compare(list.get(j-1), list.get(j))  > 0) {
                Collections.swap(list, j-1, j);
                j--;
            }
        }
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
