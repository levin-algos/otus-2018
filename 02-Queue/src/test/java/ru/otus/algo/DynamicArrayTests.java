package ru.otus.algo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DynamicArrayTests {

    @ParameterizedTest
    @MethodSource("arrayStringProvider")
    void add(DynamicArray<String> arr) {
        assertArrayEquals(new String[]{"1", "2", "3"}, arr.toArray(new String[0]));
    }

    @ParameterizedTest
    @MethodSource("emptyArrayStringProvider")
    void wrongGet(DynamicArray<String> arr) {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> arr.get(0));
        arr.add(0, "test");
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> arr.get(-1));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> arr.get(1));
    }

    @ParameterizedTest
    @MethodSource("arrayStringProvider")
    void remove(DynamicArray<String> arr) {
        String remove = arr.remove(0);
        assertEquals(2, arr.size());
        assertEquals("1", remove);
        assertArrayEquals(new String[]{"2", "3"}, arr.toArray(new String[0]));
        assertEquals(2, arr.size());
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> arr.get(2));
    }

    @ParameterizedTest
    @MethodSource("emptyArrayStringProvider")
    void removeWrong(DynamicArray<String> arr) {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> arr.remove(0));
        arr.add(0, "1");
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> arr.remove(1));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> arr.remove(-1));
    }

    @ParameterizedTest
    @MethodSource("arrayStringProvider")
    void set(DynamicArray<String> arr) {
        arr.set(1, "set");

        assertArrayEquals(new String[]{"1", "set", "3"}, arr.toArray(new String[0]));
    }

    @ParameterizedTest
    @MethodSource("arrayStringProvider")
    void seriesOfAdd(DynamicArray<String> arr) {
        arr.add(0, "1");
        arr.add(0, "2");
        arr.add(0, "3");

        assertArrayEquals(new String[]{"3", "2", "1", "1", "2", "3"}, arr.toArray(new String[0]));
    }

    @ParameterizedTest
    @MethodSource("emptyArrayStringProvider")
    void wrongAdd(DynamicArray<String> arr) {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> arr.add(-1, "test"));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> arr.add(10, "test"));
    }

    static Stream<DynamicArray<String>> emptyArrayStringProvider() {
        return Stream.of(
                new DArray<>(),
                new BArray<>(),
                new IArray<>()
        );
    }

    static Stream<DynamicArray<String>> arrayStringProvider() {
        return Stream.of(
                new DArray<>("1", "2", "3"),
                new BArray<>("1", "2", "3"),
                new IArray<>("1", "2", "3")
        );
    }
}