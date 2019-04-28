package ru.otus.algo.common;

import java.util.Objects;

public class UnionFind<T> {

    private final Map<T, SetElement<T>> set = new HashMap<>(Object::hashCode);

    public void makeSet(T i) {
        makeElementSet(i);
    }

    private SetElement<T> makeElementSet(T i) {
        if (set.containsKey(i)) {
            return set.get(i);
        }

        SetElement<T> element = new SetElement<>(i);
        set.put(i, element);
        return element;
    }

    public void union(T x, T y) {
        SetElement<T> xRoot, yRoot;
        xRoot = findElement(makeElementSet(x).element);
        yRoot = findElement(makeElementSet(y).element);

        if (xRoot == null || yRoot == null)
            return;

        if (xRoot.equals(yRoot)) return;

        if (xRoot.rank < yRoot.rank) {
            SetElement<T> tmp = xRoot;
            xRoot = yRoot;
            yRoot = tmp;
        }

        yRoot.parent = xRoot;
        if (xRoot.rank == yRoot.rank)
            xRoot.rank++;
    }

    public T find(T i) {
        SetElement<T> elem = findElement(i);
        return elem != null ? elem.element : null;
    }

    private SetElement<T> findElement(T i) {
        SetElement<T> elem = set.get(i);
        if (elem == null)
            return null;

        while (elem.parent != elem) {
            elem = elem.parent;
        }
        return elem;
    }

    private class SetElement<R> {
        private final R element;
        private SetElement<R> parent;
        private int rank;


        SetElement(R element) {
            this.element = element;
            this.parent = this;
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SetElement<?> that = (SetElement<?>) o;
            return element.equals(that.element);
        }

        @Override
        public int hashCode() {
            return Objects.hash(element);
        }
    }
}
