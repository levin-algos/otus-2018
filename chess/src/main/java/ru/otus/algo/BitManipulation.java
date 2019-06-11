package ru.otus.algo;

class BitManipulation {

    private final static long notAFile = 0xfefefefefefefefeL;
    private final static long notHFile = 0x7f7f7f7f7f7f7f7fL;

    static long fillOnce(long num, Direction[] dirs) {
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

    static long fillKnight(long knight) {
        long l1 = (knight >> 1) & 0x7f7f7f7f7f7f7f7fL;
        long l2 = (knight >> 2) & 0x3f3f3f3f3f3f3f3fL;
        long r1 = (knight << 1) & 0xfefefefefefefefeL;
        long r2 = (knight << 2) & 0xfcfcfcfcfcfcfcfcL;
        long h1 = l1 | r1;
        long h2 = l2 | r2;
        return (h1 << 16) | (h1 >>> 16) | (h2 << 8) | (h2 >>> 8);
    }

    static String drawLong(long lng) {
        int c = 64, line = 0;
        StringBuilder bld = new StringBuilder();
        while (c > 0) {
            bld.append((lng & 1) == 0 ? '.' : '1').append(" ");
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

    private static long genShift(long num, int shift) {
        int right = -((shift >>> 8) & shift);
        return (num >>> right) << (right + shift);
    }
}