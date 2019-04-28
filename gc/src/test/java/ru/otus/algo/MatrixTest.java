package ru.otus.algo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MatrixTest {

    private final int a = 1;
    private final int b = 2;

    @Test
    void isConnected() {
        AdjacencyMatrix adj = new AdjacencyMatrix();

        adj.connect(a, b);
        assertTrue(adj.isConnected(a, b));
        assertFalse(adj.isConnected(b, a));

        adj.disconnect(a, b);
        assertFalse(adj.isConnected(a, b));
        assertFalse(adj.isConnected(b, a));
    }

    @Test
    void isConnectedAfterResize() {
        AdjacencyMatrix adj = new AdjacencyMatrix();

        adj.connect(a, b);
        System.out.println(adj);
        adj.connect(9, 1);
        System.out.println(adj);
        assertTrue(adj.isConnected(a, b));
        assertFalse(adj.isConnected(b, a));

        adj.disconnect(a, b);
        assertFalse(adj.isConnected(a, b));
        assertFalse(adj.isConnected(b, a));
    }
}
