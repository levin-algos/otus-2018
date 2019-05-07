package ru.otus.algo;

import ru.otus.algo.common.HashMap;
import ru.otus.algo.common.Map;
import ru.otus.algo.common.OList;

import java.util.*;

public class AdjacencyMatrix<T> implements Adjacency<T> {

    private final Map<T, Integer> verts;
    private final BitSet[] matrix;
    private final int size;

    public AdjacencyMatrix(int vertexCount) {
        size = vertexCount;
        matrix = new BitSet[vertexCount];
        verts = new HashMap<>(key -> key.hashCode());
    }

    @Override
    public void connect(T a, T b) {
        Objects.requireNonNull(a);
        Objects.requireNonNull(b);

        int rowNum = getVertexNumber(a);
        BitSet row = this.matrix[rowNum];
        if (row == null)
            row = new BitSet(size);

        row.set(getVertexNumber(b));
        matrix[rowNum] = row;
    }

    @Override
    public boolean isConnected(T a, T b) {
        Objects.requireNonNull(a);
        Objects.requireNonNull(b);
        if (a.equals(b))
            return true;

        BitSet row = this.matrix[getVertexNumber(a)];

        if (row == null)
            return false;

        return row.get(getVertexNumber(b));
    }

    @Override
    public void disconnect(T a, T b) {
        Objects.requireNonNull(a);
        Objects.requireNonNull(b);

        BitSet row = this.matrix[getVertexNumber(a)];
        if (row == null) {
            return;
        }

        row.clear(getVertexNumber(b));
    }

    @Override
    public Iterable<T> getConnected(T a) {
        Objects.requireNonNull(a);

        BitSet row = matrix[getVertexNumber(a)];
        if (row == null)
            return () -> new Iterator<T>() {
                @Override
                public boolean hasNext() {
                    return false;
                }

                @Override
                public T next() {
                    return null;
                }
            };
        return () -> new Iterator<T>() {

            private final Iterator<Map.Entry<T, Integer>> it = verts.entrySet().iterator();
            private T res;

            @Override
            public boolean hasNext() {
                while (it.hasNext()) {

                    Map.Entry<T, Integer> next = it.next();
                    if (row.get(next.getValue())) {
                        res = next.getKey();
                        return true;
                    }
                }
                return false;
            }

            @Override
            public T next() {
                return res;
            }
        };
    }

    OList<OList<T>> topologicalSort() {
        int[] m = new int[matrix.length];
        OList<T> queue = new OList<>();

        for (Map.Entry<T, Integer> i : verts.entrySet()) {
            Integer row = i.getValue();
            m[row] = rowSum(row);
            queue.add(i.getKey());
        }

        OList<OList<T>> orders = new OList<>();
        while (queue.size() != 0) {
            OList<T> zero = new OList<>();
            for (T c: queue) {
                if (m[getVertexNumber(c)] == 0) {
                    zero.add(c);
                }
            }
            if (zero.size() == 0)
                throw new IllegalStateException("cycles in graph!");
            OList<T> order = new OList<>();
            for (T i : zero) {
                order.add(i);
                queue.remove(i);
                recalcSum(queue, m, i);
            }
            orders.add(order);
        }
        return orders;
    }

    private void recalcSum(OList<T> queue, int[] m, T u) {
        for (T w : queue) {
            int id = getVertexNumber(w);
            BitSet col = matrix[getVertexNumber(u)];
            if (col == null)
                return;

            boolean b = col.get(id);
            m[id] -= b ? 1 : 0;
        }
    }

    private int rowSum(Integer row) {
        int sum = 0;
        for (BitSet col : matrix) {
            if (col != null)
                if (col.get(row)) sum++;
        }
        return sum;
    }

    @Override
    public Adjacency<T> invert() {
        return new InvertAdjacencyMatrix<>(this);
    }

    private class InvertAdjacencyMatrix<R> implements Adjacency<R> {

        private final AdjacencyMatrix<R> matrix;

        InvertAdjacencyMatrix(AdjacencyMatrix<R> matrix) {
            this.matrix = matrix;
        }

        @Override
        public void connect(R a, R b) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isConnected(R a, R b) {
            return matrix.isConnected(b, a);
        }

        @Override
        public void disconnect(R a, R b) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Iterable<R> getConnected(R a) {
            Objects.requireNonNull(a);

            return () -> new Iterator<R>() {
                private final Iterator<Map.Entry<R, Integer>> it = matrix.verts.entrySet().iterator();
                private final int col = matrix.getVertexNumber(a);
                private R res;

                @Override
                public boolean hasNext() {
                    while (it.hasNext()) {

                        Map.Entry<R, Integer> next = it.next();
                        BitSet row = matrix.matrix[next.getValue()];
                        if (row == null)
                            continue;

                        if (row.get(col)) {
                            res = next.getKey();
                            return true;
                        }
                    }
                    return false;
                }

                @Override
                public R next() {
                    return res;
                }
            };
        }

        @Override
        public Adjacency<R> invert() {
            return matrix;
        }
    }

    private int vertCount;

    private int getVertexNumber(T vertex) {
        if (verts.containsKey(vertex)) {
            return verts.get(vertex);
        } else {
            if (vertCount >= size)
                throw new IllegalStateException();

            verts.put(vertex, vertCount);
            return vertCount++;
        }
    }
}