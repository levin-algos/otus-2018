package ru.otus.sort;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class ExternalSorterTest {

    @Test
    public void sort() throws IOException {
        Path path = Paths.get("long-file");
        int chunkCount = 10;
        ExternalSorter.sort(path, chunkCount);

        assertTrue(checkSorted(path, chunkCount));
    }

    private boolean checkSorted(Path path, int chunkCount) throws IOException {
        long size = Files.size(path);
        long intCount = size / 4;
        int chunkSize = (int)intCount / chunkCount, i;
        for (i = 1; i < chunkCount; i++) {
            if (!Common.isSortedIntSeq(path, chunkSize*(i-1), chunkSize*i))
                return false;
        }

        return true;
    }

    @Test
    public void generateData() throws IOException {
        Path path = Paths.get("long-file");
        long size = 1 << 30-1;
        Common.writeInts(path, 0, size, SizeUnits.INTEGER, (sz, buf) -> {
            for (int i = 0; i < sz; i++) {
                buf.add((int)(i & Integer.MAX_VALUE));
            }
        });

        assertTrue(Files.exists(path));
        assertEquals(size*SizeUnits.INTEGER.getSize(), Files.size(path));
    }


}