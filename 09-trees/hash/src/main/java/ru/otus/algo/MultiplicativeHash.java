package ru.otus.algo;

/**
 * Implementation of Hash interface.
 *
 * This approach uses formula: {@code hash(x) = A*x >> (w-m)}
 * {@code M = a^m}, where{@code M} - is range of hash output.
 * w - machine word size (e.g. 16, 32, 64 ...).
 * {@code A} must be prime to {@code W}, where {@code W = 2*w}.
 */
public class MultiplicativeHash<K> implements Hash<K> {
    private final int rshift;
    private final int A;
    public MultiplicativeHash(int a, int w, int m) {
        rshift = w-m;
        if (rshift < 0)
            throw new IllegalArgumentException();
        A = a;
    }

    @Override
    public int get(K key) {
        if (key == null)
            throw new IllegalArgumentException();

        return A*key.hashCode() >> rshift;
    }
}
