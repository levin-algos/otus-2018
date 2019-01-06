package ru.otus.algo;

public class PArrayQueue<T> implements PQueue<T> {

    public static <T> PQueue<T> of() {
        return new PArrayQueue<>();
    }

    private PArrayQueue() {
        queue = new BArray<>();
    }

    public void enqueue(int priority, T test) {
        QueueElement<T> element = new QueueElement<>(priority, test);
        int bin = findBin(element);

        OList<QueueElement<T>> list = queue.get(bin);

        if (list == null) {
            list = new OList<>();
        }
        list.add(element);
        queue.set(bin, list);
    }

    private int findBin(QueueElement<T> element) {
        int size = queue.size();
        for (int i = 0; i < size; i++) {
            OList<QueueElement<T>> list = queue.get(i);
            int res = list.head().get().compareTo(element);
            if (res<0) {
                continue;
            }
            if (res == 0)
                return i;

            queue.add(i, new OList<>());
            return i;
        }
        queue.add(size, new OList<>());
        return size;
    }

    public T dequeue() {
        int size = queue.size()-1;
        OList<QueueElement<T>> list = queue.get(size);
        T element = list.removeFirst().element;
        if (list.head() == null)
            queue.remove(size);
        return element;
    }

    private DynamicArray<OList<QueueElement<T>>> queue;
    private class QueueElement<T> implements Comparable<QueueElement<T>> {
        private final int priority;
        private final T element;

        QueueElement(int priority, T element) {
            this.priority = priority;
            this.element = element;
        }

        @Override
        public int compareTo(QueueElement<T> o) {
            return Integer.compare(priority, o.priority);
        }
    }
}
