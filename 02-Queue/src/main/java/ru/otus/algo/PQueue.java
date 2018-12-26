package ru.otus.algo;

public interface PQueue<T> {
    void enqueue(int priority, T test);

    T dequeue();
}
