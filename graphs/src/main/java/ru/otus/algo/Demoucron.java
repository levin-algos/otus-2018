package ru.otus.algo;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.otus.algo.common.OList;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Objects;

class Demoucron {

    private static final Logger LOGGER = LogManager.getLogger(Demoucron.class.getSimpleName());

    public static void main(String[] args) throws URISyntaxException {
        URI resource = ClassLoader.getSystemResource("topological.sort.graph").toURI();
        int[][] input = Common.loadEdges(resource);

        Objects.requireNonNull(input);

        AdjacencyMatrix<Integer> matrix = new AdjacencyMatrix<>(input.length);
        Common.fill(matrix, input);

        OList<OList<Integer>> res = matrix.topologicalSort();

        int[][] demoucron = new int[res.size()][];
        int colNum = 0;
        for (OList<Integer> list: res) {
            int[] col = new int[list.size()];
            int count = 0;
            for (Integer i: list)
                col[count++] = i;
            demoucron[colNum++] = col;
        }

        for (int[] col: demoucron) {
            LOGGER.info(Arrays.toString(col));
        }
    }
}