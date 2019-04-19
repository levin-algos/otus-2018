package ru.otus.algo.common;

public interface Set<T> extends Iterable<T> {

    void add(T el);

    boolean contains(T el);

    void remove(T b);
}