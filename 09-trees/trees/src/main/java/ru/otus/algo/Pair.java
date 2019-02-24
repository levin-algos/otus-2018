package ru.otus.algo;

import java.util.Comparator;

public class Pair<K, V> {

    private final K value;
    private final V priority;

    Pair(K value, V priority) {
        this.value = value;
        this.priority = priority;
    }

    public K getValue() {
        return value;
    }

    public V getPriority() {
        return priority;
    }
}
