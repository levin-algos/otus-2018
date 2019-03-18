package ru.otus.algo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HashMapTest {

    @ParameterizedTest
    @MethodSource("mapProducer")
    void put(Map<String, String> map) {
        map.put("1", "val1");
        map.put("2", "val2");
        map.put("3", "val3");

        assertTrue(map.containsKey("1"));
        assertTrue(map.containsKey("2"));
        assertTrue(map.containsKey("3"));
        assertFalse(map.containsKey("4"));
    }

    @ParameterizedTest
    @MethodSource("mapProducer")
    void remove(Map<String, String> map) {
        map.put("1", "val1");
        map.put("2", "val2");
        map.put("3", "val3");

        map.remove("1");
        assertFalse(map.containsKey("1"));
        assertTrue(map.containsKey("2"));
        assertTrue(map.containsKey("3"));

        map.remove("2");
        assertFalse(map.containsKey("1"));
        assertFalse(map.containsKey("2"));
        assertTrue(map.containsKey("3"));

        map.remove("3");
        assertFalse(map.containsKey("1"));
        assertFalse(map.containsKey("2"));
        assertFalse(map.containsKey("3"));
    }

    @ParameterizedTest
    @MethodSource("mapProducer")
    void containsKey() {
    }

    static Stream<Map<String, String>> mapProducer() {
        return Stream.of(new ChainHashMap<>(new TrivialHash<>(16), 16));
    }
}