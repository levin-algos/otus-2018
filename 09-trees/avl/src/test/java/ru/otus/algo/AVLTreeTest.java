package ru.otus.algo;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AVLTreeTest
{

    @Test
    public void addAVLTreeRightRotation()
    {
        AVLTree<Integer> tree = new AVLTree<>();
        tree.setValue(10);

        for (Integer i: new Integer[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1}) {
            tree.add(i);
        }

        for (Integer i: new Integer[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1}) {
            assertTrue(""+i, tree.find(i));
        }
    }

    @Test
    public void addAVLTreeLeftRotation()
    {
        AVLTree<Integer> tree = new AVLTree<>();
        tree.setValue(10);

        for (Integer i: new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}) {
            tree.add(i);
        }

        for (Integer i: new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}) {
            assertTrue(""+i, tree.find(i));
        }
    }
}