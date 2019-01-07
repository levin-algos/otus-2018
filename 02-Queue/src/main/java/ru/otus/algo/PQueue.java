package ru.otus.algo;

interface PQueue<T> {
    /**
     * Add {@code el} to the priority queue
     * @param priority -
     * @param el - element to enqueue
     */
    void enqueue(int priority, T el);

    /**
     * Extract element from queue with the most priority, and deletes it from the queue.
     * @return -
     */
    T dequeue();
}
