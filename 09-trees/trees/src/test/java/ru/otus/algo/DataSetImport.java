package ru.otus.algo;


import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.stream.Stream;

public class DataSetImport {

    public static String [] parseFile(String strpath) {
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


    public static void main(String[] argv ) {
        System.out.println("import");
        String strpath = "wiki.test-32615-a12319.tokens";

        String [] words = parseFile(strpath);
        System.out.println(words.length);
        for(String word : words)
        System.out.println(word);
    }

}