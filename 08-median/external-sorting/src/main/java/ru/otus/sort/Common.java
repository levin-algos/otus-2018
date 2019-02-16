package ru.otus.sort;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.function.BiConsumer;

class Common {

    static void writeInts(Path path, long offset, long size, SizeUnits units, BiConsumer<Long, ByteBufferArray> fn) {
        try (RandomAccessFile raf = new RandomAccessFile(path.toString(), "rw")) {
            try (FileChannel ch = raf.getChannel()) {
                ch.position(offset*units.getSize());
                long byteCount = size-offset;
                ByteBufferArray buf = new ByteBufferArray(byteCount, units);
                fn.accept(size, buf);
                buf.flip();
                ch.write(buf.toArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if file generated by {@link #writeInts} is sorted
     * starting from {@code start} integer (inclusive) and ends with {@code end} integer (exclusive)
     *
     * @param path  - file path to check
     * @param start - staring number of integer (inclusive)
     * @param end   - finish position of integer (exclusive)
     * @return - true - if file is sorted. Else - false
     */
    static boolean isSortedIntSeq(Path path, long start, long end) throws IOException {
        ByteBufferArray buf = readInts(path, start, end);

        buf.rewind();
        int cursor = Integer.MIN_VALUE;
        for (ByteBuffer b: buf.toArray()) {
            while (b.hasRemaining()) {
                int i = b.getInt();
                if (cursor > i)
                    return false;
                cursor = i;
            }
        }

        return true;
    }

    /**
     * Reads integer array from the {@code path} starting from {@code start}-th integer till {@code end}-th integer.
     * @param path - path to file to be read
     * @param start - starting integer number to be read (inclusive)
     * @param end - integer number till integers will be read
     * @return - IntBuffer array due to length of integer array can be greater than {@code Integer.MAX_VALUE}
     */
    static ByteBufferArray readInts(Path path, long start, long end) throws IOException {
        return readInts0(path, start, end);
    }

    private static ByteBufferArray readInts0(Path path, long start, long end) throws IOException {
        try (FileChannel reader = (FileChannel)Files.newByteChannel(path, EnumSet.of(StandardOpenOption.READ))) {

            ByteBufferArray result = new ByteBufferArray(end - start, SizeUnits.INTEGER);
            reader.position(start*SizeUnits.INTEGER.getSize());
            reader.read(result.toArray());
            return result;
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * Sorting integers from the file and putting them back.
     * If {@code (end - start) * 4} greater than {@code Integer.MAX_VALUE} - throws IllegalArgumentException
     * @param path - path to file
     * @param from - starting integer index (inclusive)
     * @param to - integer index to element next to last sorted element
     */
    static void sort(Path path, long from, long to) throws IOException {
        if ((to - from) * SizeUnits.INTEGER.getSize() > Integer.MAX_VALUE) {
            throw new IllegalArgumentException();
        }

        ByteBufferArray sort = Common.readInts(path, from, to);
        sort.rewind();
        int size = (int)(to - from);
        IntBuffer intBuffer = sort.toArray()[0].order(ByteOrder.BIG_ENDIAN).asIntBuffer();
        int[] arr = new int[size];
        intBuffer.get(arr);
        Arrays.sort(arr);
        Common.writeInts(path, from, to, SizeUnits.INTEGER, (sz, buf) -> {
            for (int i : arr) buf.toArray()[0].putInt(i);
        });
    }
}