package ru.otus.sort;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IntBufferArrayTest {


    @Test
    public void addGetSingleBuffer() {
        ByteBufferArray buf = new ByteBufferArray(10, SizeUnits.INTEGER);

        assertEquals(0, buf.position());
        buf.add(new int[]{1, 2, 3, 4, 5});

        assertEquals(5, buf.position());
        buf.rewind();

        assertEquals(0, buf.position());
        int[] expected = new int[] {1, 2, 3, 4, 5};

        for (int i: expected) {
            assertEquals(i, buf.getInt());
        }
    }

//    @Test
    public void addGetSeveralBuffers() {
        long capacity = Integer.MAX_VALUE/2;
        ByteBufferArray buf = new ByteBufferArray(capacity, SizeUnits.INTEGER);

        assertEquals(0, buf.position());
        for (long i = 0; i < capacity; i++) {
            buf.add((int)(i & Integer.MAX_VALUE));
        }
        assertEquals(capacity, buf.position());
        buf.rewind();
        assertEquals(0, buf.position());

        for (long i = 0; i < capacity; i++) {
            assertEquals(""+i, (int)(i & Integer.MAX_VALUE), buf.getInt());
        }
    }

}
// 2147483636
// 536870908