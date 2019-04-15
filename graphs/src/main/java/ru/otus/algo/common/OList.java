package ru.otus.algo.common;

public class OList<T> {

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

    public T removeLast() {
        OList<T>.ListItem<T> li = _tail;
        if (li == null)
            throw new NullPointerException();

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
            sb.append(li.get()).append(",");
        }
        return sb.deleteCharAt(sb.length()-1).append("]").toString();
    }
}