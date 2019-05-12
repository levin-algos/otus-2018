package ru.otus.algo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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

    @ParameterizedTest
    @MethodSource("equalsProvider")
    void compareFiles(URL url1, URL url2) throws IOException, URISyntaxException {
        FileChannel ch1 = FileChannel.open(Paths.get(url1.toURI()), StandardOpenOption.READ);
        FileChannel ch2 = FileChannel.open(Paths.get(url2.toURI()), StandardOpenOption.READ);
        assertTrue(RLE.contentEquals(ch1, ch2));
        ch1.close();
        ch2.close();
    }

    @ParameterizedTest
    @MethodSource("copyProvider")
    void decodeEncodeFiles(Path url1, Path url2, Path url3) throws IOException {
        SeekableByteChannel seekableByteChannel = Files.newByteChannel(url1, StandardOpenOption.READ);
        FileChannel ch1 = FileChannel.open(url1, StandardOpenOption.READ);
        FileChannel ch2 = FileChannel.open(url2, StandardOpenOption.CREATE_NEW);
        FileChannel ch3 = FileChannel.open(url3, StandardOpenOption.CREATE_NEW);

        RLE.encode(ch1, ch2);
        RLE.decode(ch2, ch3);
        assertTrue(RLE.contentEquals(ch1, ch3));
        ch1.close();
        ch2.close();
        ch3.close();
    }

    static Stream<? extends Arguments> equalsProvider() {
        return Stream.of(
                Arguments.of(ClassLoader.getSystemClassLoader().getResource("1.txt"),
                        ClassLoader.getSystemClassLoader().getResource("2.txt")),
                Arguments.of(ClassLoader.getSystemClassLoader().getResource("1.jpg"),
                        ClassLoader.getSystemClassLoader().getResource("2.jpg"))
        );
    }

    static Stream<? extends Arguments> copyProvider() throws URISyntaxException {
        return Stream.of(
                Arguments.of(Paths.get(ClassLoader.getSystemClassLoader().getResource("1.txt").toURI()),
                        Paths.get(ClassLoader.getSystemClassLoader().getResource("1-encoded.txt").toURI()),
                        Paths.get(ClassLoader.getSystemClassLoader().getResource("1-decoded.txt").toURI())
//                Arguments.of(ClassLoader.getSystemClassLoader().getResource("1.jpg"),
//                        ClassLoader.getSystemClassLoader().getResource("1-encoded.jpg"),
//                        ClassLoader.getSystemClassLoader().getResource("1-decoded.jpg"))
        ));
    }
}