package ru.otus.algo;


public class BArray<T> extends DArray<T> {

    BArray() {}

    BArray(int initSize) { super(initSize);}

    BArray(T... arr) {super(arr);}

    @Override
    protected int newArraySize(int oldSize) {
        return oldSize*2+1;
    }

    public BArray<T> split(int at) {
        if (at < 0 || at >= size()-1)
            throw new ArrayIndexOutOfBoundsException();

        BArray<T> tail = new BArray<>();
        int tailSize = size() - at - 1;
        tail._arr = new Object[_arr.length];
        System.arraycopy(_arr, at+1, tail._arr, 0, tailSize);
        tail.size = tailSize;
        for (int i = at+1; i < _arr.length; i++) {
            _arr[i] = null;
        }
        size = at+1;
        return tail;
    }
}
