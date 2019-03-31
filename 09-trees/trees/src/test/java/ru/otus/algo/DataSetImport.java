package ru.otus.algo;


import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.stream.Stream;

final class DataSetImport {

    private DataSetImport() {
    }

    private static String path;
    private static String[] data;

    public static String[] getData(String file, int size) {
        if (path == null || !path.equals(file)) {
            path = file;
            data = parseFile(path);

            if (data == null)
                throw new IllegalStateException();
        }

        int newSize = size > data.length ? data.length : size;
        String[] res = new String[newSize];
        System.arraycopy(data, 0, res, 0, newSize);
        return res;
    }

    private static String[] parseFile(String strpath) {
        URL resource = Thread.currentThread().getContextClassLoader().getResource(strpath);
        try (Stream<String> lines = Files.lines(Paths.get(resource.getPath()))) {
            ArrayList<String> result = new ArrayList<>();
            lines.forEach(s -> {
                StringTokenizer token = new StringTokenizer(s, " .,<>@-=():_';\"");
                while (token.hasMoreTokens())
                    result.add(token.nextToken());
            });
            return result.toArray(new String[0]);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
}