package ru.otus.algo.parsers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.otus.algo.*;

import java.awt.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.algo.Square.*;

class FenParserTest {

    @Test
    void parse() {
        String initialPos = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

        Position pos = new FenParser().parse(initialPos, new Position.Builder());

        Set<Piece> whites = new HashSet<>();
        whites.add(Piece.of(Side.WHITE, Figure.KING, E1));
        Collections.addAll(whites, Pieces.pawns(Side.WHITE, A2, B2, C2, D2, E2, F2, G2, H2));
        Collections.addAll(whites, Pieces.rooks(Side.WHITE, A1, H1));
        Collections.addAll(whites, Pieces.knights(Side.WHITE, B1, G1));
        Collections.addAll(whites, Pieces.bishops(Side.WHITE, C1, F1));
        Collections.addAll(whites, Pieces.queens(Side.WHITE, D1));
        Set<Piece> blacks = new HashSet<>();
        blacks.add(Piece.of(Side.BLACK, Figure.KING, E8));
        Collections.addAll(blacks, Pieces.pawns(Side.BLACK, A7, B7, C7, D7, E7, F7, G7, H7));
        Collections.addAll(blacks, Pieces.rooks(Side.BLACK, A8, H8));
        Collections.addAll(blacks, Pieces.knights(Side.BLACK, B8, G8));
        Collections.addAll(blacks, Pieces.bishops(Side.BLACK, C8, F8));
        Collections.addAll(blacks, Pieces.queens(Side.BLACK, D8));

        assertEquals(whites, pos.getPieces(Side.WHITE));
        assertEquals(blacks, pos.getPieces(Side.BLACK));
        assertEquals(Side.WHITE, pos.getSideToMove());
        assertEquals(0, pos.getHalfMoveClock());
        assertFalse(pos.getEnPassant().isPresent());
        assertTrue(pos.canCastle(Side.WHITE, Castle.KINGS_SIDE));
        assertTrue(pos.canCastle(Side.WHITE, Castle.QUEENS_SIDE));
        assertTrue(pos.canCastle(Side.BLACK, Castle.KINGS_SIDE));
        assertTrue(pos.canCastle(Side.BLACK, Castle.QUEENS_SIDE));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/wrong-fens.txt")
    void parseWrongFormat(String fen) {
        assertThrows(ParseError.class, () -> new FenParser().parse(fen, new Position.Builder()));
    }
}