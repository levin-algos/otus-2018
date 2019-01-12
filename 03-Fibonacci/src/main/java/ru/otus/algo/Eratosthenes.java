package ru.otus.algo;

import java.util.BitSet;

class Eratosthenes {

    private Eratosthenes(int maxNumber) {
        calcPrimes(maxNumber);
        this.maxNumber = maxNumber;
    }

    private void calcPrimes(int max) {
        primes = new BitSet(max/2);

        for (int i = 2; i*i <= max; i++) {
            if ((i & 1)== 1 && !primes.get((i-1)/2)) {
                for (int j = i*i; j <= max; j += i) {
                    if ((j & 1) != 0) {
                        primes.set((j-1)/2);
                    }
                }
            }
        }
    }

    static Eratosthenes of(int maxNumber) {
        return new Eratosthenes(maxNumber);
    }

    boolean isPrime(int prime) {
        return primes.get((prime-1)/2);
    }

    int size() {
        return size == 0 ? calcSize() : size;
    }

    private int calcSize() {
        return size = maxNumber/2 - primes.cardinality();
    }

    private BitSet primes;
    private int size;
    private final int maxNumber;
}