package ru.otus.algo.common;

import java.util.Comparator;

/**
 * Represents dynamic array with basic functionality.
 * @param <T> - type of array
 */
public interface DynamicArray<T> extends Iterable<T> {

    /**
     * Inserts @{code element} at {@code size} position.
     *
     * @param element - element to add
     */
    void add(T element);

    T[] toArray(T[] cl);

    void sort(Comparator<? super T> comparator);
}