package ru.otus.algo;

public class IArray<T> extends DArray<T> {

    IArray() {
    }

    @Override
    protected int newArraySize(int oldSize) {
        return oldSize *2+1; // => 1 3 7 15 31 63
    }

}
