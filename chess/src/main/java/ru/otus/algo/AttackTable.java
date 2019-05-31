package ru.otus.algo;

public class AttackTable {

    public AttackTable() {
        init();
    }

    private static final long[][] attacks = new long[64][64];
    private void init() {
        for (int from = 0; from < 64; from++) {
            for (int to = 0; to < 64; to++) {
                attacks[from][to] = inBetween(from, to);
            }
        }
    }

    public boolean isConnected(int from, int to) {
        if (from > 63 || to > 63)
            throw new IllegalArgumentException();

        if (from == to)
            return true;

        if (isNeighbors(from, to))
            return true;

        return attacks[from][to] != 0;
    }

    private boolean isNeighbors(int from, int to) {
        if (Math.abs(from - to) < 2)
            return true;

        int fromRank = from / 8, toRank = to / 8;
        if (Math.abs(fromRank - toRank) < 2)
            return true;
        return false;
    }

    private long inBetween(int sq1, int sq2) {
        long m1 = -1L;
        long a2a7 = 0x0001010101010100L;
        long b2g7 = 0x0040201008040200L;
        long h1b7 = 0x0002040810204080L;
        long btwn, line, rank, file;

        btwn = (m1 << sq1) ^ (m1 << sq2);
        file = (sq2 & 7) - (sq1 & 7);
        rank = ((sq2 | 7) - sq1) >> 3;
        line = ((file & 7) - 1) & a2a7; /* a2a7 if same file */
        line += 2 * (((rank & 7) - 1) >> 58); /* b1g1 if same rank */
        line += (((rank - file) & 15) - 1) & b2g7; /* b2g7 if same diagonal */
        line += (((rank + file) & 15) - 1) & h1b7; /* h1b7 if same antidiag */
        line *= btwn & -btwn; /* mul acts like shift by smaller square */
        return line & btwn;   /* return the bits on that line in-between */

    }
}
