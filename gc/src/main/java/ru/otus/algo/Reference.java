package ru.otus.algo;

import java.util.*;

public class Reference implements Node {
    private static int current;
    private final int id = current++;

    private final Object ref;
    private final STATE state;

    Reference(Object ref, STATE state) {
        this.ref = ref; this.state = state;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public STATE getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reference reference = (Reference) o;
        return ref.equals(reference.ref);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ref);
    }

    @Override
    public String toString() {
        return "Reference{" +
                "id=" + id +
                ", ref=" + ref +
                ", state=" + state +
                '}';
    }
}