package ru.otus.algo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.otus.algo.parsers.FenParser;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PerftTest {
    static final private String startingPos = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

    @ParameterizedTest
    @CsvSource({
            "0, 1",
            "1, 20",
            "2, 400",
            "3, 8902"
    })
    void perft(int depth, int nodes) {
        final Position initialPos = new FenParser().parse(startingPos, new Position.Builder());

        assertEquals(nodes, initialPos.perft(depth));
    }
}