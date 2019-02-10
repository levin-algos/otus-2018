package ru.otus.sort;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.*;

public class CommonTest {

    @Test
    public void generateIntTest() throws IOException {
        int[] sizes = new int[] {10,100, 1000, 10000, Integer.MAX_VALUE/4};
        for (int size: sizes) {
            Path path = Paths.get("test"+size+".txt");
            Common.writeInts(path, 0, size, (sz, buf) -> {
                for (int i = sz; i > 0; i--) buf.putInt(i);});

            assertTrue(Files.exists(path));
            assertEquals(4*size, Files.size(path));
            Files.delete(path);
        }
    }

    @Test
    public void testIsSorted() throws IOException {
        Path path = Paths.get("sorted-test");
        if (Files.exists(path))
            Files.delete(path);

        int size = 1000;
        Common.writeInts(path,0, size, (sz, buf) -> {
            for (int i = sz; i > 0; i--) buf.putInt(i);});

        assertTrue(Files.exists(path));
        assertEquals(4*size, Files.size(path));

        int halfSize = size / 2;
        IntBuffer sort = Common.readInts(path, 0, halfSize);
        int[] arr = new int[halfSize];
        sort.get(arr);
        Arrays.sort(arr);
        Common.writeInts(path, 0, halfSize, (sz, buf) -> {for (int i: arr) buf.putInt(i);});

        sort = Common.readInts(path, halfSize, size);
        int[] arr1 = new int[halfSize];
        sort.get(arr1);
        Arrays.sort(arr1);
        Common.writeInts(path, halfSize, size, (sz, buf) -> {for (int i: arr1) buf.putInt(i);});

        assertTrue(Common.isSortedIntSeq(path, 0, halfSize));
        assertTrue(Common.isSortedIntSeq(path, halfSize, size));
    }

    @Test
    public void writeReadTest() throws IOException {
        int[] arr = new int[] {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};

        Path path = Paths.get("write-read");
        if (Files.exists(path))
            Files.delete(path);

        Common.writeInts(path, 0, arr.length, (integer, buf) -> {
            for (int i: arr) buf.putInt(i);});

        assertTrue(Files.exists(path));
        assertEquals(arr.length*4, Files.size(path));

        IntBuffer intBuffer = Common.readInts(path, 0, arr.length);
        assertNotNull(intBuffer);

        int[] actual = new int[arr.length];
        intBuffer.get(actual);

        assertArrayEquals(arr, actual);

    }
}