package ru.otus.algo;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.otus.algo.common.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

class Dijkstra {

private static final Logger LOGGER = LogManager.getLogger(Dijkstra.class.getSimpleName());

    private static final Random rnd = new Random();

    private static final DynamicArray<Edge> edges = new DArray<>();

    public static void main(String[] args) throws URISyntaxException {
        URI uri = ClassLoader.getSystemResource("simple.graph").toURI();
        int[][] input = Common.load(uri);

        Adjacency<Integer> adjacency = new AdjacencyList<>();

        LOGGER.info("creating edge list.");
        for (int i = 0; i < input.length; i++) {
            if (input[i] == null)
                continue;
            for (int j: input[i]) {
                adjacency.connect(i, j);
            }
        }
        int A = 0;

        Queue<Vertex> q = new PriorityQueue<>(Comparator.comparing(Vertex::getMark).reversed());
        Vertex[] vertexes = new  Vertex[input.length];
        Vertex e1 = new Vertex(A, 0);
        q.add(e1);
        vertexes[A] = e1;

        for (int i=0; i< input.length; i++) {
            if (i != A) {
                Vertex e = new Vertex(i, Integer.MAX_VALUE);
                q.add(e);
                vertexes[i] = e;
            }
        }

        DynamicArray<Edge> edges = new DArray<>();
        while (!q.isEmpty()) {
            Vertex u = q.poll();

            for (Integer v: adjacency.getConnected(u.id)) {
                Vertex vertex = vertexes[v];
                if (vertex != null) {
                    int alt = u.mark + length(u.id, v);
                    if (alt < vertex.mark) {
                        vertex.mark = alt;
//                        edges.add(new Edge(u.id, ));
                    }
                }
//                int alt = dist[u.id]+length(u.id, v);


            }
        }
    }

    private static Integer length(int id, Integer v) {
        return Math.abs(rnd.nextInt(8));
    }

    private static Integer[] generateMarks(int length) {
        if (length <1)
            throw new IllegalArgumentException();

        Integer[] marks = new Integer[length];
        for (int i = 0; i < length; i++) {
            marks[i] = Integer.MAX_VALUE;
        }
        return marks;
    }

    private static class Vertex {
        final int id;
        int mark;

        Vertex(int id, int mark) {
            this.id = id;
            this.mark = mark;

        }

        private int getMark() { return mark; }
    }

}