package ru.otus.algo;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class OptimalBinarySearchTreeTest {

    @ParameterizedTest
    @MethodSource("treeProvider")
    void init(BinarySearchTree<Integer> tree) {

        assertNotNull(tree);

        assertTrue(tree.contains(1));
        assertTrue(tree.contains(2));
        assertTrue(tree.contains(3));
    }

    @ParameterizedTest
    @MethodSource("treeProvider")
    void checkImmutability(BinarySearchTree<Integer> tree) {
        assertThrows(UnsupportedOperationException.class, () -> {tree.add(0);});
        assertThrows(UnsupportedOperationException.class, () -> {tree.remove(0);});
        assertThrows(UnsupportedOperationException.class, () -> {tree.rotateLeft(tree.root);});
        assertThrows(UnsupportedOperationException.class, () -> {tree.rotateRight(tree.root);});
    }

    static Stream<BinarySearchTree<Integer>> treeProvider() {
        return Stream.of(BinarySearchTree.buildOptimal(Pair.combine(new Integer[]{2, 1, 3}, new Integer[]{60, 30, 10})),
                BinarySearchTree.buildMehlhorn(Pair.combine(new Integer[]{2, 1, 3}, new Integer[]{60, 30, 10})));
    }
}