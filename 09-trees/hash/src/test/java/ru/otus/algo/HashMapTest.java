package ru.otus.algo;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HashMapTest {


    private static final int MAX = 10_000;

    @ParameterizedTest
    @MethodSource("mapProducer")
    void put(Map<String, String> map) {

        int[] seq = IntStream.range(0, MAX).toArray();

        for (int i : seq) {
            map.put("" + i, "val" + i);
        }

        for (int i : seq)
            assertEquals("val" + i, map.get("" + i));

        assertFalse(map.containsKey("-1"));
    }

    @Test
    void resize() throws IllegalAccessException {
        ChainHashMap<String, String> map = new ChainHashMap<>(new TrivialHash<>());
        int[] range = IntStream.range(0, MAX).toArray();

        for (int r : range) {
            map.put("" + r, "val" + r);
        }

        assertEquals(MAX, map.size());

        for (int r : range) {
            assertTrue(map.containsKey("" + r));
        }

        Field bucketsNum = FieldUtils.getField(ChainHashMap.class, "BUCKETS_NUM", true);

        assertEquals(1 << 14, bucketsNum.get(map));
    }

    @ParameterizedTest
    @MethodSource("mapProducer")
    void remove(Map<String, String> map) {
        List<Integer> list = IntStream.range(0, MAX).boxed().collect(Collectors.toList());

        for (Integer i : list)
            map.put("" + i, "val" + i);

        Collections.shuffle(list);

        while (map.size() > 0) {
            for (Integer i : list) {
                assertEquals("val" + i, map.get("" + i));
            }

            Integer i = list.remove(0);
            map.remove("" + i);
        }
    }

    static Stream<Map<String, String>> mapProducer() {
        return Stream.of(new ChainHashMap<>(new TrivialHash<>()),
                new ChainHashMap<>(key -> 1));
    }
}