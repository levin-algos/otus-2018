package ru.otus.algo;


@SuppressWarnings("SameParameterValue")
public class PArrayQueue<T> implements PQueue<T> {

    PArrayQueue(int maxPriority) {
        queue = new BArray<>(maxPriority);
        for (int i = 0; i < maxPriority; i++) {
            queue.add(i, null);
        }
    }

    public void enqueue(int priority, T el) {

        OList<T> list = getList(priority);

        list.add(el);
        size++;
    }

    private OList<T> getList(int priority) {
        OList<T> list = queue.get(priority);

        if (list == null) {
            list = new OList<>();
            queue.set(priority, list);
        }

        return list;
    }

    public T dequeue() {
        if (size == 0)
            throw new IllegalStateException();

        OList<T> list = getMostPriorityList();
        if (list == null)
            throw new IllegalStateException();

        T element = list.removeFirst();

        size--;
        return element;
    }

    private OList<T> getMostPriorityList() {
        for (int i=queue.size()-1; i >=0; i--) {
            OList<T> list = queue.get(i);
            if (list != null && list.size() != 0) {
                return list;
            }
        }
        return null;
    }

    private final DynamicArray<OList<T>> queue;
    private int size;
}
