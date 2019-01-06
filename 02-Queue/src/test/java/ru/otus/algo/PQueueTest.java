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
        queue.enqueue(0, "test01");
        queue.enqueue(2, "test21");
        queue.enqueue(0, "test02");
        queue.enqueue(2, "test22");
        queue.enqueue(1, "test1");
        queue.enqueue(0, "test03");
        queue.enqueue(2, "test23");

        assertEquals(queue.dequeue(), "test21");
        assertEquals(queue.dequeue(), "test22");
        assertEquals(queue.dequeue(), "test23");
        assertEquals(queue.dequeue(), "test1");
        assertEquals(queue.dequeue(), "test01");
        assertEquals(queue.dequeue(), "test02");
        assertEquals(queue.dequeue(), "test03");

    }
}