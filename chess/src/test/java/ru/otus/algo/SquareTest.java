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

    @Test
    void genData() {
        final char[] chars = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H' };
        for (int file = 0; file < chars.length; file++) {
            for (int i = 1; i < 9; i++) {
                String square = "" + chars[file] + i;
                for (int k = 0; k < 9; k++) {
                    int resFile = file - k;
                    String res;
                    if (k == 0 || resFile < 0)
                        res = "ERR";
                    else
                        res = "" + chars[resFile]+i;

                    System.out.println(String.format("%s;%s;%s", square, k, res));
                }
            }
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/decrease-file.csv", delimiter = ';')
    void decreaseFile(@ConvertWith(SquareConverter.class) Square square, int fileNum, String result) {
        if ("ERR".equals(result))
            assertThrows(IllegalArgumentException.class, () -> Square.decreaseFile(square, fileNum));
        else {
            Square expected = Square.valueOf(result);
            assertEquals(expected, Square.decreaseFile(square, fileNum));
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/add-file.csv", delimiter = ';')
    void addFile(@ConvertWith(SquareConverter.class) Square square, int fileNum, String result) {
        if ("ERR".equals(result))
            assertThrows(IllegalArgumentException.class, () -> Square.addFile(square, fileNum));
        else {
            Square expected = Square.valueOf(result);
            assertEquals(expected, Square.addFile(square, fileNum));
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