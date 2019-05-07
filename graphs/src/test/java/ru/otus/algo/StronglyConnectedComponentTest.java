package ru.otus.algo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class StronglyConnectedComponentTest {

    @ParameterizedTest
    @MethodSource("uriProvider")
    void kosarajuTest(URI path, int[] result) {
        int[][] edges = Common.loadEdges(path);
        assertArrayEquals(result, Graphs.getSccKosaraju(edges));
    }


    static Stream<Arguments> uriProvider() throws URISyntaxException {
            return Stream.of(
                Arguments.of(ClassLoader.getSystemResource("simple.graph").toURI(), new int[] {2, 2, 2, 1, 1, 1}),
                Arguments.of(ClassLoader.getSystemResource("test.graph").toURI(), new int[] {8, 8, 8, 8, 7, 7, 7, 7, 6, 5, 2, 2, 4, 3, 2, 2, 3, 2, 1, 1, 1, 1, 1})
                );
    }
}