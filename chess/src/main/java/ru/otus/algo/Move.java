package ru.otus.algo;

import java.util.Objects;

public class Move {
    private final Piece piece;
    private final Square destination;

    private Move(Piece piece, Square destination) {
        this.piece = piece;
        this.destination = destination;
    }

    static Move of(Piece piece, Square dest) {
        return new Move(piece, dest);
    }

    public Figure getFigure() {
        return piece.getFigure();
    }

    public Side getSide() {
        return piece.getSide();
    }

    public Square getFrom() {
        return piece.getSquare();
    }

    public Square getDestination() {
        return destination;
    }

    public Piece getPiece() {
        return piece;
    }

    @Override
    public String toString() {
        return "Move{" +
                "piece=" + piece +
                ", destination=" + destination +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return piece.equals(move.piece) &&
                destination == move.destination;
    }

    @Override
    public int hashCode() {
        return Objects.hash(piece, destination);
    }
}
