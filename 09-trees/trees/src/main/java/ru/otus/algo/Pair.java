package ru.otus.algo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pair<K, V> {

    private final K left;
    private final V right;

    private Pair(K left, V right) {
        this.left = left;
        this.right = right;
    }

    public static <K, V> ru.otus.algo.Pair<K, V> of(K value, V value2) {
        return new ru.otus.algo.Pair<>(value, value2);
    }

    public K getLeft() {
        return left;
    }

    public V getRight() {
        return right;
    }

    @Override
    public String toString() {
        return "Pair{" + left + ":" + right + '}';
    }

    static <K extends Comparable<K>, V extends Comparable<V>> List<ru.otus.algo.Pair<K, V>> combine(K[] values1, V[] values2) {
        if (values1 == null || values2 == null)
            throw new IllegalArgumentException();

        if (values1.length != values2.length)
            throw new IllegalArgumentException();

        List<ru.otus.algo.Pair<K, V>> res = new ArrayList<>();

        for (int i = 0; i < values1.length; i++) {
            res.add(ru.otus.algo.Pair.of(values1[i], values2[i]));
        }
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(left, pair.left) &&
                Objects.equals(right, pair.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
}