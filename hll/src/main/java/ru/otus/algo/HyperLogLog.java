package ru.otus.algo;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class HyperLogLog {

    private final int m;
    private final int BUCKET_BITS;
    private final int bucketMask;
    private final AtomicIntegerArray registers;
    private final double alphaMSquared;

    HyperLogLog(int bucketsBit) {
        this.BUCKET_BITS = bucketsBit;
        this.m = 1 << bucketsBit;
        this.bucketMask = (1 << bucketsBit) - 1;
        registers = new AtomicIntegerArray(m);
        alphaMSquared = getAlphaMSquared();
    }

    void add(long hash) {
        int r = getRegister(hash);
        long w = (hash >>> BUCKET_BITS);
        final int b = Math.min(Long.numberOfTrailingZeros(w) + 1, 64 - BUCKET_BITS);
        boolean updated = false;
        while (!updated) {
            final int val = registers.get(r);
            if (b > val)
                updated = registers.compareAndSet(r, val, b);
            else updated = true;
        }
    }

    private int getRegister(long hash) {
        return (int) (hash & bucketMask);
    }

    long count() {
        return getEstimate();
    }

    private long getEstimate() {
        double res = 0;
        int v = 0;
        for (int i = 0; i < registers.length(); i++) {
            final int r = registers.get(i);
            res += 1.0 / (1L << r);
            if (r == 0) v++;
        }
        res = 1.0 / res;
        long E = (long) (alphaMSquared * res);

        if (E <= 5 / 2 * m) {
            E = v != 0 ? (long) (m * Math.log(m / v)) : E;
        } else {
            final long l = 1L << 32;
            if (E > (l / 30)) {
                E = (long)(-l * Math.log(1 - (double)E / l));
            }
        }
        return E;
    }

    private double getAlphaMSquared() {
        double alpha;
        if (m == 16) alpha = 0.673;
        else if (m == 32) alpha = 0.697;
        else if (m == 64) alpha = 0.709;
        else {
            alpha = 0.7213 / (1 + (1.079 / m));
        }
        return alpha * m * m;
    }
}