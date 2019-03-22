package ru.otus.algo;

/**
 * Implementation of hash interface.
 * This implementation based on {@link Object#hashCode()} function or it's override version.
 *
 * Determinism, uniformity and data normalization hash function
 * properties are considered to be satisfied by {@link Object#hashCode()} function or it's override version.
 *
 * @param <K> -
 */
public class TrivialHash<K> implements Hash<K> {

    @Override
    public int get(K key) {
        if (key == null)
            throw new IllegalArgumentException();

        return key.hashCode();
    }
}
