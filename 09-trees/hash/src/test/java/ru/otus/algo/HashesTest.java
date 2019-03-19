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
        assertEquals(hash.get(100, M), hash.get(100, M));
    }


    @Test
    void getBaseTest() {
        MultiplicativeHash<String> hash = new MultiplicativeHash<>(7, 32);
        for (int i=0; i < 31; i++)
            assertEquals(i+1, hash.getPower(1 << i), ""+i);
    }

    /*




     */

    static Stream<Hash<Integer>> hashProducer() {
        int A = 31;
        int W = 64;
        return Stream.of(
                new TrivialHash<>(),
                new MultiplicativeHash<>(A, W),
                new PerfectHash.Builder<>().coeffs(31, 13).p(37).build());
    }
}
