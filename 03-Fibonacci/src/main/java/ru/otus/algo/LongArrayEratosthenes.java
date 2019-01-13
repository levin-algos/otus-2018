package ru.otus.algo;

public class LongArrayEratosthenes implements Eratosthenes {

    public static Eratosthenes of(int nbits) {
        return new LongArrayEratosthenes(nbits);
    }

    private LongArrayEratosthenes(int nbits) {
        calcPrimes(nbits);
        bits = nbits;
    }

    private void calcPrimes(int max) {
        array = LongArray.of(max >> 1);
        for (long i = 2; i*i <= max; i++) {
            if ((i & 1)== 1 && !array.get((int)i >> 1)) {
                for (long j = i*i; j <= max; j += i) {
                    if ((j & 1) != 0) {
                        array.set((int)(j >> 1));
                    }
                }
            }
        }
    }

    @Override
    public boolean isPrime(int prime) {
        return prime >= 2 && array.get(prime >> 1);
    }

    @Override
    public int getPrimeCount() {
        return (bits >> 1) - array.size();
    }

    private LongArray array;
    private final int bits;
}
