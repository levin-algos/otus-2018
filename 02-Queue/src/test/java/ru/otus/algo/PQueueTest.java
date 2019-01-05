package ru.otus.algo;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
/*
    Реализация очереди с приоритетом
    Написать реализацию PQueue - очередь с приоритетом.
    Варианты реализации - список списков, массив списков

    Методы к реализации:
    enqueue(int priority, T item) - поместить элемент в очередь
    T dequeue() - выбрать элемент из очереди
 */

class PQueueTest {

    @Test
    void queueTest() throws Exception {
        PQueue<String> queue = PArrayQueue.of();
        queue.enqueue(1, "test");
        assertEquals(queue.dequeue(), "test");
    }
}