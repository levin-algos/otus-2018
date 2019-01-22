package ru.otus;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessPlayerTest {

    @Test
    void createPlayer() {
        ChessPlayer chessPlayer = new ChessPlayer(100, "Ivan", Sex.MALE, 1000, "GM");
        assertEquals(100, chessPlayer.getId());
        assertEquals("Ivan", chessPlayer.getDef());
        assertEquals(Sex.MALE, chessPlayer.getSex());
        assertEquals(1000, chessPlayer.getRating());
        assertEquals("GM", chessPlayer.getTitle());
    }

    @Test
    void load() {

    }

    @Test
    void compareTo() {
    }
}