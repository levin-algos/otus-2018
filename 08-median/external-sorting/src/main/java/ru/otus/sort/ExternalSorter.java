package ru.otus.sort;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;

public class ExternalSorter {

    private Heap<ExternalSortElement> heap = new Heap<>();

    private final int MIN_CHUNK_SIZE = 100;

    public static void sort(Path file, int chunkCount) {
        if (!Files.exists(file))
            throw new IllegalArgumentException("file not found");

        try {
            sortChunks(file, chunkCount);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void sortChunks(Path file, int chunkCount) throws IOException {
        long size = 0;
        try {
            size = Files.size(file);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (size % 4 != 0)
            throw new IllegalArgumentException("size % 4 != 0");

        long intCount = size / 4;
        int chunkSize = (int)intCount / chunkCount, i;
        for (i = 1; i < chunkCount; i++) {
            Common.sort(file, chunkSize*(i-1), chunkSize*i);
        }
        Common.sort(file, chunkCount*chunkSize, (int)intCount);
    }

    private final class ExternalSortElement implements Comparable<ExternalSortElement> {
        int element;
        FileChannel ch;

        @Override
        public int compareTo(ExternalSortElement o) {
            return Integer.compare(element, o.element);
        }
    }
}