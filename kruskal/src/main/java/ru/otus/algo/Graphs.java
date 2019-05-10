package ru.otus.algo;

import ru.otus.algo.common.DArray;
import ru.otus.algo.common.DynamicArray;
import ru.otus.algo.common.UnionFind;

import java.util.Comparator;

class Graphs {

    static final class Edge {
        int v1;
        int v2;
        int weight;

        Edge(int v1, int v2, int weight) {
            this.v1 = v1;
            this.v2 = v2;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "v1=" + v1 +
                    ", v2=" + v2 +
                    ", weight=" + weight +
                    '}';
        }
    }

    static Edge[] findMinSpanTree(int[][] matrix) {
        DynamicArray<Edge> edges = new DArray<>();
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i] == null)
                continue;
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != 0) {
                    edges.add(new Edge(i, j , matrix[i][j]));
                }
            }
        }

        UnionFind<Integer> unionFind = new UnionFind<>();

        edges.sort(Comparator.comparing(o -> o.weight));

        DynamicArray<Edge> span = new DArray<>();
        for (Edge e: edges) {
            Integer xRoot = unionFind.find(e.v1);
            if (xRoot == null || !xRoot.equals(unionFind.find(e.v2))) {
                unionFind.union(e.v1, e.v2);
                span.add(e);
            }
        }
        return span.toArray(new Edge[0]);
    }

}
