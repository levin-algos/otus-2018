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

    public static Move castle(Side side, Castle castleSide) {
        Square destination = null;
        Square from = side == Side.WHITE? Square.E1 : Square.E8;
        if (side == Side.WHITE)
            destination = castleSide == Castle.KING_SIDE ? Square.G1 : Square.C1;
        else
            destination = castleSide == Castle.QUEEN_SIDE ? Square.C8 : Square.G8;

        return new Move(Piece.of(side, Figure.KING, from), destination, MoveType.CASTLING);
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
