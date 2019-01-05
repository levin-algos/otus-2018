package ru.otus.algo;

public class PArrayQueue<T> implements PQueue<T> {

    public static <T> PQueue<T> of() {
        return new PArrayQueue<>();
    }

    private PArrayQueue() {

    }

    public void enqueue(int priority, T test) {

    }

    public T dequeue() {
        return null;
    }
}
