package ru.otus.algo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;

public class Dictionary {

    List<String> dict;

    public Dictionary(Path path) throws IOException {
        dict = Files.readAllLines(path);
    }


}
