package ru.otus.algo;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class AdjacencyTest
{
    private final Integer a = 1;
    private final Integer b = 2;
    private final Integer c = 3;
    private Adjacency<Integer> adj;

    @BeforeEach
    void before() {
        adj = new AdjacencyList<>();
    }

    @Test
    void isConnected() {
        assertTrue(adj.isConnected(a, a));

        adj.connect(a, b);
        assertTrue(adj.isConnected(a, b));
        assertFalse(adj.isConnected(b, a));

        adj.disconnect(a, b);
        assertFalse(adj.isConnected(a, b));
        assertFalse(adj.isConnected(b, a));
    }

    @Test
    void getConnectedList() {
        adj.connect(a, b);
        adj.connect(a, c);

        assertIterableEquals(Arrays.asList(b, c), adj.getConnected(a));
        assertIterableEquals(Collections.emptyList(), adj.getConnected(b));
        assertIterableEquals(Collections.emptyList(), adj.getConnected(c));
    }

    @Test
    void invertAdjacency() {
        adj.connect(a, b);
        adj.connect(a, c);

        Adjacency<Integer> invert = adj.invert();

        assertTrue(invert.isConnected(b, a));
        assertTrue(invert.isConnected(c, a));

        assertFalse(invert.isConnected(a, b));
        assertFalse(invert.isConnected(a, c));

        assertIterableEquals(Collections.emptyList(), invert.getConnected(a));
        assertIterableEquals(Collections.singletonList(a), invert.getConnected(b));
        assertIterableEquals(Collections.singletonList(a), invert.getConnected(c));
    }
}