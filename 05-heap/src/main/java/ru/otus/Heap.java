package ru.otus;

import java.util.Arrays;

public class Heap<T extends Comparable<T>> {

    private T[] data;
    private int size;

    public Heap(T[] arr) {
        if (arr == null)
            throw new IllegalArgumentException();

        size = arr.length;
        data = arr;
        heapify();
    }

    public static <T extends Comparable<T>> void sort(T[] arr) {
        Heap<T> h = new Heap<>(arr);
        for (int i = 0; i < h.size(); i++) {
            h.swap(0, h.size()-i-1);
            h.drown(0, h.size()-i-1);
        }
    }

    private void heapify() {
        for (int i = data.length / 2 - 1; i >= 0; i--) {
            drown(i, data.length);
        }
    }

    private void drown(int i, int size) {
        int left = i * 2 + 1, right = i * 2 + 2;
        int max = i;
        if (left < size && data[i].compareTo(data[left]) < 0)
            max = left;

        if (right < size && data[max].compareTo(data[right]) < 0)
            max = right;

        if (max != i) {
            swap(max, i);
            drown(max, size);
        }
    }

    private void swap(int i, int j) {
        T tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }

    /**
     * Get and remove the head element from the heap
     *
     * @return - head element from the heap
     */
    public T poll() {
        T res = data[0];
        swap(0, size - 1);
        data[size - 1] = null;
        size--;
        drown(0, size);
        return res;
    }

    /**
     * Returns number of elements in the heap
     *
     * @return - number of elements from the heap
     */
    public int size() {
        return size;
    }

    /**
     * Get and remove the head element from the heap
     *
     * @return - head element from the heap
     */
    public T peek() {
        return data[0];
    }

    @Override
    public String toString() {
        return "Heap{" +
                "data=" + Arrays.toString(data) +
                ", size=" + size +
                '}';
    }
}
