package ru.otus.algo;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class BinarySearchTreeTest {

    private BinarySearchTree<Integer> tree;
    private final Integer[] integers = {1, 2, 3, 4, 5, 10};

    @BeforeEach
    void init() {
        tree = BinarySearchTree.of(integers);
    }

    @Test
    void soloAdd() {
        assertEquals(integers.length, tree.size());
        assertEquals(6, Utils.getMaximumHeight(tree.getTree()));
        tree.add(100);
        assertEquals(integers.length + 1, tree.size());
        assertTrue(tree.contains(100));
        assertEquals(7, Utils.getMaximumHeight(tree.getTree()));
    }

    @Test
    void traverse() {
        List<Integer> ints = new ArrayList<>();

        assertEquals(integers.length, tree.size());
        tree.getTree().traverse(tree.getTree(), t -> ints.add(t.getValue()));

        assertEquals(integers.length, tree.size());
        assertArrayEquals(integers, ints.toArray(new Integer[0]));
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
        assertEquals(integers.length, Utils.getMaximumHeight(tree.getTree()));
        tree.remove(2);
        assertEquals(integers.length - 1, Utils.getMaximumHeight(tree.getTree()));
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
            assertTrue(Utils.isBST(tree.getTree(), Integer::compareTo));
            assertEquals(integers.length - k++, Utils.getMaximumHeight(tree.getTree()));
        }
    }

    @Test
    void deleteLeaf() {
        for (int i = integers.length - 1; i >= 0; i--) {
            tree.remove(integers[i]);

            assertTrue(Utils.isBST(tree.getTree(), Integer::compareTo));
            assertEquals(i, this.tree.size());
            assertEquals(i, Utils.getMaximumHeight(tree.getTree()));
        }
    }

    @Nested
    class Deletion {

        @Test
        void deleteWithOneChild() {
            Integer[][] integers = {{2, 4, 3}, {3, 1, 2}, {3, 2, 1}, {1, 2, 3}};

            for (Integer[] arr: integers) {
                tree = BinarySearchTree.of(arr);

                assertEquals(3, Utils.getMaximumHeight(tree.getTree()));
                assertTrue(Utils.isBST(tree.getTree(), Integer::compareTo));
                tree.remove(arr[1]);
                assertFalse(BinarySearchTreeTest.this.tree.contains(arr[1]));
                assertTrue(Utils.isBST(tree.getTree(), Integer::compareTo));
                assertEquals(arr.length - 1, tree.size());
                assertTrue(tree.contains(arr[2]));
            }
        }

        @Test
        void deleteWithTwoChildren() {
            Integer[] arr = {5, 2, 8, 1, 4, 6, 10, 3, 7};

            tree = BinarySearchTree.of(arr);
            assertTrue(Utils.isBST(tree.getTree(), Integer::compareTo));
            assertEquals(arr.length, tree.size());

            tree.remove(5);
            assertFalse(tree.contains(5));
            assertTrue(Utils.isBST(tree.getTree(), Integer::compareTo));
            assertEquals(arr.length - 1, tree.size());
            for (int i = 1; i < arr.length; i++) {
                assertTrue(tree.contains(arr[i]));
            }
        }

        @Test
        void deleteOnEmptyTree() {
            tree = BinarySearchTree.of(new Integer[0]);

            tree.remove(0);
        }
    }
}