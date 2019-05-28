package ru.otus.algo.misc;

import ru.otus.algo.BitManipulation;
import ru.otus.algo.Direction;
import ru.otus.algo.Figure;
import ru.otus.algo.Square;

import java.util.Arrays;

public class FugireAttackMapGenerator {

    public static void main(String[] args) {
            final long[] a = generateMap(Figure.BISHOP);
            final long[] b = generateMap(Figure.ROOK);

        System.out.println(Arrays.toString(a));
        System.out.println();
        System.out.println(Arrays.toString(b));
    }

    private static long[] generateMap(Figure bishop) {
        long[] lng = new long[Square.values().length];
        if (bishop == Figure.BISHOP) {
            for (Square s : Square.values()) {
                final int value = s.getValue();
                lng[value] = diagonalMask(value) | antiDiagMask(value);
            }
        } else if (bishop == Figure.ROOK) {
            for (Square s : Square.values()) {
                final int value = s.getValue();
                lng[value] = rankMask(value) | fileMask(value);
            }
        }
        return lng;
    }

    private static long rankMask(int sq) {
        return 0xffL << (sq & 56);
    }

    private static long fileMask(int sq) {
        return 0x0101010101010101L << (sq & 7);
    }

    private static long diagonalMask(int sq) {
        long maindia = 0x8040201008040201L;
        int diag = 8 * (sq & 7) - (sq & 56);
        int nort = -diag & (diag >> 31);
        int sout = diag & (-diag >> 31);
        return (maindia >>> sout) << nort;
    }

    private static long antiDiagMask(int sq) {
        long maindia = 0x0102040810204080L;
        int diag = 56 - 8 * (sq & 7) - (sq & 56);
        int nort = -diag & (diag >> 31);
        int sout = diag & (-diag >> 31);
        return (maindia >>> sout) << nort;
    }
}
