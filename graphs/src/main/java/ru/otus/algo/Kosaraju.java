package ru.otus.algo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.algo.common.OList;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

class Kosaraju {

    private static final Logger LOGGER = LogManager.getLogger(Kosaraju.class.getSimpleName());

    /*
        Format:
        1st line: # of vertexes
        every next line:
        #vertex #connections_list
    */
    private static int[][] loadEdges(URI path) {
        int[][] res = null;
            try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
                String line = reader.readLine();
                if (line == null)
                    throw new IOException();

                int size = Integer.parseInt(line);
                res = new int[size][];
                while ((line = reader.readLine()) != null) {
                    String[] arr = line.split(" ");

                    int id = Integer.parseInt(arr[0]);
                    int length = arr.length;
                    int[] conns = new int[length-1];
                    for (int i = 1; i< length; i++) {
                        conns[i-1] = Integer.parseInt(arr[i]);
                    }
                    res[id] = conns;
                }

            } catch (IOException e) {
                LOGGER.error(e);
            }

            return res;
    }

    public static void main(String[] args) throws URISyntaxException {
        URI resource = ClassLoader.getSystemResource("oracle.docs.graph").toURI();
        int[][] input = loadEdges(resource);

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

        LOGGER.info("started");
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
        LOGGER.info(Arrays.toString(components));
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