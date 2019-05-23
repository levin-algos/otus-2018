package ru.otus.algo;

import java.util.HashSet;
import java.util.Set;

public class King extends AbstractFigure {

    private final Direction[] dirs = {Direction.NORTH_WEST, Direction.NORTH, Direction.NORTH_EAST,
                                      Direction.WEST,                        Direction.EAST,
                                      Direction.SOUTH_WEST, Direction.SOUTH, Direction.SOUTH_EAST  };
    public King(Board board, int position, Side side) {
        super(board, position, side, Figures.KING);
    }

    @Override
    public Iterable<Integer> getMoves() {
        int position = getPosition();
        Bitboard board = Bitboard.of(position);
        board.fillOnce(dirs);
        return board;
    }
}