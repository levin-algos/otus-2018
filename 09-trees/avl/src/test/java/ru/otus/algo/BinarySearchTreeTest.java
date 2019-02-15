package ru.otus.algo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BinarySearchTreeTest {

    @Test
    public void add() {

        BinarySearchTree<Integer> tree = new BinarySearchTree<>(10, null);

        for (Integer i: new Integer[]{3, 5, 2, 1, 4}) {
            tree.add(i);
        }

        List<Integer> ints = new ArrayList<>();

        tree.traverse(TraversalOrder.INORDER, t -> ints.add(t.getValue()));

        assertArrayEquals(new Integer[] {1, 2, 3, 4, 5, 10}, ints.toArray(new Integer[0]));
    }

    @Test
    public void find() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>(10, null);

        for (Integer i: new Integer[]{3, 5, 2, 1, 4}) {
            tree.add(i);
        }

        for (Integer i: new Integer[] {1, 2, 3, 4, 5, 10}) {
            assertTrue(tree.find(i));
        }

        assertFalse(tree.find(11));
    }

    @Test
    public void remove() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>(10, null);

        for (Integer i: new Integer[]{3, 5, 2, 1, 4}) {
            tree.add(i);
        }

        tree.remove(2);
        assertFalse(tree.find(2));
        for (Integer i: new Integer[] {1, 3, 4, 5, 10}) {
            assertTrue(tree.find(i));
        }

        assertFalse(tree.find(11));
    }

    @Test
    public void deleteRoot() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>(10, null);

        for (Integer i: new Integer[]{3, 5, 2, 1, 4}) {
            tree.add(i);
        }

        tree.remove(3);
        assertFalse(tree.find(3));

        tree.remove(10);
        assertFalse(tree.find(10));
        for (Integer i: new Integer[] {1, 2, 4, 5}) {
            assertTrue(tree.find(i));
        }
    }
}