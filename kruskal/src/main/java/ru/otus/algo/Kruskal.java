package ru.otus.algo;

import ru.otus.algo.common.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

public class Kruskal {

    public static void main(String[] args) throws URISyntaxException {
        URI uri = ClassLoader.getSystemResource("test.matrix.graph").toURI();

        final int[][] input = Common.load(uri);
        System.out.println(Arrays.toString(Graphs.findMinSpanTree(input)));

    }
}
