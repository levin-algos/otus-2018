package ru.otus.algo;

public class Pair<K, V extends Comparable<V>> implements Comparable<Pair<K, V>> {

    private final K value;
    private final V priority;

    Pair(K value, V priority) {
        this.value = value;
        this.priority = priority;
    }

    @Override
    public int compareTo(Pair<K, V> o) {
        return this.priority.compareTo(o.priority);
    }

    public K getValue() {
        return value;
    }

    public V getPriority() {
        return priority;
    }

}
