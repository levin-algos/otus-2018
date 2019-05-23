package ru.otus.algo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FiguresTest {

    private static Board board;

    @BeforeEach
    void init() {
        board = Board.of();
    }

    @Test
    void creationTest() {
        board.add(Figures.KING, Side.WHITE, Position.E1);

        Figure figure = board.get(Position.E1);

        assertEquals(Position.E1, figure.getPosition());
        assertEquals(Side.WHITE, figure.getSide());
        assertEquals(Figures.KING, figure.getFigureType());
    }
}
