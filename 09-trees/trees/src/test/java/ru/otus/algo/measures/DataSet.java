package ru.otus.algo.measures;


import ru.otus.algo.Pair;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

final class DataSet {

    DataSet(String path) {
        data = parseFile(path);
    }

    private List<String> data;
    private static Random rnd = new Random();

    static List<Long> generateLongData(DataType type, int size) {
        List<Long> longs = new ArrayList<>();
        if (DataType.RND == type) {
            for (int i = 0; i < size; i++)
                longs.add(Math.abs(rnd.nextLong()));
        } else if (DataType.ASC == type) {
            longs = LongStream.rangeClosed(0, size).boxed().collect(Collectors.toList());
        } else {
            throw new UnsupportedOperationException();
        }

        return longs;
    }

    List<String> getData() {
        if (data == null)
            throw new IllegalStateException();

        return data;
    }

    List<String> getRandomTokens(int size) {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int pos = rnd.nextInt(data.size());
            strings.add(data.get(pos));
        }

        return strings;
    }

    List<Pair<String, Integer>> getFrequencyPairs() {
        Map<String, Integer> map = new HashMap<>();

        for (String entity: data) {
            Integer val = map.get(entity);
            Integer newVal = val == null? 1 : val+1;
            map.put(entity, newVal);
        }
        return Pair.combine(map.keySet().toArray(new String[0]), map.values().toArray(new Integer[0]));
    }

    private List<String> parseFile(String strpath) {
        URL resource = Thread.currentThread().getContextClassLoader().getResource(strpath);
        try (Stream<String> lines = Files.lines(Paths.get(resource.getPath()))) {
            ArrayList<String> result = new ArrayList<>();
            lines.forEach(s -> {
                StringTokenizer token = new StringTokenizer(s, " .,<>@-=():_';\"");
                while (token.hasMoreTokens())
                    result.add(token.nextToken());
            });
            return result;
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    enum DataType {
        RND,
        ASC,
        TOKENS,
        CYCLE
    }
}