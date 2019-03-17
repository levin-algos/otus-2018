package ru.otus.algo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HashesTest {
    private static final int M = 32;

    @ParameterizedTest
    @MethodSource("hashProducer")
    void determinismTest(Hash<Integer> hash) {
        assertEquals(hash.get(100), hash.get(100));
    }

    static Stream<Hash<Integer>> hashProducer() {
        int A = 31;
        int W = 64;
        int p = 3;
        return Stream.of(
                new TrivialHash<>(M),
                new MultiplicativeHash<>(A, W, M),
                new PerfectHash.Builder<>().coeffs(31, 13).p(37).size(M).build());
    }
}
