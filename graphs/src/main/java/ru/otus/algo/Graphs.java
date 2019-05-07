package ru.otus.algo;

import ru.otus.algo.common.OList;

class Graphs {

    private Graphs() {}

    /**
     * Calculates strongly connected components
     * @param input - adjacency vector
     * @return - array of connected components
     */
    static int[] getSccKosaraju(int[][] input) {

        if (input == null)
            throw new IllegalStateException();

        Adjacency<Integer> adjacency = new AdjacencyList<>();

        boolean[] visited = new boolean[input.length];
        OList<Integer> q = new OList<>();
        for (int i = 0; i < input.length; i++) {
            if (input[i] == null)
                continue;
            for (int j: input[i]) {
                adjacency.connect(i, j);
            }
        }

        Adjacency<Integer> invert = adjacency.invert();

        for (int i = 0; i< input.length; i++) {
            if (!visited[i]) {
                dfs1(invert, i, visited, q);
            }
        }

        int[] components = new int[input.length];
        int component = 1;
        while (q.size() > 0) {
            Integer i = q.removeLast();
            if (components[i] <= 0) {
                dfs2(adjacency, i, components, component);
                component++;
            }
        }
        return components;
    }

    private static void dfs2(Adjacency<Integer> adjacency, Integer i, int[] components, int component) {
        components[i] = component;
        for (Integer u: adjacency.getConnected(i)) {
            if (components[u] <= 0)
                dfs2(adjacency, u, components, component);
        }
    }

    private static void dfs1(Adjacency<Integer> adjacency, Integer i, boolean[] visited, OList<Integer> q) {
        visited[i] = true;
        for (Integer vec: adjacency.getConnected(i)) {
            if (!visited[vec]) {
                dfs1(adjacency, vec, visited, q);
            }
        }
        q.add(i);
    }
}