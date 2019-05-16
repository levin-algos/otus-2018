package ru.otus.algo;

public class HyperLogLog {

    private static final int BUCKETS = 16;
    private final int BUCKET_BITS = 4;
    private final long[] registers = new long[BUCKETS];
//
    void add(int hash) {
        int r = getRegister(hash);
        int w = (hash << BUCKET_BITS);
        final int b = Integer.numberOfLeadingZeros(w) % (32 - BUCKET_BITS);
        registers[r] = Math.max(registers[r], b);
    }

    private int getRegister(int hash) {
        final int intSize = 32;
        return hash >>>(intSize - BUCKET_BITS);
    }

    long count() {
        double z = getHarmonicMean(registers);
        return (long)(getAlpha()*BUCKETS*BUCKETS*z);
    }

    private double getHarmonicMean(long[] registers) {
        double res = 0;
        for (long r: registers) {
            res += Math.pow(2, -r);
        }
        return 1.0/res;
    }

    private double getAlpha() {
        if (BUCKETS == 16) return 0.673;
        if (BUCKETS == 32) return 0.697;
        if (BUCKETS == 64) return 0.709;
        return 0;
    }
}
