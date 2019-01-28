package ru.otus;

import java.util.Arrays;
import java.util.Stack;

public class Heap<T extends Comparable<T>> {

    private T[] data;
    private int size;
    private static Stack<Integer> stack = new Stack<>();

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
            stack.push(0);
            stack.push(h.size()-i-1);
            h.drown();
        }
    }

    private void heapify() {
        for (int i = data.length / 2 - 1; i >= 0; i--) {
            stack.push(i);
            stack.push(data.length);
            drown();
        }
    }

    private void drown() {
        while (!stack.empty()) {
            int size = stack.pop();
            int i = stack.pop();
            int left = i * 2 + 1, right = i * 2 + 2;
            int max = i;
            if (left < size && data[i].compareTo(data[left]) < 0)
                max = left;

            if (right < size && data[max].compareTo(data[right]) < 0)
                max = right;

            if (max != i) {
                swap(max, i);
                stack.push(max);
                stack.push(size);
            }
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
        return remove(0);
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

    public void remove(T obj) {
        if (obj == null)
            throw new IllegalArgumentException();

        for (int i=0; i< data.length; i++) {
            T tmp = data[i];
            if (obj.equals(tmp)) {
                remove(i);
                return;
            }
        }
    }

    private T remove(int pos) {
        T res = data[pos];
        swap(pos, size - 1);
        data[size - 1] = null;
        size--;
        stack.push(0);
        stack.push(size);
        drown();
        return res;
    }
}
