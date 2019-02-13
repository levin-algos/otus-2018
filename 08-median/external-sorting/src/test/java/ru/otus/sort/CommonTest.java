package ru.otus.sort;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class CommonTest {

    @Test
    public void generateIntTest() throws IOException {
        long[] sizes = new long[]{10, 100, 1000, 10000, Integer.MAX_VALUE / 4};
        for (long size : sizes) {
            Path path = Paths.get("test" + size + ".txt");
            Common.writeInts(path, 0, size, SizeUnits.INTEGER, (sz, buf) -> {
                for (long i = 0; i < size; i++)
                    buf.add((int) (i % Integer.MAX_VALUE));
            });

            assertTrue(checkSize(path, 4 * size));
            Files.delete(path);
        }
    }

    @Test
    public void testIsSorted() throws IOException {
        Path path = Paths.get("sorted-test");
        if (Files.exists(path))
            Files.delete(path);

        int size = 1000;
        Common.writeInts(path, 0, size, SizeUnits.INTEGER, (sz, buf) -> {
            for (long i = 0; i < size; i++)
                buf.add((int) ((size - i) % Integer.MAX_VALUE));
        });

        assertTrue(checkSize(path, 4 * size));

        int halfSize = size / 2;
        Common.sort(path, 0, halfSize);
        Common.sort(path, halfSize, size);

        assertTrue(Common.isSortedIntSeq(path, 0, halfSize));
        assertTrue(Common.isSortedIntSeq(path, halfSize, size));
    }

    private boolean checkSize(Path path, long i) {
        try {
            return Files.exists(path) && Files.size(path) == i;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Test
    public void writeReadTest() throws IOException {
        int[] arr = new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1, 0};

        Path path = Paths.get("write-read");
        if (Files.exists(path))
            Files.delete(path);

        Common.writeInts(path, 0, arr.length, SizeUnits.INTEGER, (integer, buf) -> {
            buf.add(arr);
        });

        assertTrue(checkSize(path, 4 * arr.length));

        ByteBufferArray buffer = Common.readInts(path, 0, arr.length);
        assertNotNull(buffer);


        buffer.rewind();
        assertEquals(0, buffer.position());

        for (int i: arr) {
            assertEquals(i, buffer.getInt());
        }

        assertEquals(arr.length, buffer.position());
    }
}