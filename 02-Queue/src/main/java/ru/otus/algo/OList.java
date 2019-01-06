package ru.otus.algo;

class OList<T> {

    @SuppressWarnings("hiding")
    class ListItem<T> {
        T _item;
        ListItem<T> _next;

        ListItem(T item) {
            _item = item;
            _next = null;
        }

        T get() {
            return _item;
        }

        void setNext(ListItem<T> item) {
            _next = item;
        }
        ListItem<T> getNext() {
            return _next;
        }
    }

    ListItem<T> _head;
    ListItem<T> _tail;

    OList() {
        _head = null;
        _tail = null;
    }

    ListItem<T> head() {
        return _head;
    }

    void add(T item) {
        ListItem<T> li = new ListItem<T>(item);
        if (_head == null) {
            _head = li;
            _tail = li;
        }
        else {
            _tail.setNext(li);
            _tail = li;
        }
    }

    T removeFirst() {
        OList<T>.ListItem<T> li = _head;
        if (li == null)
            throw new NullPointerException();

        _head = li.getNext();
        return li.get();
    }
}