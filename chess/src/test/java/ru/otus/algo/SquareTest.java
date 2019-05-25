package ru.otus.algo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SquareTest {

    @Test
    void ofTest() {
        for (Square s: Square.values()) {
            assertEquals(s, Square.of(s.getValue()));
        }
    }
}