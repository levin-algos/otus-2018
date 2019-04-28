package ru.otus.algo.common;

import java.util.Iterator;

public class HashSet<T> implements Set<T> {

    private final Map<T, T> set = new HashMap<>(key -> hashCode());

    @Override
    public void add(T el) {
        set.put(el, el);
    }

    @Override
    public boolean contains(T el) {
        return set.containsKey(el);
    }

    @Override
    public void remove(T b) {
        set.remove(b);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private final Iterator<Map.Entry<T, T>> it = set.entrySet().iterator();
            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public T next() {
                return it.next().getKey();
            }
        };
    }
}