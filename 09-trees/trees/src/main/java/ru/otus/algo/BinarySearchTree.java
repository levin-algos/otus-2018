package ru.otus.algo;

import java.util.Comparator;
import java.util.List;

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

    private BinarySearchTree(BinarySearchTree<T> tree) {
        super(tree);
    }

    private BinarySearchTree(Comparator<? super T> cmp) {
        super(cmp);
    }

    static <T> BinarySearchTree<T> of() {
        return new BinarySearchTree<>((Comparator<? super T>)null);
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


    /**
     * Implementation of binary search tree provides nearly the smallest possible search time
     * based on paper "Nearly optimal binary search trees" by Kurt Mehlhorn.
     *
     * This class solves the static optimality problem.
     * E.g. it cannot be modified since it has been constructed.
     *
     *
     * Build approximately optimal search tree.
     * Algorithm:
     * <ul>
     * <li>Place most frequently occurred element at the root of the tree.</li>
     * <li>Proceed similarly on subtrees</li>
     * </ul>
     *
     * @return - constructed optimal tree
     */
    public static <T, K extends Comparable<? super K>> BinarySearchTree<T> buildOptimal(List<Pair<T, K>> pairs) {
        pairs.sort(Comparator.comparing(Pair::getRight, Comparator.reverseOrder()));
        BinarySearchTree<T> tree = new BinarySearchTree<>((Comparator<? super T>)null);

        for (Pair<T, K> e : pairs) {
            tree.add(e.getLeft());
        }

        return new ImmutableBinarySearchTree<>(tree);
    }

    /**
     * Build nearly optimal search tree.
     *
     * @param pairs
     * @param <T>
     * @param <K>
     * @return
     */
    public static <T extends Comparable<? super T>, K extends Comparable<? super K>> BinarySearchTree<T> buildMehlhorn(List<Pair<T, K>> pairs) {
        pairs.sort(Comparator.comparing(Pair::getLeft));

//        return null;
        return new ImmutableBinarySearchTree<>(buildTree(pairs, 0, pairs.size()));
    }

    private static <T extends Comparable<? super T>, K extends Comparable<? super K>> BinarySearchTree<T> buildTree(List<Pair<T, K>> pairs, int from, int to) {
        if (to > from) {
            int pivot = getPivot(pairs, from, to);

        }
        return null;
    }

    private static <T extends Comparable<? super T>, K extends Comparable<? super K>> int getPivot(List<Pair<T, K>> pairs, int from, int to) {
        return 0;
    }

    private static class ImmutableBinarySearchTree<T> extends BinarySearchTree<T> {

        public ImmutableBinarySearchTree(BinarySearchTree<T> tree) {
            super(tree);
        }

        @Override
        public void add(T element) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove(T element) {
            throw new UnsupportedOperationException();
        }

        @Override
        void rotateRight(Node<T> node) {
            throw new UnsupportedOperationException();
        }

        @Override
        void rotateLeft(Node<T> node) {
            throw new UnsupportedOperationException();
        }
    }
}