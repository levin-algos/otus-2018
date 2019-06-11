package ru.otus.algo;

import java.util.Objects;

public class Move {
    private final Piece piece;
    private final Square destination;
    private final MoveType type;

    private Move(Piece piece, Square destination, MoveType type) {
        this.piece = piece;
        this.destination = destination;
        this.type = type;
    }

    static Move of(Piece piece, Square dest) {
        return new Move(piece, dest, MoveType.NORMAL);
    }

    static Move of(Piece piece, Square dest, MoveType type) {
        return new Move(piece, dest, type);
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

    public MoveType getType() {
        return type;
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
