package ru.otus.algo;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.otus.algo.common.Common;
import ru.otus.algo.common.DArray;
import ru.otus.algo.common.DynamicArray;
import ru.otus.algo.common.UnionFind;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Kruskal {

    private static final Logger LOGGER = LogManager.getLogger(Dijkstra.class.getSimpleName());

    private static final Random rnd = new Random();

    private static final DynamicArray<Edge> edges = new DArray<>();

    public static void main(String[] args) throws URISyntaxException {
        URI uri = ClassLoader.getSystemResource("oracle.docs.graph").toURI();
        int[][] input = Common.loadEdges(uri);

        LOGGER.info("creating edge list.");
        for (int i = 0; i < input.length; i++) {
            if (input[i] == null)
                continue;
            for (int j: input[i]) {
                int w = rnd.nextInt();
                edges.add(new Edge(i, j, w));
            }
        }

        UnionFind<Integer> unionFind = new UnionFind<>();

        LOGGER.info("sorting.");
        edges.sort(Comparator.comparing(o -> o.weight));

        LOGGER.info("building MST.");
        List<Edge> span = new ArrayList<>();
        for (Edge e: edges) {
            Integer xRoot = unionFind.find(e.x);
            if (xRoot == null || !xRoot.equals(unionFind.find(e.y))) {
                unionFind.union(e.x, e.y);
                span.add(e);
            }
        }
        LOGGER.info(span);
    }

    private static class Edge {
        final int x;
        final int y;
        final int weight;

        Edge(int x, int y, int weight) {
            this.x = x;
            this.y = y;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "x=" + x +
                    ", y=" + y +
                    ", weight=" + weight +
                    '}';
        }
    }

}
