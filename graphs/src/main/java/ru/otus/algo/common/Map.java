package ru.otus.algo.common;

public interface Map<K, V> {

    void put(K key, V value);

    void remove(K key);

    boolean containsKey(K key);

    V get(K key);

    Iterable<Map.Entry<K, V>> entrySet();

    interface Entry<K, V> {
        K getKey();
        V getValue();
    }
}