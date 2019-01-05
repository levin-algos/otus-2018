package ru.otus.algo;


class OList<T> {

    private ListItem<T> _head;

    private ListItem<T> _tail;
    OList() {
        _head = null;
        _tail = null;
    }

    ListItem<T> head() {
        return _head;
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
    }

    @SuppressWarnings("hiding")
    private class ListItem<K> {
        final K item;
        ListItem<K> next;

        ListItem(K item) {
            this.item = item;
            next = null;
        }

        K get() {
            return item;
        }

        void setNext(ListItem<K> item) {
            next = item;
        }
        ListItem<K> getNext() {
            return next;
        }
    }
}

