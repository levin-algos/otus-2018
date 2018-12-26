package ru.otus.algo;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class PQueueTest {
    @Test
    public void queueTest() throws Exception {
        PQueue<String> queue = PArrayQueue.of();
        queue.enqueue(1, "test");
        assertEquals(queue.dequeue(), "test");
    }
}