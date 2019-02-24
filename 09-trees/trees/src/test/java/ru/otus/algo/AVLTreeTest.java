package ru.otus.algo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AVLTreeTest
{
    @Test
    public void addAVLTreeRightRotation()
    {
        Integer[] arr = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

        AVLTree<Integer> tree = AVLTree.of(arr);

        assertTrue(Utils.isBST(tree, Integer::compareTo));

        for (Integer i: arr) {
            assertTrue(tree.contains(i));
        }
    }

    @Test
    public void addAVLTreeLeftRotation()
    {
        AVLTree<Integer> tree = new AVLTree<>(Integer::compareTo);

        for (Integer i: new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}) {
            tree.add(i);
            assertTrue(Utils.isBST(tree, Integer::compareTo));
        }

        for (Integer i: new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}) {
            assertTrue(tree.contains(i));
        }

        List<Integer> sorted = new ArrayList<>();
        tree.traverse(tree, in -> sorted.add(in.getValue()));

        assertTrue(Utils.isSorted(sorted.toArray(new Integer[0])));
    }


    private AVLTree<Integer> tree;
    private final Integer[] integers = {1, 2, 3, 4, 5, 10};

    @BeforeEach
    public void init() {
        tree = AVLTree.of(integers);
    }

    @Test
    public void soloAdd() {
        assertEquals(integers.length, tree.size());
        tree.add(100);
        assertEquals(integers.length + 1, tree.size());
        assertTrue(tree.contains(100));
    }

    @Test
    public void traverse() {
        List<Integer> ints = new ArrayList<>();

        assertEquals(integers.length, tree.size());
        tree.traverse(tree, t -> ints.add(t.getValue()));

        assertEquals(integers.length, tree.size());
        assertArrayEquals(integers, ints.toArray(new Integer[0]));
    }

    @Test
    public void contains() {
        for (Integer i : integers) {
            assertTrue(tree.contains(i));
        }
        assertEquals(integers.length, tree.size());

        assertFalse(tree.contains(11));
    }

    @Test
    public void remove() {
        assertEquals(integers.length, tree.size());
        tree.remove(2);
        assertEquals(integers.length - 1, tree.size());
        assertFalse(tree.contains(2));
        for (Integer i : new Integer[]{1, 3, 4, 5, 10}) {
            assertTrue(tree.contains(i));
        }

        assertFalse(tree.contains(11));
    }

    @Test
    public void deleteRoot() {
        for (Integer i : integers) {
            tree.remove(i);
            assertTrue(Utils.isBST(tree, Integer::compareTo));
        }
    }

    @Test
    public void deleteLeaf() {
        tree.remove(10);
        assertTrue(Utils.isBST(tree, Integer::compareTo));
        assertEquals(integers.length - 1, tree.size());

        tree.remove(5);
        assertTrue(Utils.isBST(tree, Integer::compareTo));
        assertEquals(integers.length - 2, tree.size());

        tree.remove(4);
        assertTrue(Utils.isBST(tree, Integer::compareTo));
        assertEquals(integers.length - 3, tree.size());

        tree.remove(3);
        assertTrue(Utils.isBST(tree, Integer::compareTo));
        assertEquals(integers.length - 4, tree.size());

        tree.remove(2);
        assertTrue(Utils.isBST(tree, Integer::compareTo));
        assertEquals(integers.length - 5, tree.size());

        tree.remove(1);
        assertTrue(Utils.isBST(tree, Integer::compareTo));
        assertEquals(0, tree.size());
    }


    @Nested
    class Deletion {

        @Test
        public void deleteWithOneChild() {
            Integer[] arr = {2, 4, 3};
            tree = AVLTree.of(arr);
            assertTrue(Utils.isBST(tree, Integer::compareTo));
            tree.remove(4);
            assertFalse(tree.contains(4));
            assertTrue(Utils.isBST(tree, Integer::compareTo));
            assertEquals(arr.length - 1, tree.size());
            assertTrue(tree.contains(3));

            arr = new Integer[]{3, 1, 2};
            tree = AVLTree.of(arr);
            assertTrue(Utils.isBST(tree, Integer::compareTo));
            tree.remove(1);
            assertFalse(tree.contains(1));
            assertTrue(Utils.isBST(tree, Integer::compareTo));
            assertEquals(arr.length - 1, tree.size());
            assertTrue(tree.contains(2));

            arr = new Integer[]{3, 2, 1};
            tree = AVLTree.of(arr);
            assertTrue(Utils.isBST(tree, Integer::compareTo));
            tree.remove(2);
            assertFalse(tree.contains(2));
            assertTrue(Utils.isBST(tree, Integer::compareTo));
            assertEquals(arr.length - 1, tree.size());
            assertTrue(tree.contains(1));

            arr = new Integer[]{1, 2, 3};
            tree = AVLTree.of(arr);
            tree.remove(2);
            assertFalse(tree.contains(2));
            assertTrue(Utils.isBST(tree, Integer::compareTo));
            assertEquals(arr.length - 1, tree.size());
            assertTrue(tree.contains(3));
        }

        @Test
        public void deleteWithTwoChildren() {
            Integer[] arr = {5, 2, 8, 1, 4, 6, 10, 3, 7};

            tree = AVLTree.of(arr);
            assertTrue(Utils.isBST(tree, Integer::compareTo));
            assertEquals(arr.length, tree.size());

            tree.remove(5);
            assertFalse(tree.contains(5));
            assertTrue(Utils.isBST(tree, Integer::compareTo));
            assertEquals(arr.length-1, tree.size());
            for (int i=1; i< arr.length; i++) {
                assertTrue(tree.contains(arr[i]));
            }
        }

        @Test
        public void deleteOnEmptyTree() {
            tree = AVLTree.of(new Integer[0]);

            tree.remove(0);
        }
    }
}