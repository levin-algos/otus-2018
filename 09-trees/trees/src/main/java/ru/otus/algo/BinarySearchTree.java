package ru.otus.algo;

import java.util.Comparator;
import java.util.List;

/**
 * Implementation of common methods of binary search tree (BST).
 * <p>
 * 1. Add element to tree
 * 2. Remove element from the tree
 * 3. Find element in the tree
 * 4. Build an optimal binary search tree
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
        return new BinarySearchTree<>((Comparator<? super T>) null);
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

    @Override
    boolean checkInvariants(Node<T> node, Comparator<? super T> cmp) {
        return isBST(node, getComparator(), null, null);
    }

    private static <V> boolean isBST(Node<V> node, Comparator<? super V> cmp, V from, V to) {
        V value = node.value;
        if (value != null) {
            if (cmp == null) {
                @SuppressWarnings("unchecked")
                Comparable<V> c = (Comparable<V>) value;
                if (from != null && c.compareTo(from) < 0)
                        return false;

                if (to != null && c.compareTo(to) > 0)
                        return false;
            } else {
                if (from != null && cmp.compare(value, from) < 0)
                        return false;

                if (to != null && cmp.compare(value, to) > 0)
                        return false;
            }
        }

        if (node.left != null && !isBST(node.left, cmp, from, value))
            return false;

        if (node.right != null && !isBST(node.right, cmp, value, to))
            return false;

        return true;
    }

    @Override
    void insertionFixup(Node<T> root) {}

    @Override
    void removeFixup(Node<T> root) {}

    /**
     * Build approximately optimal search tree using list of {@code pairs}.
     * Pair is an element to add in a tree and element's weight.
     * Weight of an element represents it's searching frequency.
     * Algorithm:
     * <ul>
     * <li>Sort {@code pairs} by weight in descending order</li>
     * <li>Place most frequently occurred element at the root of the tree.</li>
     * <li>Proceed similarly on subtrees</li>
     * </ul>
     * </p>
     * <p>
     * This function solves static optimality problem.
     * Tree cannot be modified since it has been constructed.
     *
     * @param pairs - list of {@code pairs} of {@code (T - K}.
     *              Where T - element to add in the tree
     *              K - weight of element.
     * @param <T>   - type of tree elements
     * @return - constructed approximately optimal immutable tree.
     */
    public static <T> BinarySearchTree<T> buildOptimal(List<Pair<T, Integer>> pairs) {
        pairs.sort(Comparator.comparing(Pair::getRight, Comparator.reverseOrder()));
        BinarySearchTree<T> tree = new BinarySearchTree<>();

        for (Pair<T, Integer> e : pairs) {
            tree.add(e.getLeft());
        }

        return new ImmutableBinarySearchTree<>(tree);
    }

    /**
     * Build nearly optimal search tree using list of {@code pairs}.
     * Pair is an element to add in a tree and element's weight.
     * Weight of an element represents it's searching frequency.
     * Main idea of this algorithm:
     * choose root of tree so as to equalize the weight of left and right subtrees as much as possible.
     * Then proceed to subtrees.
     *
     * @param pairs - list of {@code pairs} of {@code (T - K}.
     *              Where T - element to add in the tree
     *              K - frequency of element.
     * @param <T>   - type of tree elements
     * @return - constructed nearly optimal immutable tree.
     */
    public static <T extends Comparable<? super T>> BinarySearchTree<T> buildMehlhorn(List<Pair<T, Integer>> pairs) {
        BinarySearchTree<T> tree = new BinarySearchTree<>();

        if (pairs == null) {
            return tree;
        }

        pairs.sort(Comparator.comparing(Pair::getLeft));

        tree.root = buildTree(pairs, null, 0, pairs.size());
        return new ImmutableBinarySearchTree<T>(tree);
    }

    private static <T extends Comparable<? super T>> Node<T> buildTree(List<Pair<T, Integer>> pairs, Node<T> parent, int from, int to) {
        if (to > from) {
            int pivot = getPivot(pairs, from, to);
            Node<T> node = new Node<>(pairs.get(pivot).getLeft(), parent);
            Node<T> left = buildTree(pairs, node, from, pivot);
            Node<T> right = buildTree(pairs, node, pivot + 1, to);
            node.left = left;
            if (left != null)
                left.parent = node;
            node.right = right;
            if (right != null)
                right.parent = node;

            return node;
        }

        return null;
    }

    private static <T extends Comparable<? super T>> int getPivot(List<Pair<T, Integer>> pairs, int from, int to) {
        if (pairs == null || from < 0 || to > pairs.size() || to - from < 0)
            throw new IllegalArgumentException();

        int lcur = from, rcur = to - 1;
        int lsum = 0, rsum = 0;

        while (lcur < rcur) {
            if (lsum < rsum) {
                lsum += pairs.get(lcur).getRight();
                lcur++;
            } else {
                rsum += pairs.get(rcur).getRight();
                rcur--;
            }
        }
        return lcur;
    }

    private static class ImmutableBinarySearchTree<T> extends BinarySearchTree<T> {

        private ImmutableBinarySearchTree(BinarySearchTree<T> tree) {
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