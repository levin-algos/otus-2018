package ru.otus.algo;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class HashTest {

    private static List<String> dict;


    private static Hash<String> hash = new TrivialHash<>();


    public static void main(String[] args) throws IOException, URISyntaxException {
        Path path = Paths.get(HashTest.class.getClassLoader().getResource("dictionary.txt").toURI());

        dict = Files.readAllLines(path);
        double[] bits = new double[32];
        int[] bins = new int[16];

        for (String w: dict) {
            int h = hash.get(w);
            bins[15 & h]++;
            int[] b = getBits(h);
            sumArrays(bits, b);
        }

        normalize(bits, dict.size());
        System.out.println(Arrays.toString(bits));
        System.out.println(Arrays.toString(bins));
    }

    private static void normalize(double[] bits, int word_count) {
        for (int i =0; i< bits.length; i++) {
            bits[i] = bits[i] / word_count - 0.5;
        }
    }

    private static void sumArrays(double[] bits, int[] b) {
        for (int i = 0; i < b.length; i++) {
            bits[i] += b[i];
        }
    }

    private static int[] getBits(int hash) {
        int[] bits = new int[32];
        int i = 31;
        while (hash !=0 && i >= 0) {
            if ((hash & 1) == 1)
                bits[i] = 1;
            hash = hash >> 1;
            i--;
        }
        return bits;
    }
}
