package ru.otus.algo;

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
        return board.fillOnce(dirs);
    }

    @Override
    public void move(int i) {

    }
}