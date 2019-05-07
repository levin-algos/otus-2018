package ru.otus.algo;

import java.util.Objects;

public class Edge {
    final int v1;
    final int v2;
    final int weight;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return v1 == edge.v1 &&
                v2 == edge.v2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(v1, v2);
    }
}