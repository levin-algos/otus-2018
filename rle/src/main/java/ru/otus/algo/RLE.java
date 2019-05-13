package ru.otus.algo;

import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of run-length encoding algorithm.
 * Converts byte array or file
 *
 */
class RLE {

    static void encode(Path source, Path dest) {
        Objects.requireNonNull(source);
        Objects.requireNonNull(dest);

        new FileConverter(source, dest).convert(byteBuffer -> ByteBuffer.wrap(encode(byteBuffer.array())));
    }

    static void decode(Path source, Path dest) {
        Objects.requireNonNull(source);
        Objects.requireNonNull(dest);

        new FileConverter(source, dest).convert(byteBuffer -> ByteBuffer.wrap(decode(byteBuffer.array())));
    }

    static byte[] encode(byte[] source) {
        Objects.requireNonNull(source);
        if (source.length == 0)
            throw new IllegalArgumentException();

        byte cur = source[0];
        byte count = 1;
        List<Byte> dest = new ArrayList<>();
        for (int i = 1; i < source.length; i++) {
            byte b = source[i];
            if (cur == b) {
                //TODO: more tests
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