package ru.otus.algo.measures;


import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

final class DataSet {

    public DataSet(String path) {
        this.path = path;
        data = parseFile(path);
    }

    private String path;
    private List<String> data;

    public List<String> getData(int size) {
        if (data == null)
            throw new IllegalStateException();

        int newSize = size > data.size() ? data.size() : size;
        return data.subList(0, newSize);
    }

    public List<String> getRandomTokens(int size) {
        List<String> strings = new ArrayList<>();
        Random rnd = new Random();
        for (int i = 0; i < size; i++) {
            int pos = rnd.nextInt(data.size());
            strings.add(data.get(pos));
        }

        return strings;
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
}