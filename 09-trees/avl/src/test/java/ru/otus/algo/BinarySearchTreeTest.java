package ru.otus.algo;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BinarySearchTreeTest {

    private BinarySearchTree<Integer> tree;
    private final Integer[] integers = {1, 2, 3, 4, 5, 10};

    @Before
    public void init() {
        tree = BinarySearchTree.of(integers);
    }

    @Test
    public void soloAdd() {
        tree.add(100);

        assertTrue(tree.contains(100));
    }

    @Test
    public void add() {
         List<Integer> ints = new ArrayList<>();

        tree.traverse(TraversalOrder.INORDER, t -> ints.add(t.getValue()));

        assertArrayEquals(integers, ints.toArray(new Integer[0]));
    }

    @Test
    public void contains() {
        for (Integer i : integers) {
            assertTrue(tree.contains(i));
        }

        assertFalse(tree.contains(11));
    }

    @Test
    public void remove() {
        tree.remove(2);
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
    public void deleteList() {
        tree.remove(10);
        assertTrue(Utils.isBST(tree, Integer::compareTo));
        assertEquals(integers.length-2, tree.size());
    }
}