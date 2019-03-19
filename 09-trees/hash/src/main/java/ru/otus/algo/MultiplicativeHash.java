package ru.otus.algo;

/**
 * Implementation of Hash interface.
 *
 * This approach uses formula: {@code hash(x) = A*x >> (w-m)}
 * {@code M = a^m}, where{@code M} - is range of hash output.
 * w - machine word size (e.g. 16, 32, 64 ...).
 * {@code A} must be prime to {@code W}, where {@code W = 2^w}.
 */
public class MultiplicativeHash<K> implements Hash<K> {
    private final int A;
    private final int w;
    public MultiplicativeHash(int a, int w) {
        this.w = w;
        A = a;
    }

    @Override
    public int get(K key, int M) {
        if (key == null)
            throw new IllegalArgumentException();

        return A*key.hashCode() >> (w - getPower(M));
    }

    int getPower(int M) {
       return Integer.numberOfTrailingZeros(Integer.highestOneBit(M))+1;
    }
}