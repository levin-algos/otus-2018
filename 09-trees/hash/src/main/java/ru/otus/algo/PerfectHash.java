package ru.otus.algo;

import java.util.Random;


/**
 * Implementation of perfect hashing algorithm.
 * <p>
 * To generate perfect hash function, this implementation uses next class of hash functions:
 * {@code hash(x) = (((a*x + b) mod p) mod M)}, where
 * 1. a and b is randomly generated numbers.
 * 1.1 a is in [1, p-1]
 * 1.2 b is in [0, p-1]
 * 1.3 {@code a % p != 0}
 * 2. p - is a prime number
 * 2.1 maximum key's {@link Object#hashCode()}  of it's overridden version is {@code &lt;= p - 1}.
 * 3. M - output range of hash function.
 * @param <K> - input type of hash function
 */
public class PerfectHash<K> implements Hash<K> {
    private final int a, b;
    private final long p;
    private final int M;

    private PerfectHash(int a, int b, long p, int m) {
        this.a = a;
        this.b = b;
        this.p = p;
        M = m;
    }

    @Override
    public int get(K key) {
        if (key == null)
            throw new IllegalArgumentException();

        long res = (((long) a * key.hashCode() + b) % p) % M;

        return (int) res;
    }

    /**
     * Builder class for constructing perfect hash function.
     * Call of {@link #size(int)} is required for constructing hash function.
     *
     * If {@link #p(int)} is not called - p is set to {@code 2147483659} - next prime from {@link Integer#MAX_VALUE}
     * if {@link #coeffs(int, int)} is not called - a and b are generated randomly.
     * @param <K>
     */
    static class Builder<K> {

        private static Random rnd = new Random();
        private int a, b;
        private long p = 2_147_483_659L;
        private int M;

        public Builder coeffs(int a, int b) {
            if (a <=0 || b < 0)
                throw new IllegalArgumentException();

            this.a = a;
            this.b = b;
            return this;
        }

        public Builder p(int p) {
            if (p <=0)
                throw new IllegalArgumentException();
            this.p = p;
            return this;
        }

        public Builder size(int M) {
            if (M <= 0)
                throw new IllegalArgumentException();
            this.M = M;
            return this;
        }

        public PerfectHash<K> build() {
            if (M == 0)
                throw new IllegalArgumentException();

            int bound = p > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) p;
            while (a % p == 0)
                a = rnd.nextInt(bound - 1) + 1;

            if (b != 0)
                b = rnd.nextInt(bound);

            return new PerfectHash<K>(a, b, p, M);
        }
    }
}
