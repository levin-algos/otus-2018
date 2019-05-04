package ru.otus.algo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class BoyerMoorTest {

    @ParameterizedTest
    @MethodSource("dataProvider")
    void test(Data data) {
        List<Integer> actual = BoyerMoor.boyerMoor(data.text, data.pattern);
        assertIterableEquals(data.result, actual, data.pattern);
    }

    static Stream<Data> dataProvider() throws URISyntaxException, IOException {
        URI resource = ClassLoader.getSystemClassLoader().getResource("string_matching_test_cases.tsv").toURI();
        return Files.readAllLines(Paths.get(resource)).stream().map(Data::new);
    }

    private static class Data {
        String text;
        String pattern;
        List<Integer> result;

        Data(String text) {
            String[] args = text.split("\t");
            assert args.length <=3;
            this.text = args[0];
            pattern = args[1];
            if (args.length == 3) {
                result = Arrays.stream(args[2].split(" "))
                        .map(Integer::parseInt).collect(Collectors.toList());

            } else result = Collections.emptyList();
        }
    }
}
