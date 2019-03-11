package ru.otus.algo;

import java.util.Comparator;
import java.util.List;

/**
 * Implementation of binary search tree provides the smallest possible search time.
 * This class solves the static optimality problem.
 * E.g. it cannot be modified since it has been constructed.
 * @param <T> - tree's element type
 */
public class OptimalBinarySearchTree<T> extends AbstractBinarySearchTree<T> {

    @Override
    protected Node<T> createNode(T value, Node<T> parent) {
        return new Node<>(value, parent);
    }

    private OptimalBinarySearchTree(Comparator<? super T> cmp) {
        super(cmp);
    }

    /**
     * Build approximately optimal search tree.
     * Algorithm:
     * <ul>
     *     <li></li>
     * </ul>
     * @return - constructed optimal tree
     */
    public static <T, K extends Comparable<? super K>> OptimalBinarySearchTree<T> build(List<Pair<T, K>> pairs) {
        pairs.sort(Comparator.comparing(Pair::getRight, Comparator.reverseOrder()));
        OptimalBinarySearchTree<T> tree = new OptimalBinarySearchTree<>(null);

        for (Pair<T, K> e: pairs) {
            tree.add(e.getLeft());
        }

        return tree;
    }

    /**
     *  Mehlhorn's approximation algorithm implementation.
     *
     *
     * @param pairs
     * @param <T>
     * @param <K>
     * @return
     */
    public static <T extends Comparable<T>, K extends Comparable<? super K>>  OptimalBinarySearchTree<T> buildMehlhorn(List<Pair<T, K>> pairs) {
        pairs.sort(Comparator.comparing(Pair::getLeft));

        return null;
//        return buildTree(pairs, 0, pairs.size());
    }

    private static <T extends Comparable<T>, K extends Comparable<K>> OptimalBinarySearchTree<T> buildTree(List<Pair<T, K>> pairs, int from, int to) {
        if (to > from) {

        }
        return null;
    }

    private static <T extends Comparable<T>, K extends Comparable<? super K>> int getPivot(List<Pair<T, K>> pairs) {
        return 0;
    }

    @Override
    public void add(T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(T element) {
        throw new UnsupportedOperationException();
    }
}
