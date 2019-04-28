package ru.otus.algo;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

/**
 * Adjacency matrix based on {@link BitVector} for directed graph.
 * Vertexes represented in adjacency matrix by integers.
 * {@code matrix} is a square matrix with {@code size} rows.
 * When {@link #connect(int, int)} is call with attributes &gt;= {@code size} matrix will resize.
 * Resize example:
 * Consider {@code matrix}:
 * 0 1 ... 0
 * 1 0 ... 1
 * ...
 * ...
 * 1 1 ... 1
 * {@code size = 8}
 * {@code connect(1, 12}
 * {@code matrix} will be converted to:
 * 0 1 ... 0 0
 * 1 0 ... 1 0
 * ...
 * ...
 * 1 1 ... 1 0
 * nulls omitted
 * <p>
 * {@code size = 12}
 * <p>
 * Bits in a row represents in-degree property of vertex.
 * Bits in a column represents out-degree property of vertex.
 */
public class AdjacencyMatrix {
    private static final Logger LOGGER = LogManager.getLogger();
    private BitVector matrix;
    private int size = DEFAULT_DIM;

    private final static int DEFAULT_DIM = 8;

    AdjacencyMatrix() {
        matrix = BitVector.of(DEFAULT_DIM * DEFAULT_DIM);
    }

    /**
     * Make vertex a and b adjacent.
     * Throws IllegalArgumentException if a or b &lt; 0
     *
     * @param a -
     * @param b -
     */
    void connect(int a, int b) {
        if (a < 0 || b < 0)
            throw new IllegalArgumentException();

        matrix.set(getPosition(b, a));
    }

    /**
     * Checks if vertex a and b is adjacent.
     * Throws IllegalArgumentException if a or b &lt; 0
     * @param a -
     * @param b -
     * @return - true if vertex a and b is connected
     */
    boolean isConnected(int a, int b) {
        if (a < 0 || b < 0)
            throw new IllegalArgumentException();

        return matrix.get(getPosition(b, a));
    }

    /**
     * Removes adjacent between vertex a and b
     * Throws IllegalArgumentException if a or b &lt; 0
     * @param a -
     * @param b -
     */
    void disconnect(int a, int b) {
        if (a < 0 || b < 0)
            throw new IllegalArgumentException();

        matrix.clear(getPosition(b, a));
    }

    /**
     * Checks if vertex has in-degree equals to 0
     * Throws IllegalArgumentException if v &lt; 0
     * @param v - vertex
     * @return true if in-degree of {@code v} is 0
     */
    boolean isZeroInDegrees(int v) {
//        LOGGER.info(String.format("is zero in degree: %d, size: %d", v, size));
        return matrix.isZeroBits(v * size, (v + 1)*size);
    }

    /**
     * Creates an iterable of vertexes that are adjacent with vertex {@code a}
     * Throws IllegalArgumentException if a &lt; 0
     * @param a - vertex
     * @return - iterable
     */
    Iterable<Integer> getConnected(int a) {
        if (a < 0)
            throw new IllegalArgumentException();

        return () -> new Iterator<Integer>() {

            private int it = 0;

            @Override
            public boolean hasNext() {
                while (it < size) {
                    int cur = it * size + a;
                    if (matrix.get(cur)) {
                        return true;
                    }
                    it++;
                }
                return false;
            }

            @Override
            public Integer next() {
                return it;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("AdjacencyMatrix{size: " + size);

        sb.append(System.lineSeparator());
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                char val = matrix.get(size * i + j) ? '1' : '0';
                sb.append(val);
            }
            sb.append(System.lineSeparator());

        }
        sb.append('}');
        return sb.toString();
    }

    private int getPosition(int row, int col) {
        if (row < 0 || col < 0)
            throw new IllegalArgumentException();

        int max = Math.max(row, col);
        if (max > size)
            resize(max+1);

        int pos = size * row + col;
        assert pos < size * size;
        return pos;
    }

    private void resize(int newSize) {
        int shift = newSize - size;
        assert shift > 0;

        for (int i = size; i > 0; i--) {
            matrix.insertBlock(size * i, shift);
        }
        size = newSize;
    }
}