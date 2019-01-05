package ru.otus.algo;

/**
 * Represents dynamic array with basic functionality.
 * @param <T> - type of array
 */
interface DynamicArray<T> {
    /**
     * Get item on {@code index}.
     * If index &lt; 0 ArrayIndexOutOfBoundsException is thrown
     * If index &gt; {@link #size()} ArrayIndexOutOfBoundsException is thrown
     * When try to get item on an empty array, ArrayIndexOutOfBoundsException is thrown
     *
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
     *
     * @param index   - position in the array where to add an {@code element}
     * @param element - element to add
     */
    void add(int index, T element);

    /**
     * Rewrites element at {@code index} with new {@code element}
     * If {@code index} &lt; 0 or &gt; {@link #size()}
     *
     * @param index   - position where to rewrite element
     * @param element - element to store in array
     */
    void set(int index, T element);

    /**
     * Removes element from array and return it/
     * If {@code index} &lt; 0 throws ArrayIndexOutOfBoundsException
     * If {@code index} &gt; {@code size()} throws ArrayIndexOutOfBoundsException
     * @param index - element's index
     * @return - removed element
     */
    T remove(int index);
    /**
     * Returns number of elements in this array.
     *
     * @return number of elements in this array.
     */
    int size();

    T[] toArray(T[] cl);
}