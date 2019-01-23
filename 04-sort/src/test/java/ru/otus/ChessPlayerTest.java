package ru.otus;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChessPlayerTest {

    @Test
    void createPlayer() {
        ChessPlayer chessPlayer = new ChessPlayer("100", "Ivan", Sex.MALE, 1000, "GM");
        assertEquals("100", chessPlayer.getId());
        assertEquals("Ivan", chessPlayer.getName());
        assertEquals(Sex.MALE, chessPlayer.getSex());
        assertEquals(1000, chessPlayer.getRating());
        assertEquals("GM", chessPlayer.getTitle());
    }

    @Test
    void testEquals() {
        ChessPlayer x = new ChessPlayer("35077023", "A Chakravarthy", Sex.MALE, 1151, "");
        ChessPlayer y = new ChessPlayer("35077023", "A Chakravarthy", Sex.MALE, 1151, "");
        ChessPlayer z = new ChessPlayer("3507702", "A Chakravarthy", Sex.MALE, 1151, "");

        assertEquals(x, y);
        assertEquals(y, x);
        assertEquals(x, x);
        assertNotEquals(x, z);
    }

    @Test
    void loadXML() {
        ChessPlayer expected = new ChessPlayer("35077023", "A Chakravarthy", Sex.MALE, 1151, "");
        List<ChessPlayer> chessPlayers = ChessPlayer.loadXML("standard_rating_list.xml", 10);

        assertEquals(10, chessPlayers.size());
        assertEquals(expected, chessPlayers.get(0));
    }

    @Test
    void compareTo() {
        ChessPlayer x = new ChessPlayer("35077023", "A Chakravarthy", Sex.MALE, 1151, "");
        ChessPlayer y = new ChessPlayer("5045886", "A K, Kalshyan", Sex.MALE, 1826, "");

        assertTrue(x.compareTo(y) < 0);
        assertEquals(0, x.compareTo(x));
        assertTrue(y.compareTo(x) > 0);
    }
}