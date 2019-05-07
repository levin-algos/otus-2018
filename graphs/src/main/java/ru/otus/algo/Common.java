package ru.otus.algo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

class Common {
    private Common() {}

    private final static Logger LOGGER = LogManager.getLogger(Common.class.getSimpleName());

    /*
        Format:
        1st line: # of vertexes
        every next line:
        #vertex #connections_list
    */
    public static int[][] loadEdges(URI path) {
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

    public static void fill(Adjacency<Integer> adj, int[][] input) {
        for (int i = 0; i < input.length; i++) {
            if (input[i] == null)
                continue;
            for (int j: input[i]) {
                adj.connect(i, j);
            }
        }
    }
}
