package ru.otus.algo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FindPrefixTest
{
    @ParameterizedTest
    @MethodSource("preprocessProvider")
    void findPrefixTest(Data data) {
        int[] prefixes = BoyerMoor.findPrefixes(data.text);
        assertArrayEquals(data.prefix, prefixes);
    }

    static Stream<Data> preprocessProvider() throws IOException, URISyntaxException {
        URI uri = ClassLoader.getSystemClassLoader().getResource("preprocess_test_cases.txt").toURI();
        return Files.readAllLines(Paths.get(uri)).stream().map(Data::new);
    }

    private static class Data {
        String text;
        int[] prefix;

        Data(String s) {
            if (s == null)
                return;

            String[] args = s.split(" ");

            if (args.length < 1)
                return;

            text = args[0];
            prefix = new int[args.length-1];
            for (int i = 1; i < args.length; i++)
                prefix[i-1] = Integer.parseInt(args[i]);
        }
    }
}