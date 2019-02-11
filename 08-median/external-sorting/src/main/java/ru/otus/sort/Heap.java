package ru.otus.sort;

import java.util.*;

public class Heap<T extends Comparable<T>> {

    private List<T> data;
    private int size;

    public Heap() {
        data = new ArrayList<>();
    }

    public Heap(T[] arr) {
        if (arr == null)
            throw new IllegalArgumentException();

        size = arr.length;
        data = Arrays.asList(Arrays.copyOf(arr, arr.length));
        heapify();
    }

    private void heapify() {
        for (int i = data.size() / 2 - 1; i >= 0; i--) {
            drown(i, data.size());
        }
    }

    private void ascend(int i) {
        int parent = (i - 1) / 2;

        if (parent >= 0) {
            if (data.get(parent).compareTo(data.get(i)) > 0) {
                swap(parent, i);

                ascend(parent);
            }
        }
    }

    private void drown(int i, int size) {
        int left = i * 2 + 1, right = i * 2 + 2;
        int min = i;
        if (left < size && data.get(i).compareTo(data.get(left)) > 0)
            min = left;

        if (right < size && data.get(min).compareTo(data.get(right)) > 0)
            min = right;

        if (min != i) {
            swap(min, i);
            drown(min, size);
        }
    }

    private void swap(int i, int j) {
        T tmp = data.get(i);
        data.set(i, data.get(j));
        data.set(j, tmp);
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
        return data.get(0);
    }

    @Override
    public String toString() {
        return "Heap{" +
                "data=" + data +
                ", size=" + size +
                '}';
    }

    /**
     * Remove first occurrences of {@code obj} from the heap
     *
     * @param obj - object to remove from the heap
     * @return true - if object was removed. false - otherwise.
     */
    public boolean remove(T obj) {
        if (obj == null)
            throw new IllegalArgumentException();

        for (int i = 0; i < data.size(); i++) {
            T tmp = data.get(i);
            if (obj.equals(tmp)) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    public T[] toArray(T[] cl) {
        return data.toArray(cl);
    }

    private T remove(int pos) {
        T res = data.get(pos);
        size--;
        swap(pos, size);
        data.set(size, null);
        if (pos < ((size) / 2)) {
            drown(pos, size);
        } else {
//            ascend(pos);
        }
        return res;
    }

    public void add(T i) {
        data.add(i);
        ascend(size);
        size++;
    }
}