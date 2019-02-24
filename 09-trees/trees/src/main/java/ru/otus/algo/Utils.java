package ru.otus.algo;

import java.util.Comparator;
import java.util.Random;

public class Utils {
    private Utils() {}

    /**
     * Recursively checks invariants of binary search tree:
     * 1. {@code node} value greater than left child of this {@code node}
     * 2. {@code node} value less than right child of this {@code node}
     * Empty tree is binary search tree by definition.
     * Comparisons are made by {@code cmp} comparator
     *
     * This method does not allow null {@code node} values.
     * Tree with null values are not considered to be binary search tree.
     *
     * If {@code cmp} is null throws IllegalArgumentException
     *
     * @param node - root element of the binary search tree
     * @param cmp - comparator to compare {@code node} values
     * @param <T> - value type of {@code node}
     * @return - true if binary tree is binary search tree, else false
     */
    public static <T> boolean isBST(BinaryTreeNode<T> node, Comparator<T> cmp) {
        if (cmp == null)
            throw new IllegalArgumentException();

        if (node == null) return true;

        BinaryTreeNode<T> left = node.getLeft();
        if (left != null) {

            T nodeValue = node.getValue();
            T leftValue = left.getValue();

            if (nodeValue == null || leftValue == null || cmp.compare(nodeValue, leftValue) < 0)
                return false;
        }

        BinaryTreeNode<T> right = node.getRight();
        if (right != null) {

            T nodeValue = node.getValue();
            T rightValue = right.getValue();

            if (nodeValue == null || rightValue == null || cmp.compare(nodeValue, rightValue) > 0)
                return false;
        }

        return isBST(left, cmp) && isBST(right, cmp);
    }

    /**
     * Get maximum height of binary tree
     * Returns {@code 0} for empty tree
     * @param node - binary tree
     * @param <T> - value type of {@code node}
     * @return - maximum height
     */
    public static <T> int getMaximumHeight(BinaryTreeNode<T> node) {
        if (node == null)
            return 0;

        int leftHeight = getMaximumHeight(node.getLeft());
        int rightHeight = getMaximumHeight(node.getRight());

        return Math.max(leftHeight, rightHeight)+1;
    }

    public static Integer[] generateRandom(int size, int max) {
        if (size < 0 || max < 1)
            throw new IllegalArgumentException();

        Random random = new Random();
        return random.ints(size, 0, max).boxed().toArray(Integer[]::new);
    }

    public static boolean isSorted(Integer[] list) {
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