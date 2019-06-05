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
        Set<String> m = allMoves.stream().sequential()
                .map(move -> String.format("%s%s", move.getFrom(), move.getDestination()))
                .collect(Collectors.toSet());

        assertEquals(moves, m);
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
                Move.white(Figure.PAWN, Square.F3, Square.F4)
        );

        assertEquals(expected, pos.getAllMoves());
    }
}