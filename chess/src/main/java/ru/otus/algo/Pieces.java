package ru.otus.algo;

import java.util.HashSet;
import java.util.Set;

public final class Pieces {

    private Pieces() {
    }

    public static Piece[] pawns(Side side, Square... squares) {
        return pieces(side, Figure.PAWN, squares);
    }

    public static Piece[] rooks(Side side, Square... squares) {
        return pieces(side, Figure.ROOK, squares);
    }

    public static Piece[] bishops(Side side, Square... squares) {
        return pieces(side, Figure.BISHOP, squares);
    }

    public static Piece[] knights(Side side, Square... squares) {
        return pieces(side, Figure.KNIGHT, squares);
    }

    public static Piece[] queens(Side side, Square... squares) {
        return pieces(side, Figure.QUEEN, squares);
    }

    private static Piece[] pieces(Side side, Figure figure, Square... squares) {
        Set<Piece> pawns = new HashSet<>();

        for (Square s : squares)
            pawns.add(Piece.of(side, figure, s));

        return pawns.toArray(new Piece[0]);
    }
}