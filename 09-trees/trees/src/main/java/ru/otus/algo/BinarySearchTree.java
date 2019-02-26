package ru.otus.algo;

import java.util.Comparator;

/**
 * Implementation of common methods of binary search tree (BST).
 * <p>
 * 1. Add element to tree
 * 2. Remove element from the tree
 * 3. Find element in the tree
 * </p>
 * This implementation cannot keep null values.
 *
 * @param <T> - type of elements in BST
 */
public class BinarySearchTree<T> extends AbstractBinarySearchTree<T> {

    private BinarySearchTree() {
        super();
    }

    private BinarySearchTree(Comparator<? super T> cmp) {
        super(cmp);
    }

    static <T> BinarySearchTree<T> of(T[] arr) {
        BinarySearchTree<T> tree = new BinarySearchTree<>();
        for (T i : arr) {
            tree.add(i);
        }
        return tree;
    }

    public static <T> BinarySearchTree<T> of(T[] arr, Comparator<T> cmp) {
        BinarySearchTree<T> tree = new BinarySearchTree<>(cmp);
        for (T i : arr) {
            tree.add(i);
        }
        return tree;
    }

    @Override
    protected Node<T> createNode(T value, Node<T> parent) {
        return new Node<>(value, parent);
    }
}