package ru.otus.algo;

import java.util.Objects;

public class Move {
    private final Figure figure;
    private final Side side;
    private final Square from;
    private final Square destination;

    private Move(Side side, Figure figure, Square from, Square destination) {

        this.figure = figure;
        this.side = side;
        this.from = from;
        this.destination = destination;
    }

    static Move white(Figure figure, Square from, Square dest) {
        return new Move(Side.WHITE, figure, from, dest);
    }
    static Move black(Figure figure, Square from, Square dest) {
        return new Move(Side.BLACK, figure, from, dest);
    }

    @Override
    public String toString() {
        return "Move{" +
                side +
                " " + figure +
                destination +
                '}';
    }

    public Figure getFigure() {
        return figure;
    }

    public Side getSide() {
        return side;
    }

    public Square getFrom() {
        return from;
    }

    public Square getDestination() {
        return destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return figure == move.figure &&
                side == move.side &&
                from == move.from &&
                destination == move.destination;
    }

    @Override
    public int hashCode() {
        return Objects.hash(figure, side, from, destination);
    }
}
