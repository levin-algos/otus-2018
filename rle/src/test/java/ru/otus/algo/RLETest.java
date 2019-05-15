package ru.otus.algo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RLETest {

    @Test
    void encodeDecode() {
        String str = "Simple sample";
        byte[] bytes = str.getBytes();

        byte[] encode = RLE.encode(bytes);
        assertArrayEquals(bytes, RLE.decode(encode));
    }

    @Test
    void repeatingSequence() {
        byte[] in = new byte[128];
        byte[] expected = {127, 0, 1, 0};

        assertArrayEquals(expected, RLE.encode(in));
    }

    @Test
    void emptySequence() {
        byte[] in = {};
        byte[] expected = {};

        assertArrayEquals(expected, RLE.encode(in));
    }

    @ParameterizedTest
    @MethodSource("equalsProvider")
    void compareFiles(Path url1, Path url2) {
        assertTrue(FileConverter.contentEquals(url1, url2));
    }

    @ParameterizedTest
    @MethodSource("copyProvider")
    void decodeEncodeFiles(Path url1, Path url2, Path url3) {
        RLE.encode(url1, url2);
        RLE.decode(url2, url3);
        assertTrue(FileConverter.contentEquals(url1, url3));
    }

    static Stream<? extends Arguments> equalsProvider() throws URISyntaxException {
        return Stream.of(
                Arguments.of(getFromResources("1.txt"),
                        getFromResources("2.txt"),
                Arguments.of(getFromResources("1.jpg")),
                        getFromResources("2.jpg"))
        );
    }

    static Stream<? extends Arguments> copyProvider() throws URISyntaxException {
        return Stream.of(
                Arguments.of(getFromResources("1.txt"),
                        Paths.get("1-encode.txt"),
                        Paths.get("1-decode.txt")),
                Arguments.of(getFromResources("1.jpg"),
                        Paths.get("1-encoded.jpg"),
                        Paths.get("1-decoded.jpg")));
    }

    static Path getFromResources(String fileName) throws URISyntaxException {
        Objects.requireNonNull(fileName);
        final URL resource = ClassLoader.getSystemClassLoader().getResource(fileName);
        if (resource == null)
            throw new IllegalArgumentException();

        return Paths.get(resource.toURI());
    }
}