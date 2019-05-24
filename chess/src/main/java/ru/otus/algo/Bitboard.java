package ru.otus.algo;

import java.util.Iterator;


/**
 * Immutable realisation of bitset for keeping figure positions
 *
 *
 */
public final class Bitboard implements Iterable<Integer> {

    private final long board;

    private Bitboard(int pos) {
        board = 1L << pos;
    }

    private Bitboard(long that) {
        this.board = that;
    }

    static Bitboard of(int pos) {
        return new Bitboard(pos);
    }

    static Bitboard of(long that) {
        return new Bitboard(that);
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {

            long b = board;
            int pos = 0;
            @Override
            public boolean hasNext() {
                return b != 0;
            }

            @Override
            public Integer next() {
                int delta = Long.numberOfTrailingZeros(b);
                pos+=delta;
                b = b >>> (delta+1);
                return pos++;
            }
        };
    }

    private long genShift(long num, int shift) {
        int right = -((shift >>> 8) & shift);
        return (num >>> right) << (right + shift);
    }

    private final static long notAFile = 0xfefefefefefefefeL;
    private final static long notHFile = 0x7f7f7f7f7f7f7f7fL;

    public Bitboard fillOnce(Direction[] dirs) {
        long res = 0L;
        for (Direction d : dirs) {
            if (Direction.EAST == d || Direction.NORTH_EAST == d || Direction.SOUTH_EAST == d) {
                res |= genShift(board, d.getValue()) & notAFile;
            } else if (Direction.WEST == d || Direction.SOUTH_WEST == d || Direction.NORTH_WEST == d) {
                res |= genShift(board, d.getValue()) & notHFile;
            } else
                res |= genShift(board, d.getValue());
        }
        return Bitboard.of(res);
    }
}