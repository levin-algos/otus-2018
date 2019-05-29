package ru.otus.algo.misc;

import java.util.Random;

public class MagicGenerator {
    static private Random rnd = new Random();

    private static long random_long() {
        long u1, u2, u3, u4;
        u1 = (rnd.nextLong()) & 0xFFFF;
        u2 = (rnd.nextLong()) & 0xFFFF;
        u3 = (rnd.nextLong()) & 0xFFFF;
        u4 = (rnd.nextLong()) & 0xFFFF;
        return u1 | (u2 << 16) | (u3 << 32) | (u4 << 48);
    }

    private static long random_long_fewbits() {
        return random_long() & random_long() & random_long();
    }

    private static int count_1s(long b) {
        return Long.bitCount(b);
    }

    private final static int[] BitTable = {  63, 30,  3, 32, 25, 41, 22, 33,
                                             15, 50, 42, 13, 11, 53, 19, 34,
                                             61, 29,  2, 51, 21, 43, 45, 10,
                                             18, 47,  1, 54,  9, 57,  0, 35,
                                             62, 31, 40,  4, 49,  5, 52, 26,
                                             60,  6, 23, 44, 46, 27, 56, 16,
                                              7, 39, 48, 24, 59, 14, 12, 55,
                                             38, 28, 58, 20, 37, 17, 36,  8 };

    private static int pop_1st_bit(long bb) {
        long b = bb ^ (bb - 1);
        int fold = (int)(((b & 0xffffffffL) ^ (b >> 32)));
          return BitTable[(fold * 0x783a9b23) >>> 26];
    }

    private static long index_to_long(int index, int bits, long m) {
        int i, j;
        long result = 0L;
        for (i = 0; i < bits; i++) {
            j = pop_1st_bit(m);
            m = m & (m - 1);
            if ((index & (1 << i)) == 0) result |= (1L << j);
        }
        return result;
    }

    private static long rmask(int sq) {
        long result = 0 ;
        int rk = sq / 8, fl = sq % 8, r, f;
        for (r = rk + 1; r <= 6; r++) result |= (1  << (fl + r * 8));
        for (r = rk - 1; r >= 1; r--) result |= (1  << (fl + r * 8));
        for (f = fl + 1; f <= 6; f++) result |= (1  << (f + rk * 8));
        for (f = fl - 1; f >= 1; f--) result |= (1  << (f + rk * 8));
        return result;
    }

    private static long bmask(int sq) {
        long result = 0 ;
        int rk = sq / 8, fl = sq % 8, r, f;
        for (r = rk + 1, f = fl + 1; r <= 6 && f <= 6; r++, f++) result |= (1  << (f + r * 8));
        for (r = rk + 1, f = fl - 1; r <= 6 && f >= 1; r++, f--) result |= (1  << (f + r * 8));
        for (r = rk - 1, f = fl + 1; r >= 1 && f <= 6; r--, f++) result |= (1  << (f + r * 8));
        for (r = rk - 1, f = fl - 1; r >= 1 && f >= 1; r--, f--) result |= (1  << (f + r * 8));
        return result;
    }

    private static long ratt(int sq, long block) {
        long result = 0 ;
        int rk = sq / 8, fl = sq % 8, r, f;
        for (r = rk + 1; r <= 7; r++) {
            result |= (1  << (fl + r * 8));
            if ((block & (1  << (fl + r * 8))) == 0)break;
        }
        for (r = rk - 1; r >= 0; r--) {
            result |= (1  << (fl + r * 8));
            if ((block & (1  << (fl + r * 8))) == 0) break;
        }
        for (f = fl + 1; f <= 7; f++) {
            result |= (1  << (f + rk * 8));
            if ((block & (1  << (f + rk * 8))) == 0) break;
        }
        for (f = fl - 1; f >= 0; f--) {
            result |= (1  << (f + rk * 8));
            if ((block & (1  << (f + rk * 8))) == 0) break;
        }
        return result;
    }

    private static long batt(int sq, long block) {
        long result = 0 ;
        int rk = sq / 8, fl = sq % 8, r, f;
        for (r = rk + 1, f = fl + 1; r <= 7 && f <= 7; r++, f++) {
            result |= (1  << (f + r * 8));
            if ((block & (1  << (f + r * 8))) == 0) break;
        }
        for (r = rk + 1, f = fl - 1; r <= 7 && f >= 0; r++, f--) {
            result |= (1  << (f + r * 8));
            if ((block & (1  << (f + r * 8))) == 0) break;
        }
        for (r = rk - 1, f = fl + 1; r >= 0 && f <= 7; r--, f++) {
            result |= (1  << (f + r * 8));
            if ((block & (1  << (f + r * 8))) == 0) break;
        }
        for (r = rk - 1, f = fl - 1; r >= 0 && f >= 0; r--, f--) {
            result |= (1  << (f + r * 8));
            if ((block & (1  << (f + r * 8))) == 0) break;
        }
        return result;
    }


    private static int transform(long b, long magic, int bits) {
        return (int) ((b * magic) >>> (64 - bits));
    }

    private static long find_magic(int sq, int m, boolean bishop) {
        long[] b, a, used;
        long magic, mask;
        int i, j, k, n;
        boolean fail;

        b = new long[4096];
        a = new long[4096];
        used = new long[4096];

        mask = bishop ? bmask(sq) : rmask(sq);
        n = count_1s(mask);

        for (i = 0; i < (1 << n); i++) {
            b[i] = index_to_long(i, n, mask);
            a[i] = bishop ? batt(sq, b[i]) : ratt(sq, b[i]);
        }
        for (k = 0; k < 100000000; k++) {
            magic = random_long_fewbits();
            if (count_1s((mask * magic) & 0xFF00000000000000L) < 6) continue;
            for (i = 0; i < 4096; i++) used[i] = 0 ;
            for (i = 0, fail = false; !fail && i < (1 << n); i++) {
                j = transform(b[i], magic, m);
                if (used[j] == 0 )used[j] = a[i];
      else if (used[j] != a[i]) fail = true;
            }
            if (!fail) return magic;
        }
        System.out.println("***Failed***\n");
        return 0 ;
    }

    private static final int[] RBits=    {
                12, 11, 11, 11, 11, 11, 11, 12,
                11, 10, 10, 10, 10, 10, 10, 11,
                11, 10, 10, 10, 10, 10, 10, 11,
                11, 10, 10, 10, 10, 10, 10, 11,
                11, 10, 10, 10, 10, 10, 10, 11,
                11, 10, 10, 10, 10, 10, 10, 11,
                11, 10, 10, 10, 10, 10, 10, 11,
                12, 11, 11, 11, 11, 11, 11, 12  };

    private static final int[] BBits = {
                6, 5, 5, 5, 5, 5, 5, 6,
                5, 5, 5, 5, 5, 5, 5, 5,
                5, 5, 7, 7, 7, 7, 5, 5,
                5, 5, 7, 9, 9, 7, 5, 5,
                5, 5, 7, 9, 9, 7, 5, 5,
                5, 5, 7, 7, 7, 7, 5, 5,
                5, 5, 5, 5, 5, 5, 5, 5,
                6, 5, 5, 5, 5, 5, 5, 6  };

    public static void main(String[] args) {
        int square;

        System.out.println("const long RMagic[64] = {\n");
        for (square = 0; square < 64; square++)
            System.out.println(find_magic(square, RBits[square], false) + ", ");
        System.out.println("};\n\n");

        System.out.println("const long BMagic[64] = {\n");
        for (square = 0; square < 64; square++)
            System.out.println(find_magic(square, BBits[square], true) +", ");
        System.out.println("};\n\n");
    }
}

