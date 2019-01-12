package ru.otus.algo;

import java.util.BitSet;

/**
 * Calculates prime numbers in [2, maxNumber] interval using Sieve of BitSetEratosthenes.
 * This implementation uses BitSet as an bit array for calculating prime numbers.
 */
public class BitSetEratosthenes implements Eratosthenes {

    private BitSetEratosthenes(int maxNumber) {
        calcPrimes(maxNumber);
        this.maxNumber = maxNumber;
    }

    private void calcPrimes(int max) {
        primes = new BitSet(max >> 1);
        for (long i = 2; i*i <= max; i++) {
            if ((i & 1)== 1 && !primes.get((int)i >> 1)) {
                for (long j = i*i; j <= max; j += i) {
                    if ((j & 1) != 0) {
                        primes.set((int)(j >> 1));
                    }
                }
            }
        }
    }

    public static BitSetEratosthenes of(int maxNumber) {
        if (maxNumber < 2)
            throw new IllegalArgumentException();

        return new BitSetEratosthenes(maxNumber);
    }

    @Override
    public boolean isPrime(int prime) {
        return prime >= 2 && primes.get(prime >> 1);
    }

    @Override
    public int size() {
        return size == 0 ? calcSize() : size;
    }

    private int calcSize() {
        return size = maxNumber/2 - primes.cardinality();
    }

    private BitSet primes;
    private int size;
    private final int maxNumber;
}
