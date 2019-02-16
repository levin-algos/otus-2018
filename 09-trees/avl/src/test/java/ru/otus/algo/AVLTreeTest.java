package ru.otus.algo;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AVLTreeTest
{

    @Test
    public void addAVLTree()
    {
        AVLTree<Integer> tree = new AVLTree<>();
        tree.setValue(3);

        for (Integer i: new Integer[]{2, 1}) {
            tree.add(i);
        }

        for (Integer i: new Integer[]{3, 2, 1}) {
            assertTrue(""+i, tree.find(i));
        }
    }
}
