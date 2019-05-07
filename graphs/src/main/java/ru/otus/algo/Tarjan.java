package ru.otus.algo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.algo.common.OList;

import java.net.URI;
import java.net.URISyntaxException;

class Tarjan {

    private static final Logger LOGGER = LogManager.getLogger(Tarjan.class.getSimpleName());

    private static final byte WHITE = 0;
    private static final byte GRAY = 1;
    private static final byte BLACK = 2;

    private static final OList<Integer> stack = new OList<>();

    public static void main(String[] args) throws URISyntaxException {
        URI resource = ClassLoader.getSystemResource("topological.sort.graph").toURI();
        int[][] input = Common.loadEdges(resource);

        if (input == null)
            throw new IllegalStateException();

        if (!tarjan(input))
            throw new IllegalArgumentException();
        LOGGER.info(stack);
    }

    private static <T> boolean tarjan(int[][] input) {
        Adjacency<Integer> adjacency = new AdjacencyList<>();
        Common.fill(adjacency, input);

        byte[] colors = new byte[input.length];
        for (int i = 0; i < input.length; i++) {
            if (colors[i] == WHITE) {
                if (!dfs(adjacency, i, colors))
                    return false;
            }
        }
        return true;
    }

    private static boolean dfs(Adjacency<Integer> v, int i, byte[] color) {
        color[i] = GRAY;
        for (Integer u: v.getConnected(i)) {
            if (color[u] == WHITE) {
                if (!dfs(v, u, color)) {
                    return false;
                }
            } else {
                if (color[u] == GRAY) {
                    LOGGER.error("cycles in graph");
                    return false;
                }
            }
        }

        color[i] = BLACK;
        stack.addFirst(i);
        return true;
    }
}
