package ru.otus.algo;

public interface Map<K, V> {

    void put(K key, V value);

    void remove(K key);

    boolean containsKey(K key);
}
