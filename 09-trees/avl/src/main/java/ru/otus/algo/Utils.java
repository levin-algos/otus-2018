package ru.otus.algo;

import java.util.Comparator;
import java.util.Random;

public class Utils {

    public static <T> boolean isBST(BinarySearchTree<T> node, Comparator<T> cmp) {
        if (cmp == null)
            throw new IllegalArgumentException();

        if (node == null) return true;

        if (node.getLeft() != null && cmp.compare(node.getValue(), node.getLeft().getValue()) <= 0)
            return false;

        if (node.getRight() != null && cmp.compare(node.getValue(), node.getRight().getValue()) >= 0)
            return false;

        return isBST(node.getLeft(), cmp) && isBST(node.getRight(), cmp);
    }

    public static Integer[] generateRandom(int size, int max) {
        if (size < 0 || max < 1)
            throw new IllegalArgumentException();

        Random random = new Random();
        return random.ints(size, 0, max).boxed().toArray(Integer[]::new);
    }

    public static boolean isSorted(int[] list) {
        if (list == null)
            throw new IllegalArgumentException();

        int current = Integer.MIN_VALUE;
        for (int el : list) {
            if (current > el)
                return false;
            current = el;
        }
        return true;
    }
}