package ru.otus.algo;

@FunctionalInterface
public interface TriConsumer<T, R> {

    void accept(T el, R less, R eq, R greater);
}
