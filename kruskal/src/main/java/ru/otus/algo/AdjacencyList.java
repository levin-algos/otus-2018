package ru.otus.algo;


import ru.otus.algo.common.HashMap;
import ru.otus.algo.common.HashSet;
import ru.otus.algo.common.Map;
import ru.otus.algo.common.Set;

import java.util.Iterator;

public class AdjacencyList<T> implements Adjacency<T> {
    private final Map<T, Set<T>> edges;

    AdjacencyList() {
        edges = new HashMap<>(key -> key.hashCode());
    }

    @Override
    public void connect(T a, T b) {
        Set<T> val = edges.get(a);
        if (val == null)
            val = new HashSet<>();

        val.add(b);
        edges.put(a, val);
        connect(b, a);
    }

    @Override
    public boolean isConnected(T a, T b) {
        if (a == null || b == null)
            throw new IllegalArgumentException();
        if (a.equals(b))
            return true;

        Set<T> set = edges.get(a);
        return set != null && set.contains(b) || isConnected(b, a);
    }

    @Override
    public void disconnect(T a, T b) {
        Set<T> val = edges.get(a);
        if (val == null)
            return;

        val.remove(b);
        disconnect(b, a);
    }

    @Override
    public Iterable<T> getConnected(T a) {
        return new Iterable<T>() {

            private Iterator<T> it;

            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    @Override
                    public boolean hasNext() {
                        if (it == null) {
                            Set<T> ts = edges.get(a);
                            if (ts == null)
                                return false;
                            it = ts.iterator();
                        }
                        return it.hasNext();
                    }

                    @Override
                    public T next() {
                        return it.next();
                    }
                };
            }
        };
    }
}
