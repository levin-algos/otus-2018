package ru.otus.algo;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CartesianTree<K extends Comparable<K>, V extends Comparable<V>> extends AbstractBinarySearchTree<Pair<K, V>> {


    public CartesianTree() {
        super(Comparator.comparing(Pair::getLeft));
    }

    static <K extends Comparable<K>, V extends Comparable<V>> CartesianTree<K, V> of(List<Pair<K, V>> pairs) {
        return CartesianTree.build(pairs);
    }

    /**
     * Splits cartesian tree into two trees splitting {@code tree} at the key {@code element}
     *
     * @param element - key for splitting tree
     * @return - {@link Pair} of trees splitted by key {@code element}
     */
    public Pair<CartesianTree<K, V>, CartesianTree<K, V>> split(K element) {
        Pair<Node<Pair<K, V>>, Node<Pair<K, V>>> split = split(root, element);

        CartesianTree<K, V> left = new CartesianTree<>();
        CartesianTree<K, V> right = new CartesianTree<>();
        left.root = split.getLeft();
        if (left != null)
            left.root.parent = left.root;
        right.root = split.getRight();
        if (right != null)
            right.root.parent = right.root;
        return Pair.of(left, right);
    }

    private Pair<Node<Pair<K, V>>, Node<Pair<K, V>>> split(Node<Pair<K, V>> node, K element) {
        Node<Pair<K, V>> cur = node, left = null, right = null;
        while (cur != null) {
            int cmp = cur.value.getLeft().compareTo(element);
            if (cmp < 0) {
                if (left == null) {
                    left = cur;
                    left.parent = null;
                } else {
                    left.right = cur;
                    cur.parent = left;
                    left = left.right;

                }
                cur = cur.right;
            } else if (cmp > 0) {
                if (right == null) {
                    right = cur;
                    right.parent = null;
                } else {
                    right.left = cur;
                    cur.parent = right;
                    right = right.left;
                }
                cur = cur.left;
            } else {
                if (left == null) {
                    left = cur.left;
                    left.parent = null;
                } else {
                    left.right = cur.left;
                    left.right.parent = left;
                }

                if (right == null) {
                    right = cur.right;
                    right.parent = null;
                } else {
                    right.left = cur.right;
                    right.left.parent = right;
                }
                cur = null;
            }

        }

        if (left != null) {
//            left.right = null;
            while (left.parent != null) {
                left = left.parent;
            }
        }
        if (right != null) {
//            right.left = null;
            while (right.parent != null) {
                right = right.parent;
            }
        }

        return Pair.of(left, right);
    }

    /**
     * Invariants:
     * 1. maximum value in left cartesian tree is lower (equal?) than the minimum value i right cartesian tree
     * @param left -
     * @param right -
     * @param <K> -
     * @param <V> -
     * @return -
     */
    static <K extends Comparable<K>, V extends Comparable<V>> CartesianTree<K, V> merge(CartesianTree<K, V> left, CartesianTree<K, V> right) {
        CartesianTree<K, V> tree = new CartesianTree<>();

        tree.root = mergeNodes(left.root, right.root);
        return tree;
    }

    private static <K extends Comparable<K>, V extends Comparable<V>>
    Node<Pair<K, V>> mergeNodes(Node<Pair<K, V>> left, Node<Pair<K, V>> right) {

        if (left == null) return right;
        if (right == null) return left;

        if (left.value.getRight().compareTo(right.value.getRight()) > 0) {
            Node<Pair<K, V>> newR = mergeNodes(left.right, right);
            Node<Pair<K, V>> node = new Node<>(left.value, null);
            node.left = left.left;
            node.right = newR;
            return node;
        } else {
            Node<Pair<K, V>> newL = mergeNodes(left, right.left);
            Node<Pair<K, V>> node = new Node<>(right.value, null);
            node.left = newL;
            node.right = right.right;
            return node;
        }
    }

    public void add(Pair<K, V> val) {
        Pair<CartesianTree<K, V>, CartesianTree<K, V>> pair;
        pair = split(val.getLeft());
        CartesianTree<K, V> m = CartesianTree.of(Collections.singletonList(val));
        merge(merge(pair.getLeft(), m), pair.getRight());
    }

    /**
     * Build cartesian tree for list of {@link Pair}. Pair consist of value and priority.
     * For simplicity list of {@code values} are assumed to be sorted by value (left component of Pair).
     * Algorithm is similar to quick sort.
     * buildTree(V) =
     * if |V| = 0 then empty
     * else pivot = getMaxPriorityPos(V)
     * left = {e in V | left < pivot}
     * right = {e in V | right > pivot}
     * return node(buildTree(left), pivot, BuildTree(right))
     *
     * Time complexity is {@code O(nlogn)}
     *
     * @param values - list of pairs
     * @param <K>    - type of values
     * @param <V>    - priorities type
     * @return - Cartesian tree
     */
    private static <K extends Comparable<K>, V extends Comparable<V>> CartesianTree<K, V> build(List<Pair<K, V>> values) {
        CartesianTree<K, V> tree = new CartesianTree<>();

        if (values == null) {
            return tree;
        }

        tree.root = buildTree(values, 0, values.size());

        return tree;
    }

    private static <K extends Comparable<K>, V extends Comparable<V>> Node<Pair<K, V>> buildTree(List<Pair<K, V>> values, int from, int to) {
        if (to > from) {
            int pivot = getMaxPriorityPos(values, from, to);
            Node<Pair<K, V>> node = new Node<>(values.get(pivot), null);
            Node<Pair<K, V>> left = buildTree(values, from, pivot);
            Node<Pair<K, V>> right = buildTree(values, pivot + 1, to);
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

    private static <K extends Comparable<K>, V extends Comparable<V>> int getMaxPriorityPos(List<Pair<K, V>> values, int from, int to) {
        if (values == null || from < 0 || to > values.size() || to - from < 0)
            throw new IllegalArgumentException();

        Pair<K, V> res = values.get(from);
        int pos = from;
        for (int i = from + 1; i < to; i++) {
            Pair<K, V> pair = values.get(i);
            if (res.getRight().compareTo(pair.getRight()) < 0) {
                res = pair;
                pos = i;
            }

        }
        return pos;
    }

    @Override
    protected Node<Pair<K, V>> createNode(Pair<K, V> value, Node<Pair<K, V>> parent) {
        return new Node<>(value, parent);
    }
}