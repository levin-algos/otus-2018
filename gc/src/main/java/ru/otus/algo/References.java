package ru.otus.algo;

import java.util.*;

class References {
    private References() {}
    private static Map<Object, Reference> references;

    static Node of(Object ref) {
        return References.of(ref, Node.STATE.COMMON);
    }

    static Node of(Object ref, Node.STATE state) {
        Objects.requireNonNull(ref);

        if (references == null) {
            references = new HashMap<>();
        }

        Reference node = references.get(ref);

        if (node == null) {
            node = new Reference(ref, state);
            references.put(ref, node);
        }
        return node;
    }

    static void remove(Node ref) {
        Objects.requireNonNull(ref);

        if (references == null)
            return;

        references.remove(ref);
    }
}
