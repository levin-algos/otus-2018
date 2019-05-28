package ru.otus.algo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.otus.algo.parsers.FenParser;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PositionTest {

    private final FenParser parser = new FenParser();

    @ParameterizedTest
    @CsvFileSource(resources = "/fens.txt", delimiter = ';')
    void getAllMoves(String fen, @ConvertWith(MovesConverter.class) Set<String> moves) {

        final Position position = parser.parse(fen, new Position.Builder());


        final Set<Move> allMoves = position.getAllMoves();
        Set<String> m = allMoves.stream()
                .map(move -> String.format("%s%s", move.getFrom(), move.getDestination()))
                .collect(Collectors.toSet());

        System.out.println(moves.containsAll(m));
        assertEquals(moves, m);
    }

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

    @Test
    void exp01() {
        long a = 1_143_472_128L;
        long b =  -9187201950435737472L;

        System.out.println(drawLong(a, 8));
        System.out.println(drawLong(b, 8));
        System.out.println(drawLong(a*b, 8));
        System.out.println(a*b);
    }

    private String drawLong(long lng, int bitsInLine) {
        int c = 64, line = 0;
        StringBuilder bld = new StringBuilder();
        while (c > 0) {
            bld.append((lng & 1) == 0? '.': '1').append(" ");
            line++;
            if (line == 8) {
                bld.append(System.lineSeparator());
                line = 0;
            }
            lng = lng >>> 1;
            c--;
        }
        return bld.toString();
    }
}