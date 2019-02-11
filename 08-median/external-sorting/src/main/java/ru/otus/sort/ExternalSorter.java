package ru.otus.sort;

import java.nio.file.Path;

public class ExternalSorter {

    private Heap<ExternalSortElement> heap = new Heap<>();

    public static void sort(Path file, int chunkNum) {

    }

    private final class ExternalSortElement implements Comparable<ExternalSortElement> {
        int element;

        @Override
        public int compareTo(ExternalSortElement o) {
            return Integer.compare(element, o.element);
        }
    }
}