package ru.otus.algo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class FiguresTest {

    private static Board board;

    @BeforeEach
    void init() {
        board = Board.of();
    }

    @Test
    void kingTest() {
        board.add(Figures.KING, Side.WHITE, 4);

        Figure figure = board.get(4);

        assertEquals(4, figure.getPosition());
        assertEquals(Side.WHITE, figure.getSide());
        assertEquals(Figures.KING, figure.getFigureType());

        HashSet<Integer> expected = new HashSet<>();
        Collections.addAll(expected, 3, 11, 12, 13, 5);

        assertIterableEquals(expected, figure.getMoves());
        figure.move(3);

        figure = board.get(3);
        assertNotNull(figure);
        assertEquals(3, figure.getPosition());
        assertEquals(Side.WHITE, figure.getSide());
        assertEquals(Figures.KING, figure.getFigureType());
    }

    @Test
    void test() {
        long l = 0xfefefefefefefefeL;
        System.out.println(Common.longToBinary(l));
    }
}
