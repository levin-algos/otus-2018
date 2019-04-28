package ru.otus.algo;

/**
 * Represents link between to vertexes of type {@code T}.
 * @param <T> - vertex type
 */
public interface Adjacency<T extends Node> {
    void connect(T a, T b);

    boolean isConnected(T a, T b);

    void disconnect(T a, T b);

    Iterable<T> getConnected(T a);
}