package ru.otus.algo;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class GraphTest {

    @Test
    void connect() {
        Graph graph = new Graph();

        graph.connect(References.of(1), References.of(2));
        graph.connect(References.of(2), References.of(3));

        assertIterableEquals(Arrays.asList(References.of(1)), graph.getWithZeroInDegree());
        graph.remove(References.of(1));
        assertIterableEquals(Arrays.asList(References.of(2)), graph.getWithZeroInDegree());
        graph.remove(References.of(2));
        assertIterableEquals(Arrays.asList(References.of(3)), graph.getWithZeroInDegree());
        graph.remove(References.of(3));
        assertIterableEquals(Collections.emptyList(), graph.getWithZeroInDegree());
    }
}
