package ru.otus.algo;

class DArray<T> implements DynamicArray<T> {

    private Object[] _arr;
    private int size;

    public DArray() {
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (_arr == null || index < 0)
            throw new ArrayIndexOutOfBoundsException();

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
            System.arraycopy(_arr, index, tmp, index + 1, _arr.length);
            _arr = tmp;
        } else {
            System.arraycopy(_arr, index, _arr, index + 1, _arr.length - index - 1);
        }
    }

    public void add(int index, T element) {
        if (index < 0 || index > size())
            throw new ArrayIndexOutOfBoundsException();

        relocate(index);
        _arr[index] = element;
        size++;
    }

    public void set(int index, T element) {
        _arr[index] = element;
    }

    public int size() {
        return size;
    }

    protected int newArraySize(int oldSize) {
        return oldSize + 1;
    }
}