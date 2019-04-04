package ru.otus.algo.measures;

public class SearchResult {
    private final String testName;
    private final long time;
    private final int size;
    private final int height;
    private final int found;


    public static String header() {
        return String.format("%s;%s;%s;%s;%s", "case", "ms", "search_count", "found", "height");
    }

    SearchResult(String testName, long time, int size, int found, int height) {
        this.testName = testName;
        this.time = time;
        this.size = size;
        this.found = found;
        this.height = height;
    }

    @Override
    public String toString() {
        return String.format("%s;%f;%s;%s;%s",
                testName, (double) time / 1_000_000, size, found, height);
    }
}
