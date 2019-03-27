package ru.otus.algo;

import java.util.function.Consumer;

public interface BinaryTree<T> {

    boolean contains(T element);

    void add(T element);

    void remove(T element);

    int size();

    void traverse(Traversal order, Consumer<T> action);

    enum Traversal {
        IN_ORDER,
    }
}