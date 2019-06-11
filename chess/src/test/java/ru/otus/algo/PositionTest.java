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

//        System.out.println(moves.containsAll(m));
        assertEquals(moves, m);
    }
}