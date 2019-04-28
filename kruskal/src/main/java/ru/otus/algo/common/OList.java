package ru.otus.algo.common;

import java.util.Iterator;
import java.util.Objects;

class OList<T> implements Iterable<T> {

    @Override
    public Iterator<T> iterator() {

        return new Iterator<T>() {

            private ListItem<T> cur = _head;
            @Override
            public boolean hasNext() {
                return cur != null;
            }

            @Override
            public T next() {
                ListItem<T> res = cur;
                cur = cur._next;
                return res._item;
            }
        };
    }

    private class ListItem<K> {
        final K _item;
        ListItem<K> _next;
        ListItem<K> _prev;

        ListItem(K item) {
            _item = item;
        }

        K get() {
            return _item;
        }

        void setNext(ListItem<K> item) {
            item._prev = this;
            _next = item;
        }
        ListItem<K> getNext() {
            return _next;
        }

        ListItem<K> getPrev() {
            return _prev;
        }
    }

    private ListItem<T> _head;
    private ListItem<T> _tail;
    private int size;

    public OList() {
        _head = null;
        _tail = null;
    }

    public void addFirst(T item) {
        ListItem<T> li = new ListItem<>(item);
        if (_head == null) {
            _head = li;
            _tail = li;
        }
        else {
            li.setNext(_head);
            _head = li;
        }
        size++;
    }

    public void add(T item) {
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

    /**
     * Removes the first occurrence of {@code item} in list.
     * @param item -
     * @return -
     */
    public boolean remove(T item) {
        Objects.requireNonNull(item);

        ListItem<T> cur = _head;
        while (cur != null) {
            if (cur.get().equals(item)) {
                ListItem<T> next = cur._next;
                if (cur == _head) {
                    _head = next;
                    if (next != null)
                        next._prev = null;
                } else if (cur == _tail) {
                    ListItem<T> prev = cur._prev;
                    _tail = prev;
                    if (prev != null)
                        prev._next = null;
                } else {
                    ListItem<T> prev = cur._prev;

                    prev._next = next;
                    next._prev = prev;
                }
                size--;
                return true;
            }
            cur = cur._next;
        }

        return false;

    }

    public T removeLast() {
        OList<T>.ListItem<T> li = _tail;
        if (li == null)
            throw new NullPointerException();

        if (_head == li)
            _head = null;
        _tail = li.getPrev();
        size--;
        return li.get();
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");

        for (ListItem<T> li = _head; li != null; li = li.getNext()) {
            sb.append(li.get()).append(", ");
        }
        return sb.delete(sb.length()-2, sb.length()).append("]").toString();
    }
}