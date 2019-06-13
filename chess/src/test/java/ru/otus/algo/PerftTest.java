package ru.otus.algo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import ru.otus.algo.parsers.FenParser;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PerftTest {
    static final private String startingPos = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

    @ParameterizedTest
    @CsvFileSource(resources = "/perft.csv")
    void perft(int depth, int nodes) {
        final Position initialPos = new FenParser().parse(startingPos, new Position.Builder());

        assertEquals(nodes, initialPos.perft(depth));
    }

}