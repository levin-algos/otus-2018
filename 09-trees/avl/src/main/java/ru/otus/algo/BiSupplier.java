package ru.otus.algo;

public interface BiSupplier<T, R, U> {

    T get(R r, U u);
}
