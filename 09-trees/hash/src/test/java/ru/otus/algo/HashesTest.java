package ru.otus.algo;

import org.junit.jupiter.api.Test;
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
        return Stream.of(
                new TrivialHash<>(),
                new PerfectHash.Builder<>().coeffs(31, 13).p(37).build());
    }
}
