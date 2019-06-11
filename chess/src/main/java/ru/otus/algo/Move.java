package ru.otus.algo;

import java.util.Objects;

public class Move {
    private final Piece piece;
    private final Square destination;
    private final MoveType type;
    private final Figure promotion;

    private Move(Piece piece, Square destination, MoveType type) {
        this.piece = piece;
        this.destination = destination;
        this.type = type;
        promotion = null;
    }

    private Move(Piece piece, Square destination, MoveType type, Figure promote) {
        this.piece = piece;
        this.destination = destination;
        this.type = type;
        promotion = promote;
    }

    static Move of(Piece piece, Square dest) {
        return new Move(piece, dest, MoveType.NORMAL);
    }

    static Move enPassant(Piece piece, Square dest) {
        return new Move(piece, dest, MoveType.EN_PASSANT);
    }

    static Move promote(Piece piece, Square dest, Figure figure) {
        return new Move(piece, dest, MoveType.PROMOTION, figure);
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

    public Figure getPromotion() {
        return promotion;
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
                destination == move.destination &&
                type == move.type &&
                promotion == move.promotion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(piece, destination, type, promotion);
    }
}
