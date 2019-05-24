package ru.otus.algo;

import java.util.Set;

/**
 * Each figure on the board has it's own bitboard (8 bytes)
 * Result board can be represented with 12 bitboards:
 * 1 set of buffers for white and black figures.
 * buffer contains all figures of current color and current type (e.g. all white bishops)
 */
public class Board {

    private final long[][] buffers;

    private Board() {
        this.buffers = new long[2][6];
    }

    public static Board of() {
        return new Board();
    }

    public Set<Figure> get(Figures figures, Side side) {
        return null;
    }

    public Figure add(Figures figure, Side side, int position) {
        long buf = buffers[side.getValue()][figure.getValue()];

        final long b = buf & (1L << position);
        if (b != 0) {
            throw new IllegalArgumentException("spot already taken");
        }

        Figure result = null;
        long tmp = 1L << position;
        buffers[side.getValue()][figure.getValue()] |= tmp;

        if (Figures.KING == figure) {
            result = new King(this, position, side);
        }

        return result;
    }

    public Figure get(int position) {
        for (Side s: Side.values()) {
            for (Figures f: Figures.values()) {
                final long b = buffers[s.getValue()][f.getValue()] & (1L << position);
                if (b != 0) {
                    if (Figures.KING == f)
                        return new King(this, position, s);
                }
            }
        }
        return null;
    }
}
