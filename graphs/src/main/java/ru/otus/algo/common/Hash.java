package ru.otus.algo.common;

/**
 * Hash function abstraction.
 * Maps key of generic type {@code K} into integers.
 *
 * Each implementation of this interface have to satisfy properties.
 * Properties of hash function:
 * 1. Determinism. Given input parameter of {@code get} always produces the same result.
 * 2. Uniformity. Hash function should generate hashes as evenly as possible over it's output range.
 * 3. Defined range. The output of hash function have fixed size.
 * 4. Data normalisation. Two input that are considered equals must produce the same hash.
 */
public interface Hash<K> {

    /**
     * Maps {@code key} into integer.
     * @param key - key to be converted into integer.
     * @return - hash value.
     */
    int get(K key);
}