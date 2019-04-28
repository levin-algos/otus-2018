package ru.otus.algo;

public class Edge {
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
