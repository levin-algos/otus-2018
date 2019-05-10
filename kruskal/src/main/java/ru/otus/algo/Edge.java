package ru.otus.algo;

import java.util.Objects;

public class Edge<T> {
    final T v1;
    final T v2;
    final int weight;

    Edge(T v1, T v2, int weight) {
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
                v2 == edge.v2 &&
                weight == edge.weight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(v1, v2, weight);
    }
}