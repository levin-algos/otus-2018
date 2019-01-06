package ru.otus.algo;

import java.util.Arrays;

class DArray<T> implements DynamicArray<T> {

    Object[] _arr;
    int size;

    DArray() {}

    DArray(int initSize) { _arr = new Object[initSize]; }

    DArray(T... arr) {
        for (int i = 0; i < arr.length; i++) add(i, arr[i]);
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (_arr == null || index < 0 || index >= size())
            throw new ArrayIndexOutOfBoundsException(index);

        return (T) _arr[index];
    }


    private void relocate(int index) {
        if (_arr == null) {
            _arr =  new Object[newArraySize(size())];
            return;
        }

        if (size() >= _arr.length) {
            Object[] tmp = new Object[newArraySize(size())];
            System.arraycopy(_arr, 0, tmp, 0, index);
            System.arraycopy(_arr, index, tmp, index + 1, size() - index);
            _arr = tmp;
        } else {
            System.arraycopy(_arr, index, _arr, index + 1, size() - index);
        }
    }

    public void add(int index, T element) {
        if (index < 0 || index > size())
            throw new ArrayIndexOutOfBoundsException(index);

        relocate(index);
        _arr[index] = element;
        size++;
    }


    public void set(int index, T element) {
        _arr[index] = element;
    }

    @Override
    public T remove(int index) {
        if (index >= size() || index < 0)
            throw new ArrayIndexOutOfBoundsException(index);

        T res = get(index);
        collapse(index);
        size--;
        return res;
    }

    private void collapse(int index) {
        System.arraycopy(_arr, index+1, _arr, index, size() - index - 1);
    }

    public int size() {
        return size;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T[] toArray(T[] a) {
        if (a.length < size)
            // Make a new array of a's runtime type, but my contents:
            return (T[]) Arrays.copyOf(_arr, size, a.getClass());
        System.arraycopy(_arr, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }

    int newArraySize(int oldSize) {
        return oldSize + 1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        if (size() >0) {
            for (int i = 0; i < size() - 1; i++) {
                sb.append(_arr[i]).append(",");
            }
            sb.append(_arr[size() - 1]);
        }
        return sb.append(']').toString();
    }
}