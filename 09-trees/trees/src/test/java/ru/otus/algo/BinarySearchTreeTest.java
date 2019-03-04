package ru.otus.algo;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.otus.algo.common.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;


class BinarySearchTreeTest {

    private static final int MAX = 1000;
    private BinarySearchTree<Integer> tree;
    private final Integer[] integers = {1, 2, 3, 4, 5, 10};
    private static final TreeChecker<Integer> checker = new TreeChecker<>();

    @BeforeAll
    static void initChecker() {
        checker.addCheck(TreeInvariants.isBST(), Integer::compareTo, TreeChecker.Invocation.ROOT);
    }

    @BeforeEach
    void init() {
        tree = BinarySearchTree.of(integers);
    }


    @Test
    void inorderTraversal() {
        List<Integer> list = IntStream.range(0, MAX).boxed().collect(Collectors.toList());
        Collections.shuffle(list);
        BinarySearchTree<Integer> tree = BinarySearchTree.of(list.toArray(new Integer[0]));

        List<Integer> res = new ArrayList<>();

        tree.traverse(Traversal.IN_ORDER, res::add);
        assertEquals(MAX, res.size());
        assertTrue(Utils.isSorted(res.toArray(new Integer[0])));
    }

    @Test
    void soloAdd() {
        assertEquals(integers.length, tree.size());
        assertEquals(6, tree.getMaxHeight());
        tree.add(100);
        assertEquals(integers.length + 1, tree.size());
        assertTrue(tree.contains(100));
        assertEquals(7, tree.getMaxHeight());
    }

        @Test
    void contains() {
        for (Integer i : integers) {
            assertTrue(tree.contains(i));
        }
        assertEquals(integers.length, tree.size());

        assertFalse(tree.contains(11));
    }

    @Test
    void remove() {
        assertEquals(integers.length, tree.size());
        assertEquals(integers.length, tree.getMaxHeight());
        tree.remove(2);
        assertEquals(integers.length - 1, tree.getMaxHeight());
        assertEquals(integers.length - 1, tree.size());
        assertFalse(tree.contains(2));
        for (Integer i : new Integer[]{1, 3, 4, 5, 10}) {
            assertTrue(tree.contains(i));
        }

        assertFalse(tree.contains(11));
    }

    @Test
    void deleteRoot() {
        int k = 1;
        for (Integer i : integers) {
            tree.remove(i);
            assertTrue(checker.check(tree));
            assertEquals(integers.length - k++, tree.getMaxHeight());
        }
    }

    @Test
    void deleteLeaf() {
        for (int i = integers.length - 1; i >= 0; i--) {
            tree.remove(integers[i]);

            assertTrue(checker.check(tree));
            assertEquals(i, this.tree.size());
            assertEquals(i, tree.getMaxHeight());
        }
    }

    @Nested
    class Deletion {

        @Test
        void deleteWithOneChild() {
            Integer[][] integers = {{2, 4, 3}, {3, 1, 2}, {3, 2, 1}, {1, 2, 3}};

            for (Integer[] arr: integers) {
                tree = BinarySearchTree.of(arr);

                assertEquals(3, tree.getMaxHeight());
                assertTrue(checker.check(tree));
                tree.remove(arr[1]);
                assertFalse(BinarySearchTreeTest.this.tree.contains(arr[1]));
                assertTrue(checker.check(tree));
                assertEquals(arr.length - 1, tree.size());
                assertTrue(tree.contains(arr[2]));
            }
        }

        @Test
        void deleteWithTwoChildren() {
            Integer[] arr = {5, 2, 8, 1, 4, 6, 10, 3, 7};

            tree = BinarySearchTree.of(arr);
            assertTrue(checker.check(tree));
            assertEquals(arr.length, tree.size());

            tree.remove(5);
            assertFalse(tree.contains(5));
            assertTrue(checker.check(tree));
            assertEquals(arr.length - 1, tree.size());
            for (int i = 1; i < arr.length; i++) {
                assertTrue(tree.contains(arr[i]));
            }
        }
    }
}