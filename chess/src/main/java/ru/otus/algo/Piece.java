package ru.otus.algo;

import java.util.Objects;

public final class Piece {
    private final Side side;
    private final Figure figure;
    private final Square square;

    public static Piece of(Side side, Figure figure, Square square) {
        return new Piece(side, figure, square);
    }

    private Piece(Side side, Figure figure, Square square) {
        this.side = side;
        this.figure = figure;
        this.square = square;
    }

    public Side getSide() {
        return side;
    }

    public Figure getFigure() {
        return figure;
    }

    public Square getSquare() {
        return square;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return side == piece.side &&
                figure == piece.figure &&
                square == piece.square;
    }

    @Override
    public int hashCode() {
        return Objects.hash(side, figure, square);
    }

    @Override
    public String toString() {
        return "Piece{" +
                side +
                " " + figure +
                " " + square +
                '}';
    }
}