package ru.otus.algo;

import ru.otus.algo.common.*;
import ru.otus.algo.common.HashMap;
import ru.otus.algo.common.HashSet;
import ru.otus.algo.common.Map;
import ru.otus.algo.common.Set;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

class Dijkstra {

    private final int[] dist;
    private final int[] path;

    public Dijkstra(int[][] matrix, int startingPoint) {
        Objects.requireNonNull(matrix);
        int verts = matrix.length;
        dist = new int[verts];
        path = new int[verts];

        for (int i = 0; i < dist.length; i++) {
            dist[i] = i == startingPoint ? 0 : -1;
            path[i] = i == startingPoint ? 0 : -1;
        }

        Set<Vertex> s = new HashSet<>();
        Vertex[] vertexes = new Vertex[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i] == null)
                continue;
            Vertex from = getVertex(i);
            for (int j=0; j < matrix[i].length; j++) {
                if (matrix[i][j] != 0)
                    from.addEdge(getVertex(j), matrix[i][j]);
            }
            if (i == startingPoint)
                from.mark = 0;

            vertexes[i] = from;
            s.add(from);
        }

        Heap<Vertex> q = new Heap<>(vertexes);

        while (q.size() != 0) {
            Vertex u = q.peek();

            for (Edge<Vertex> v : u.getEdges()) {
                if (s.contains(v.v2) && !v.v1.equals(v.v2)) {
                    int alt = u.mark + v.weight;
                    if (alt < v.v2.mark) {
                        v.v2.mark = alt;
                        dist[v.v2.value] = alt;
                        path[v.v2.value] = u.value;
                    }
                }
            }
            q.remove(u);
            s.remove(u);
        }
    }

    public int[] getDist() {
        return dist;
    }

    public int[] getPath() {
        return path;
    }

    public static void main(String[] args) throws URISyntaxException {
        URI uri = ClassLoader.getSystemResource("1.graph").toURI();
        Dijkstra d = new Dijkstra(Common.load(uri), 0);

        System.out.println(Arrays.toString(d.getDist()));
        System.out.println(Arrays.toString(d.getPath()));
    }

    private static final Map<Integer, Vertex> vertexes = new HashMap<>(Objects::hashCode);

    private static Vertex getVertex(int i) {
        Vertex vertex = vertexes.get(i);
        if (vertex == null) {
            vertex = new Vertex(i, Integer.MAX_VALUE/2);
            vertexes.put(i, vertex);
        }
        return vertex;
    }

    private static class Vertex implements Comparable<Vertex> {
        final int value;
        int mark;
        private Set<Edge<Vertex>> edges;

        Vertex(int value, int mark) {
            this.value = value;
            edges = new HashSet<>();
            this.mark = mark;
        }

        Set<Edge<Vertex>> getEdges() {
            return edges;
        }

        void addEdge(Vertex vertex, int weight) {
            edges.add(new Edge<>(this, vertex, weight));
        }

        @Override
        public String toString() {
            return "Vertex{" +
                    "value=" + value +
                    ", mark=" + mark +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vertex vertex = (Vertex) o;
            return value == vertex.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }


        @Override
        public int compareTo(Vertex o) {
            return -Integer.compare(mark, o.mark);
        }
    }

}