package ru.otus.algo;

class OList<T> {

     @SuppressWarnings("hiding")
    class ListItem<K> {
        final K _item;
        ListItem<K> _next;

        ListItem(K item) {
            _item = item;
            _next = null;
        }

        K get() {
            return _item;
        }

        void setNext(ListItem<K> item) {
            _next = item;
        }
        ListItem<K> getNext() {
            return _next;
        }
    }

    private ListItem<T> _head;
    private ListItem<T> _tail;
    private int size;

    OList() {
        _head = null;
        _tail = null;
    }

    void add(T item) {
        ListItem<T> li = new ListItem<>(item);
        if (_head == null) {
            _head = li;
            _tail = li;
        }
        else {
            _tail.setNext(li);
            _tail = li;
        }
        size++;
    }

    T removeFirst() {
        OList<T>.ListItem<T> li = _head;
        if (li == null)
            throw new NullPointerException();

        _head = li.getNext();
        size--;
        return li.get();
    }

    int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");

        for (ListItem<T> li = _head; li != null; li = li.getNext()) {
            sb.append(li.get()).append(",");
        }
        return sb.deleteCharAt(sb.length()-1).append("]").toString();
    }
}