package ru.otus.algo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import ru.otus.algo.converters.SquareConverter;

import static org.junit.jupiter.api.Assertions.*;

class SquareTest {

    @Test
    void ofTest() {
        for (Square.Rank rank : Square.Rank.values()) {
            for (Square.File file : Square.File.values()) {
                String expected = file.name() + (rank.getValue() + 1);
                assertEquals(expected, Square.of(rank, file).name());
            }
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/add-ranks.csv", delimiter = ';')
    void addRank(@ConvertWith(SquareConverter.class) Square square, int ranksNum, String result) {
        if ("ERR".equals(result))
            assertThrows(IllegalArgumentException.class, () -> Square.addRank(square, ranksNum));
        else {
            Square expected = Square.valueOf(result);
            assertEquals(expected, Square.addRank(square, ranksNum));
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/decrease-rank.csv", delimiter = ';')
    void decreaseRank(@ConvertWith(SquareConverter.class) Square square, int ranksNum, String result) {
        if ("ERR".equals(result))
            assertThrows(IllegalArgumentException.class, () -> Square.decreaseRank(square, ranksNum));
        else {
            Square expected = Square.valueOf(result);
            assertEquals(expected, Square.decreaseRank(square, ranksNum));
        }
    }
}