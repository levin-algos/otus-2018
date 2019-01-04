package ru.otus.algo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DynamicArrayTests {

    @ParameterizedTest
    @MethodSource("arrayStringProvider")
    void add(DynamicArray<String> arr) {
        arr.add(0, "test");
        assertEquals(arr.get(0), "test");
    }

    @ParameterizedTest
    @MethodSource("arrayStringProvider")
    void wrongGet(DynamicArray<String> arr) {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {arr.get(0);});
        arr.add(0, "test");
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {arr.get(-1);});
    }

    @ParameterizedTest
    @MethodSource("arrayStringProvider")
    void seriesOfAdd(DynamicArray<String> arr) {
        arr.add(0, "1");
        arr.add(0, "2");
        arr.add(0, "3");
        // should be arr = [3, 2, 1]
        assertEquals("3", arr.get(0));
        assertEquals("2", arr.get(1));
        assertEquals("1", arr.get(2));
    }


    @ParameterizedTest
    @MethodSource("arrayStringProvider")
    void wrongAdd(DynamicArray<String> arr) {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {arr.add(-1, "test");});
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {arr.add(10, "test");});
    }

    static Stream<DynamicArray<String>> arrayStringProvider() {
        return Stream.of(
                new DArray<>(),
                new IArray<>()
        );
    }
}


