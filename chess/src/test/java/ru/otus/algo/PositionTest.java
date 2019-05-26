package ru.otus.algo;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PositionTest {

    @Test
    void creationTest() {
        Position pos = Position.of(Side.WHITE, Figure.KING, Square.E1);

        Set<Move> expectedMoves = new HashSet<>();
        Collections.addAll(expectedMoves,
                Move.white(Figure.KING, Square.E1, Square.D1),
                Move.white(Figure.KING, Square.E1, Square.D2),
                Move.white(Figure.KING, Square.E1, Square.E2),
                Move.white(Figure.KING, Square.E1, Square.F1),
                Move.white(Figure.KING, Square.E1, Square.F2));

        assertEquals(expectedMoves, pos.getAllMoves());

        Position newPosition = pos.move(Move.white(Figure.KING, Square.E1, Square.E2));
        expectedMoves = new HashSet<>();

        Collections.addAll(expectedMoves,
                Move.white(Figure.KING, Square.E2, Square.D1),
                Move.white(Figure.KING, Square.E2, Square.D2),
                Move.white(Figure.KING, Square.E2, Square.D3),
                Move.white(Figure.KING, Square.E2, Square.E1),
                Move.white(Figure.KING, Square.E2, Square.E3),
                Move.white(Figure.KING, Square.E2, Square.F1),
                Move.white(Figure.KING, Square.E2, Square.F2),
                Move.white(Figure.KING, Square.E2, Square.F3));

        assertEquals(expectedMoves, newPosition.getAllMoves());
    }

    @Test
    void pawnTest() {
        Position pos = new Position.Builder()
                .add(Pieces.pawns(Side.WHITE, Square.E2, Square.D2, Square.F2, Square.F3))
                .add(Piece.of(Side.WHITE, Figure.KING, Square.E1))
                .build();

        HashSet<Move> expected = new HashSet<>();
        Collections.addAll(expected,
                Move.white(Figure.KING, Square.E1, Square.D1),
                Move.white(Figure.KING, Square.E1, Square.F1),
                Move.white(Figure.PAWN, Square.E2, Square.E3),
                Move.white(Figure.PAWN, Square.E2, Square.E4),
                Move.white(Figure.PAWN, Square.D2, Square.D3),
                Move.white(Figure.PAWN, Square.D2, Square.D4),
//                Move.white(Figure.PAWN, Square.F2, Square.F3)
                Move.white(Figure.PAWN, Square.F3, Square.F4)
        );

        assertEquals(expected, pos.getAllMoves());
    }
}