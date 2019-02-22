package ru.otus.algo;

public class TreeRotations {

    private TreeRotations() {}

    public static <T> void right(BinarySearchTree<T> node) {
        BinarySearchTree<T> left = node.getLeft();
        BinarySearchTree<T> left1 = left.getLeft();

        node.setLeft(left1);
        if (left1 != null)
            left1.setParent(node);

        left.setLeft(left.getRight());
        left.setRight(node.getRight());
        left.setParent(node);

        node.setRight(left);
        TreeRotations.swapValues(node, left);
    }

    public static <T> void left(BinarySearchTree<T> node) {
        BinarySearchTree<T> right = node.getRight();

        node.setRight(right.getRight());
        if (right.getRight() != null)
            right.getRight().setParent(node);

        right.setRight (right.getLeft());
        right.setLeft(node.getLeft());
        if (right.getLeft() != null)
            right.getLeft().setParent(right);

        node.setLeft(right);
        right.setParent(node);

        swapValues(node, right);
    }

    public static <T> void swapValues(BinarySearchTree<T> a, BinarySearchTree<T> b) {
        T tmp = a.getValue();
        a.setValue(b.getValue());
        b.setValue(tmp);
    }
}