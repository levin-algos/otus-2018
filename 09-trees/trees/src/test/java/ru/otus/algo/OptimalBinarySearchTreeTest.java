package ru.otus.algo;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class OptimalBinarySearchTreeTest {

    private BinarySearchTree<Integer> tree = BinarySearchTree.buildOptimal(Pair.combine(new Integer[]{2, 1, 3}, new Integer[]{60, 30, 10}));

    @Test
    void init() {

        assertNotNull(tree);

        assertTrue(tree.contains(1));
        assertTrue(tree.contains(2));
        assertTrue(tree.contains(3));
        tree.saveToFile(Paths.get("1.png"));
    }

    @Test
    void checkImmutability() {
        assertThrows(UnsupportedOperationException.class, () -> {tree.add(0);});
        assertThrows(UnsupportedOperationException.class, () -> {tree.remove(0);});
        assertThrows(UnsupportedOperationException.class, () -> {tree.rotateLeft(tree.root);});
        assertThrows(UnsupportedOperationException.class, () -> {tree.rotateRight(tree.root);});
    }
}