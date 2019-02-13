package ru.otus.sort;

import java.nio.ByteBuffer;

public class ByteBufferArray {
    private final long capacity;
    private long position;
    private final int bufferCapacity = Integer.MAX_VALUE - 11;
    private ByteBuffer[] buffer;
    private int currentBag;
    private SizeUnits units;

    public ByteBufferArray(long capacity, SizeUnits units) {
        if (capacity <= 0)
            throw new IllegalArgumentException();

        this.units = units;
        this.capacity = capacity * units.getSize();

        if (this.capacity > bufferCapacity) {
            int bags = (int) (this.capacity / bufferCapacity) + 1;

            buffer = new ByteBuffer[bags];
            for (int bag = 0; bag < bags; bag++) {
                if (buffer[bag] == null) {
                    int cap = calcBufferSize(bag);
                    buffer[bag] = ByteBuffer.allocate(cap);
                }
            }
        } else
            buffer = new ByteBuffer[]{ByteBuffer.allocate((int)this.capacity)};
    }


    public void add(int[] ints) {
        for (int i : ints)
            add(i);
    }

    public void add(int i) {
        if (position >= capacity)
            throw new IllegalArgumentException();

        int bag = getBag();
        buffer[bag].putInt(i);
        position += units.getSize();
    }

    private int calcBufferSize(int bag) {
        int size;
        if (capacity > bufferCapacity) {
            long s = capacity - bag * bufferCapacity;
            size = s > bufferCapacity ? bufferCapacity : (int) s;
        } else {
            size = (int) capacity;
        }

        return size;
    }

    private int getBag() {
        return (int) ((position) / bufferCapacity);
    }

    public void rewind() {
        currentBag = 0;
        for (ByteBuffer b : buffer) {
            if (b != null)
                b.rewind();
        }
        position = 0;
    }

    public int getInt() {
        if (buffer[currentBag].remaining() == 0) {
            if (buffer.length <= currentBag + 1)
                throw new IllegalStateException();
            else currentBag++;
        }
        position+= units.getSize();
        return buffer[currentBag].getInt();
    }

    public long position() {
        return position / units.getSize();
    }

    public ByteBuffer[] toArray() {
        return buffer;
    }

    public void flip() {
        for (ByteBuffer b: buffer) {
            b.flip();
        }
    }
}
