package ru.otus.algo;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AttackTableTest {

    private static final AttackTable attack = new AttackTable();

    @Nested
    class RookTest {
        @ParameterizedTest
        @MethodSource("ru.otus.algo.AttackTableTest#rankSource")
        void inBetweenRankTest(int from, int to) {
            if (from > to) {
                int tmp = to;
                to = from;
                from = tmp;
            }
            assertFalse(attack.inBetween(Figure.ROOK, from, to, from));
            assertFalse(attack.inBetween(Figure.ROOK, from, to, to));
            for (int i = from + 1; i < to; i++) {
                assertTrue(attack.inBetween(Figure.ROOK, from, to, i));
            }
        }

        @ParameterizedTest
        @MethodSource("ru.otus.algo.AttackTableTest#fileSource")
        void inBetweenFileTest(int from, int to) {
            if (from > to) {
                int tmp = to;
                to = from;
                from = tmp;
            }
            assertFalse(attack.inBetween(Figure.ROOK, from, to, from));
            assertFalse(attack.inBetween(Figure.ROOK, from, to, to));
            for (int i = from + 8; i < to; i += 8) {
                assertTrue(attack.inBetween(Figure.ROOK, from, to, i));
            }
        }

        @ParameterizedTest
        @MethodSource("ru.otus.algo.AttackTableTest#diagSource")
        void inBetweenDiagTest(int from, int to) {
            if (from > to) {
                int tmp = to;
                to = from;
                from = tmp;
            }
            assertFalse(attack.inBetween(Figure.ROOK, from, to, from));
            assertFalse(attack.inBetween(Figure.ROOK, from, to, to));
            for (int i = from + 9; i < to; i += 9) {
                assertFalse(attack.inBetween(Figure.ROOK, from, to, i));
            }
        }

        @ParameterizedTest
        @MethodSource("ru.otus.algo.AttackTableTest#antiDiagSource")
        void inBetweenAntiDiagTest(int from, int to) {
            if (from > to) {
                int tmp = to;
                to = from;
                from = tmp;
            }
            assertFalse(attack.inBetween(Figure.ROOK, from, to, from));
            assertFalse(attack.inBetween(Figure.ROOK, from, to, to));
            for (int i = from + 7; i < to; i += 7) {
                assertFalse(attack.inBetween(Figure.ROOK, from, to, i));
            }
        }
    }


    @Nested
    class BishopTest {
        @ParameterizedTest
        @MethodSource("ru.otus.algo.AttackTableTest#rankSource")
        void inBetweenRankTest(int from, int to) {
            if (from > to) {
                int tmp = to;
                to = from;
                from = tmp;
            }
            assertFalse(attack.inBetween(Figure.BISHOP, from, to, from));
            assertFalse(attack.inBetween(Figure.BISHOP, from, to, to));
            for (int i = from + 1; i < to; i++) {
                assertFalse(attack.inBetween(Figure.BISHOP, from, to, i));
            }
        }

        @ParameterizedTest
        @MethodSource("ru.otus.algo.AttackTableTest#fileSource")
        void inBetweenFileTest(int from, int to) {
            if (from > to) {
                int tmp = to;
                to = from;
                from = tmp;
            }
            assertFalse(attack.inBetween(Figure.BISHOP, from, to, from));
            assertFalse(attack.inBetween(Figure.BISHOP, from, to, to));
            for (int i = from + 8; i < to; i += 8) {
                assertFalse(attack.inBetween(Figure.BISHOP, from, to, i));
            }
        }

        @ParameterizedTest
        @MethodSource("ru.otus.algo.AttackTableTest#diagSource")
        void inBetweenDiagTest(int from, int to) {
            if (from > to) {
                int tmp = to;
                to = from;
                from = tmp;
            }
            assertFalse(attack.inBetween(Figure.BISHOP, from, to, from));
            assertFalse(attack.inBetween(Figure.BISHOP, from, to, to));
            for (int i = from + 9; i < to; i += 9) {
                assertTrue(attack.inBetween(Figure.BISHOP, from, to, i));
            }
        }

        @ParameterizedTest
        @MethodSource("ru.otus.algo.AttackTableTest#antiDiagSource")
        void inBetweenAntiDiagTest(int from, int to) {
            if (from > to) {
                int tmp = to;
                to = from;
                from = tmp;
            }
            assertFalse(attack.inBetween(Figure.BISHOP, from, to, from));
            assertFalse(attack.inBetween(Figure.BISHOP, from, to, to));
            for (int i = from + 7; i < to; i += 7) {
                assertTrue(attack.inBetween(Figure.BISHOP, from, to, i));
            }
        }
    }


    private static Stream<Arguments> diagSource() {
        final Stream.Builder<Arguments> builder = Stream.builder();

        for (int from = 0; from < 64; from++) {
            for (int i = from; i > 0; i -= 9) {
                builder.add(Arguments.of(from, i));
                if (i % 8 == 0) break;
            }
            if (from % 8 != 7) {
                for (int i = from + 9; i < 64; i += 9) {

                    builder.add(Arguments.of(from, i));
                    if (i % 8 == 7) break;
                }
            }
        }
        return builder.build();
    }

    private static Stream<Arguments> antiDiagSource() {
        final Stream.Builder<Arguments> builder = Stream.builder();

        for (int from = 0; from < 64; from++) {
            for (int i = from; i > 0; i -= 7) {
                builder.add(Arguments.of(from, i));
                if (i % 8 == 7) break;
            }
            if (from % 8 != 0) {
                for (int i = from + 7; i < 64; i += 7) {

                    builder.add(Arguments.of(from, i));
                    if (i % 8 == 0) break;
                }
            }
        }
        return builder.build();
    }

    private static Stream<Arguments> fileSource() {
        final Stream.Builder<Arguments> builder = Stream.builder();

        for (int from = 0; from < 64; from++) {
            int f = from % 8;
            for (int i = 0; i < 8; i++)
                builder.add(Arguments.of(from, f + 8 * i));
        }

        return builder.build();
    }

    private static Stream<Arguments> rankSource() {
        final Stream.Builder<Arguments> builder = Stream.builder();

        for (int from = 0; from < 64; from++) {
            int r = from / 8;
            for (int i = 0; i < 8; i++)
                builder.add(Arguments.of(from, i + 8 * r));
        }
        return builder.build();
    }
}