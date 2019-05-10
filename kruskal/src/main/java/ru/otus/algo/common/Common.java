package ru.otus.algo.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class Common {
    private Common() {}

    private final static Logger LOGGER = LogManager.getLogger(Common.class.getSimpleName());


    public static int[][] load(URI path) {
        int[][] res = null;
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
            String line = reader.readLine();
            if (line == null)
                throw new IOException();

            int size = Integer.parseInt(line);
            res = new int[size][size];
            int i = 0;
            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(" ");

                assert arr.length == size;

                for (int j = 0; j < arr.length; j++) {
                    res[i][j] = Integer.parseInt(arr[j]);
                }

                i++;
            }

        } catch (IOException e) {
            LOGGER.error(e);
        }

        return res;
    }
}