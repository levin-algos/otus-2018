package ru.otus.algo;

abstract class AbstractFigure implements Figure {

    private final int position;
    private final Side side;
    private final Figures figure;
    private final Board board;

    public AbstractFigure(Board board, int position, Side side, Figures figure) {
        this.board = board;
        this.position = position;
        this.side = side;
        this.figure = figure;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public Side getSide() {
        return side;
    }

    @Override
    public Figures getFigureType() {
        return figure;
    }

    public Board getBoard() {
        return board;
    }
}
