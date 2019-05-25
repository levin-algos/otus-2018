package ru.otus.algo;

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

    public static long genShift(long num, int shift) {
        int right = -((shift >>> 8) & shift);
        return (num >>> right) << (right + shift);
    }


}
