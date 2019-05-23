package ru.otus.algo;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardTest {

    private static Set<Figure> WHITE_PAWNS;

    @Test
    void creationTest() {
        Board board = Board.of();

         assertEquals(WHITE_PAWNS, board.get(Figures.PAWN, Side.WHITE));
    }
}