package ru.otus.algo;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class IArrayTest {

    @Test
    @SuppressWarnings("unchecked")
    void increaseBinNumberTest() throws NoSuchFieldException, IllegalAccessException {
        IArray<String> arr = new IArray<>(10);
        // should split fist bin when adds 8th element

        Field arraySize = IArray.class.getDeclaredField("_arr");
        BArray<BArray<String>> _arr = (BArray<BArray<String>>)FieldUtils.readField(arraySize, arr, true);
        for (int i = 0; i < 7; i++) {
            arr.add(i, ""+i);
            assertEquals(1, _arr.size(), "at index: " +i);
        }
        arr.add(7, "7");
        // [0, 1, 2, 3, 4, 5, 6, 7] -> [0, 1, 2, 3] [4, 5, 6, 7]
        assertEquals(2, _arr.size());
        assertArrayEquals(new String[]{"0", "1", "2", "3", "4"}, _arr.get(0).toArray(new String[0]));
        assertArrayEquals(new String[]{"5", "6", "7"}, _arr.get(1).toArray(new String[0]));
    }

    @Test
    @SuppressWarnings("unchecked")
    void decreaseBinNumberTest() throws NoSuchFieldException, IllegalAccessException {
        IArray<String> arr = new IArray<>(10);
        // should split fist bin when adds 8th element

        Field arraySize = IArray.class.getDeclaredField("_arr");
        BArray<BArray<String>> _arr = (BArray<BArray<String>>)FieldUtils.readField(arraySize, arr, true);
        for (int i = 0; i < 8; i++) {
            arr.add(i, ""+i);
        }
        // [0, 1, 2, 3, 4, 5, 6, 7] -> [0, 1, 2, 3] [4, 5, 6, 7]
        assertEquals(2, _arr.size());
        assertArrayEquals(new String[]{"0", "1", "2", "3", "4"}, _arr.get(0).toArray(new String[0]));
        assertArrayEquals(new String[]{"5", "6", "7"}, _arr.get(1).toArray(new String[0]));

        assertEquals("5", arr.remove(5));
        assertEquals("6", arr.remove(5));
        assertEquals("7", arr.remove(5));
        assertEquals(1, _arr.size());
        assertArrayEquals(new String[]{"0", "1", "2", "3", "4"}, _arr.get(0).toArray(new String[0]));
    }

}
