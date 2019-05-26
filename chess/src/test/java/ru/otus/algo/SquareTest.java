package ru.otus.algo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SquareTest {

    @Test
    void ofTest() {
        for (Square.Rank rank: Square.Rank.values()) {
            for (Square.File file: Square.File.values()) {
                String expected = file.name()+(rank.getValue()+1);
                assertEquals(expected, Square.of(rank, file).name());
            }
        }
    }
}