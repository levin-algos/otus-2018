package ru.otus.algo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AttackTableTest {

    private static final AttackTable attack = new AttackTable();

    @ParameterizedTest
    @MethodSource("fileSource")
    void checkFilesConnected(int from, int to) {
        assertTrue(attack.isConnected(from, to));
    }

    @ParameterizedTest
    @MethodSource("rankSource")
    void checkRankConnected(int from, int to) {
        assertTrue(attack.isConnected(from, to));
    }

    @ParameterizedTest
    @MethodSource("diagSource")
    void checkDiagConnected(int from, int to) {
        assertTrue(attack.isConnected(from, to));
    }


    @ParameterizedTest
    @MethodSource("antiDiagSource")
    void checkAntiDiagConnected(int from, int to) {
        assertTrue(attack.isConnected(from, to));
    }


    private boolean checkConnectedAntiDiags(int sq, AttackTable attack) {
        int r = sq / 8, f = sq % 8;
        for (int i = (r + f) * 8; i > 0; i -= 7)
            if (!attack.isConnected(sq, i)) return false;
        return true;
    }

    private static Stream<Arguments> diagSource() {
        final Stream.Builder<Arguments> builder = Stream.builder();

        for (int from = 0; from < 64; from ++) {
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

        for (int from = 0; from < 64; from ++) {
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

        for (int from = 0; from < 64; from ++) {
            int f = from % 8;
            for (int i = 0; i < 8; i++)
                builder.add(Arguments.of(from, f + 8 * i));
        }

        return builder.build();
    }

    private static Stream<Arguments> rankSource() {
        final Stream.Builder<Arguments> builder = Stream.builder();

        for (int from = 0; from < 64; from ++) {
            int r = from / 8;
            for (int i = 0; i < 8; i++)
                builder.add(Arguments.of(from, i + 8 * r));
        }

        return builder.build();
    }
}