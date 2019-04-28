package ru.otus.algo;

public interface Node {

    int getId();

    STATE getState();

    enum STATE {
        ROOT,
        COMMON
    }
}
