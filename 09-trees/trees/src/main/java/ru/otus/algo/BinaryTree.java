package ru.otus.algo;

interface BinaryTree<T> {

    boolean contains(T element);

    void add(T element);

    void remove(T element);

    int size();
}
