package ru.otus.algo;

import java.util.*;

class Graph {
    private final AdjacencyMatrix adjacency = new AdjacencyMatrix();
    private Map<Integer, Node> vertexes = new HashMap<>();

    void connect(Node a, Node b) {
        Objects.requireNonNull(a); Objects.requireNonNull(b);

        vertexes.put(a.getId(), a);
        vertexes.put(b.getId(), b);

        adjacency.connect(a.getId(), b.getId());
    }

    void disconnect(Node a, Node b) {
        Objects.requireNonNull(a); Objects.requireNonNull(b);

        adjacency.disconnect(a.getId(), b.getId());
    }

    void remove(Node v) {
        Objects.requireNonNull(v);

        int id = v.getId();
        for (int i: adjacency.getConnected(id)) {
            adjacency.disconnect(id, i);
        }
        vertexes.remove(id);
    }

    Iterable<Node> getWithZeroInDegree() {
        return () -> new Iterator<Node>() {

            Iterator<Map.Entry<Integer, Node>> it = vertexes.entrySet().iterator();
            Node cur;
            @Override
            public boolean hasNext() {
                while (it.hasNext()) {
                    Map.Entry<Integer, Node> next = it.next();
                    if (adjacency.isZeroInDegrees(next.getKey())) {
                        cur = next.getValue();
                        return true;
                    }
                }
                return false;
            }

            @Override
            public Node next() {
                return cur;
            }
        };
    }
}