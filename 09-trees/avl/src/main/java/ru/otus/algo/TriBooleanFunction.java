package ru.otus.algo;

@FunctionalInterface
public interface TriBooleanFunction<R, U> {

    boolean apply(R el, U u, U t, U v);
}
