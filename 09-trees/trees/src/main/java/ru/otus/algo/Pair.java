package ru.otus.algo;

public class Pair<K, V> {

    private final K val1;
    private final V val2;

    private Pair(K value1, V value2) {
        this.val1 = value1;
        this.val2 = value2;
    }

    public static <K, V> Pair<K, V> of(K value, V value2) {
        return new Pair<>(value, value2);
    }

    public K getFirst() {
        return val1;
    }

    public V getSecond() {
        return val2;
    }

    @Override
    public String toString() {
        return "Pair{" + val1 + ":" + val2 +'}';
    }
}
