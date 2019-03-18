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

        return A*key.hashCode();
    }

    private int getPower(int M) {
        if (M >> 16 == 0) {
            if (M >> 8 == 0) {

            }

        } else {

        }
        return 0;
    }
}


/*
1       0001
2   0010
4   0100
8   1000
16
32
64
128





 */