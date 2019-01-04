package ru.otus.algo;

/**
 * Represents dynamic array with basic functionality.
 * @param <T> - type of array
 */
public interface DynamicArray<T> {
    /**
     * Get item on {@code index}.
     * If index &lt; 0 ArrayIndexOutOfBoundsException is thrown
     * When try to get item on an empty array, ArrayIndexOutOfBoundsException is thrown
     * @param index - element's position
     * @return element at {@code index} position
     */
    T get(int index);

    /**
     * Inserts @{code element} at {@code element} position.
     * Shifts every element in the array starts from {@code index} position by 1
     * and inserts {@code element} at {@code index} position.
     * If {@code index} &lt; 0 throws ArrayIndexOutOfBoundsException
     * If {@code index} &gt; {@code size()} throws ArrayIndexOutOfBoundsException
     * @param index - position in the array where to add an {@code element}
     * @param element - element to add
     */
    void add(int index, T element);

    /**
     * Returns number of elements in this array.
     *
     * @return number of elements in this array.
     */
    int size();
}