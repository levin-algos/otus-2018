package ru.otus.algo;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class AVLTreeTest
{
    @Test
    public void addAVLTreeRightRotation()
    {
        AVLTree<Integer> tree = new AVLTree<>(Integer::compareTo);
        tree.setValue(10);

        for (Integer i: new Integer[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1}) {
            tree.add(i);
            assertTrue(Utils.isBST(tree, Integer::compareTo));
        }

        for (Integer i: new Integer[]{10, 9, 8, 7, 6, 5, 4, 3, 2, 1}) {
            assertTrue(""+i, tree.contains(i));
        }
    }

    @Test
    public void addAVLTreeLeftRotation()
    {
        AVLTree<Integer> tree = new AVLTree<>(Integer::compareTo);
        tree.setValue(10);

        for (Integer i: new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}) {
            tree.add(i);
            assertTrue(Utils.isBST(tree, Integer::compareTo));
        }

        for (Integer i: new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}) {
            assertTrue(""+i, tree.contains(i));
        }
    }

    @Test
    public void test() {
        TreeMap<Integer[], Object> tree = new TreeMap<>();


        Map<Integer[], Object> map = new HashMap<>();
        map.put(new Integer[0], new Object());
        map.put(new Integer[0], new Object());
        map.put(new Integer[0], new Object());
        tree.putAll(map);

    }

}