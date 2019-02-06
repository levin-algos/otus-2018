package ru.otus.sort;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommonTest {

    @Test
    public void getDigits() {
        assertEquals(1, Common.getNumberOfDigits(5));
        assertEquals(2, Common.getNumberOfDigits(15));
        assertEquals(3, Common.getNumberOfDigits(125));
        assertEquals(4, Common.getNumberOfDigits(1235));
        assertEquals(5, Common.getNumberOfDigits(12345));
        assertEquals(6, Common.getNumberOfDigits(123456));
        assertEquals(7, Common.getNumberOfDigits(1234567));
        assertEquals(8, Common.getNumberOfDigits(12345678));
        assertEquals(9, Common.getNumberOfDigits(123456789));
        assertEquals(10, Common.getNumberOfDigits(1234567890));
    }

}
