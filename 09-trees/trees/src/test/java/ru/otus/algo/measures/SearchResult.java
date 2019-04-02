package ru.otus.algo.measures;

public class SearchResult {
    private final Class<?> cl;
    private final DataSet.DataType type;
    private final long time;
    private final int size;
    private final int found;


    public static String header() {
        return String.format("%s;%s;%s;%s", "case", "ms", "search_count", "found");
    }

    SearchResult(Class<?> cl, long time, DataSet.DataType type, int size, int found) {
        this.cl = cl;
        this.time = time;
        this.type = type;
        this.size = size;
        this.found = found;
    }

    @Override
    public String toString() {
        return String.format("%s %s;%f;%s;%s",
                cl.getSimpleName(), type, (double) time / 1_000_000, size, found);
    }
}
