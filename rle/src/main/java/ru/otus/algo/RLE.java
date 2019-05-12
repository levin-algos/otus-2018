package ru.otus.algo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Implementation of run-length encoding algorithm.
 */
class RLE {

    static void encode(FileChannel source, FileChannel dest) throws IOException {
        Objects.requireNonNull(source);
        Objects.requireNonNull(dest);

        readWrite(source, dest, byteBuffer -> ByteBuffer.wrap(encode(byteBuffer.array())));
    }

    static void decode(FileChannel source, FileChannel dest) throws IOException {
        Objects.requireNonNull(source);
        Objects.requireNonNull(dest);

        readWrite(source, dest, byteBuffer -> ByteBuffer.wrap(decode(byteBuffer.array())));
    }

    static boolean contentEquals(FileChannel a, FileChannel b) throws IOException {
        if (a.size() != b.size())
            return false;

        int c1, c2;
        do {
            ByteBuffer buffer1 = ByteBuffer.allocate(1024);
            ByteBuffer buffer2 = ByteBuffer.allocate(1024);
            c1 = a.read(buffer1);
            c2 = b.read(buffer2);
            if (c1 != c2)
                return false;

            if (!buffer1.equals(buffer2))
                return false;
        } while (c1 != -1);

        return true;
    }

    private static void readWrite(FileChannel in, FileChannel out, Function<ByteBuffer, ByteBuffer> action) throws IOException {
        int read;
        do {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            read = in.read(buffer);
            out.write(action.apply(buffer));
        } while (read != 0);

    }

    static byte[] encode(byte[] source) {
        Objects.requireNonNull(source);
        if (source.length == 0)
            throw new IllegalArgumentException();

        byte cur = source[0];
        byte count = 1;
        List<Byte> dest = new ArrayList<>();
        for (int i = 1; i < source.length; i++) {
            Byte b = source[i];
            if (cur == b) {
                if (count > 126) {
                    dest.add((byte) 127);
                    dest.add(cur);
                    count = 1;
                } else
                    count++;
            } else {
                dest.add(count);
                dest.add(cur);
                count = 1;
                cur = b;
            }
        }
        dest.add(count);
        dest.add(cur);
        return convert(dest);
    }

    static byte[] decode(byte[] source) {
        Objects.requireNonNull(source);
        int size = source.length;
        if (size == 0 || size % 2 != 0)
            throw new IllegalArgumentException();

        List<Byte> dest = new ArrayList<>();
        for (int i = 0; i < size; i += 2) {
            byte count = source[i];
            byte cur = source[i + 1];

            assert count > 0;

            for (int j = 0; j < count; j++) {
                dest.add(cur);
            }
        }
        return convert(dest);
    }

    private static byte[] convert(List<Byte> dest) {
        byte[] bytes = new byte[dest.size()];
        for (int i = 0; i < dest.size(); i++)
            bytes[i] = dest.get(i);
        return bytes;
    }
}