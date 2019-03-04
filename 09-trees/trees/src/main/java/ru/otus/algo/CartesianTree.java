package ru.otus.algo;

import java.util.function.Function;

public class CartesianTree<V> extends AbstractBinarySearchTree<V> {

    private final Function<V, Integer> priorityConsumer;
    private static class CTNode<V> extends Node<V> {
        int priority;

        CTNode(V value, Node<V> parent, int priority) {
            super(value, parent);
            this.priority = priority;
        }
    }

    public CartesianTree(Function<V, Integer> priority) {
        super(null);
        if (priority == null)
            throw new IllegalArgumentException();

        priorityConsumer = priority;
    }

    @Override
    protected Node<V> createNode(V value, Node<V> parent) {
        return null;
    }

    static <V> CartesianTree<V> of(V[] arr, Function<V, Integer> priority) {
        return CartesianTree.build(arr, priority);
    }

//    /**
//     * Splits cartesian tree into two trees splitting {@code tree} at the key {@code element}
//     *
//     * @param element - key for splitting tree
//     * @return - {@link Pair} of trees splitted by key {@code element}
//     */
//    public Pair<CartesianTree<V>, CartesianTree<V>> split(V element) {
//        Pair<Node<V>, Node<V>> split = split(root, element);
//
//        CartesianTree<V> left = new CartesianTree<>();
//        CartesianTree<V> right = new CartesianTree<>();
//        left.root = split.getLeft();
//        if (left != null)
//            left.root.parent = left.root;
//        right.root = split.getRight();
//        if (right != null)
//            right.root.parent = right.root;
//        return Pair.of(left, right);
//    }

//    private Pair<Node<V>, Node<V>> split(Node<V> node, V element) {
//        Node<V> cur = node, left = null, right = null;
//        while (cur != null) {
//            int cmp = cur.value.compareTo(element);
//            if (cmp < 0) {
//                if (left == null) {
//                    left = cur;
//                    left.parent = null;
//                } else {
//                    left.right = cur;
//                    cur.parent = left;
//                    left = left.right;
//
//                }
//                cur = cur.right;
//            } else if (cmp > 0) {
//                if (right == null) {
//                    right = cur;
//                    right.parent = null;
//                } else {
//                    right.left = cur;
//                    cur.parent = right;
//                    right = right.left;
//                }
//                cur = cur.left;
//            } else {
//                if (left == null) {
//                    left = cur.left;
//                    left.parent = null;
//                } else {
//                    left.right = cur.left;
//                    left.right.parent = left;
//                }
//
//                if (right == null) {
//                    right = cur.right;
//                    right.parent = null;
//                } else {
//                    right.left = cur.right;
//                    right.left.parent = right;
//                }
//                cur = null;
//            }
//
//        }
//
//        if (left != null) {
////            left.right = null;
//            while (left.parent != null) {
//                left = left.parent;
//            }
//        }
//        if (right != null) {
////            right.left = null;
//            while (right.parent != null) {
//                right = right.parent;
//            }
//        }
//
//        return Pair.of(left, right);
//    }

    /**
     * Invariants:
     * 1. maximum value in left cartesian tree is lower (equal?) than the minimum value i right cartesian tree
     * @param left -
     * @param right -
     * @param <V> -
     * @return -
     */
    static <V> CartesianTree<V> merge(CartesianTree<V> left, CartesianTree<V> right) {
//        CartesianTree<V> tree = new CartesianTree<>();
//
//        tree.root = mergeNodes(left.root, right.root);
        return null;
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

//    public void add(V val) {
//        Pair<CartesianTree<V>, CartesianTree<V>> pair;
//        pair = split(val);
//        CartesianTree<V> m = CartesianTree.of(Collections.singletonList(val), priorityConsumer);
////        merge(merge(pair.getLeft(), m), pair.getRight());
//    }

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
     * @param <V>    - priorities type
     * @return - Cartesian tree
     */
    private static <V> CartesianTree<V> build(V[] values, Function<V, Integer> priority) {
        CartesianTree<V> tree = new CartesianTree<>(priority);

        if (values == null) {
            return tree;
        }

        int size = values.length;
        int[] priorities = new int[size];

        for (int i=0; i< size; i++) {
            priorities[i] = priority.apply(values[i]);
        }

        tree.root = buildTree(values, priorities, 0, size);

        return tree;
    }

    private static <V> Node<V> buildTree(V[] values, int[] priorities, int from, int to) {
        if (to > from) {
            int pivot = getMaxPriorityPos(priorities, from, to);
            Node<V> node = new CTNode<>(values[pivot], null, priorities[pivot]);
            Node<V> left = buildTree(values, priorities, from, pivot);
            Node<V> right = buildTree(values, priorities, pivot + 1, to);
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

    private static <V> int getMaxPriorityPos(int[] values, int from, int to) {
        if (values == null || from < 0 || to > values.length || to - from < 0)
            throw new IllegalArgumentException();

        int res = values[from];
        int pos = from;
        for (int i = from + 1; i < to; i++) {
            int cur = values[i];
            if (res < cur) {
                res = cur;
                pos = i;
            }

        }
        return pos;
    }
}