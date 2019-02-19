package ru.otus.algo;

public class TreeRotations {

    private TreeRotations() {}

    public static <T extends Comparable<T>> void right(BinarySearchTree<T> node) {
        BinarySearchTree<T> left = node.left;
        BinarySearchTree<T> left1 = left.left;

        node.left = left1;
        if (left1 != null)
            left1.parent = node;

        left.left = left.right;
        left.right = node.right;
        left.parent = node;

        node.right = left;
        TreeRotations.swapValues(node, left);
    }

    public static <T extends Comparable<T>> void left(BinarySearchTree<T> node) {
        BinarySearchTree<T> right = node.right;

        node.right = right.right;
        if (right.right != null)
            right.right.parent = node;

        right.right = right.left;
        right.left = node.left;
        if (right.left != null)
            right.left.parent = right;

        node.left = right;
        right.parent = node;

        swapValues(node, right);
    }

    public static <T extends Comparable<T>> void swapValues(BinarySearchTree<T> a, BinarySearchTree<T> b) {
        T tmp = a.value;
        a.value = b.value;
        b.value = tmp;
    }
}