package ru.otus.algo;

import java.util.Iterator;

public class Bitboard implements Iterable<Integer> {

    private long board;

    private Bitboard(int pos) {
        board = 1L << pos;
    }

    private Bitboard(Bitboard that) {
        this.board = that.board;
    }

    static Bitboard of(int pos) {
        return new Bitboard(pos);
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

    private final long notAFile = 0xfefefefefefefefeL;
    private final long notHFile = 0x7f7f7f7f7f7f7f7fL;

    public void fillOnce(Direction[] dirs) {
        long res = 0L;
        for (Direction d : dirs) {
            if (Direction.EAST == d || Direction.NORTH_EAST == d || Direction.SOUTH_EAST == d) {
                res |= genShift(board, d.getValue()) & notAFile;
            } else if (Direction.WEST == d || Direction.SOUTH_WEST == d || Direction.NORTH_WEST == d) {
                res |= genShift(board, d.getValue()) & notHFile;
            } else
                res |= genShift(board, d.getValue());
        }
        System.out.println(Common.longToBinary(res));
        board = res;
    }
}
