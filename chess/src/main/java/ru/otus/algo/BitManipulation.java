package ru.otus.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BitManipulation {

    private final static long notAFile = 0xfefefefefefefefeL;
    private final static long notHFile = 0x7f7f7f7f7f7f7f7fL;

    public static long fillOnce(long num, Direction[] dirs) {
        long res = 0L;
        for (Direction d : dirs) {
            if (Direction.EAST == d || Direction.NORTH_EAST == d || Direction.SOUTH_EAST == d) {
                res |= genShift(num, d.getValue()) & notAFile;
            } else if (Direction.WEST == d || Direction.SOUTH_WEST == d || Direction.NORTH_WEST == d) {
                res |= genShift(num, d.getValue()) & notHFile;
            } else
                res |= genShift(num, d.getValue());
        }
        return res;
    }

    static String drawLong(long lng) {
        int c = 64, line = 0;
        StringBuilder bld = new StringBuilder();
        while (c > 0) {
            bld.append((lng & 1) == 0? '.': '1').append(" ");
            line++;
            if (line == 8) {
                bld.append(System.lineSeparator());
                line = 0;
            }
            lng = lng >>> 1;
            c--;
        }
        return bld.toString();
    }

    static long genShift(long num, int shift) {
        int right = -((shift >>> 8) & shift);
        return (num >>> right) << (right + shift);
    }

    static long rankMask(int sq) {
        return 0xffL << (sq & 56);
    }

    static long fileMask(int sq) {
        return 0x0101010101010101L << (sq & 7);
    }

    static long diagonalMask(int sq) {
        long maindia = 0x8040201008040201L;
        int diag = 8 * (sq & 7) - (sq & 56);
        int nort = -diag & (diag >> 31);
        int sout = diag & (-diag >> 31);
        return (maindia >>> sout) << nort;
    }

    static long antiDiagMask(int sq) {
        long maindia = 0x0102040810204080L;
        int diag = 56 - 8 * (sq & 7) - (sq & 56);
        int nort = -diag & (diag >> 31);
        int sout = diag & (-diag >> 31);
        return (maindia >>> sout) << nort;
    }

    static long positiveRay(int val, long ray) {
        return ray & (-2L << val);
    }

    static long negativeRay(int val, long ray) {
        return ray & ((1L << val+1) - 1);
    }

    static List<Long> generateMap(Direction dir) {
        long[] lng = new long[Square.values().length];

        for (Square s : Square.values()) {
            final int value = s.getValue();

            if (dir == Direction.NORTH)
                lng[value] = positiveRay(value, fileMask(value));
            else if (dir == Direction.SOUTH)
                lng[value] = negativeRay(value, fileMask(value));
            else if (dir == Direction.WEST)
                lng[value] = negativeRay(value, rankMask(value));
            else if (dir == Direction.EAST)
                lng[value] = positiveRay(value, rankMask(value));
            else if (dir == Direction.NORTH_WEST)
                lng[value] = positiveRay(value, antiDiagMask(value));
            else if (dir == Direction.SOUTH_EAST)
                lng[value] = negativeRay(value, antiDiagMask(value));
            else if (dir == Direction.NORTH_EAST)
                lng[value] = positiveRay(value, diagonalMask(value));
            else if (dir == Direction.SOUTH_WEST)
                lng[value] = negativeRay(value, diagonalMask(value));
        }
        final List<Long> list = new ArrayList<>();
        for (long l: lng)
            list.add(l);
        return list;
    }
}
