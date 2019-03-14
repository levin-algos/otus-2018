package ru.otus.algo;

import java.util.function.Function;

/**
 * Cartesian tree implementation. Priority generates with {@code Function<V, Integer>} functional interface.
 * <p>
 * This implementation is immutable.
 *
 * @param <V>
 */
public class CartesianTree<V> extends AbstractBinarySearchTree<V> {

    private final Function<V, Integer> priorityConsumer;

    private static class CTNode<V> extends Node<V> {
        int priority;

        CTNode(V value, Node<V> parent, int priority) {
            super(value, parent);
            this.priority = priority;
        }

        @Override
        public String toString() {
            return "{" +
                    value +", " +
                    priority +
                    '}';
        }
    }

    public CartesianTree(Function<V, Integer> priority) {
        super();
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

    /**
     * Splits cartesian tree into two trees.
     * Splitting {@code tree} at the key {@code element}
     *
     * @param element - key for splitting tree
     * @return - {@link Pair} of trees splitted by key {@code element}
     */
    public Pair<CartesianTree<V>, CartesianTree<V>> split(V element) {
        Pair<Node<V>, Node<V>> split = split(root, element);

        CartesianTree<V> left = new CartesianTree<>(priorityConsumer);
        CartesianTree<V> right = new CartesianTree<>(priorityConsumer);
        left.root = split.getLeft();
        if (left.root != null)
            left.root.parent = null;
        right.root = split.getRight();
        if (right.root != null)
            right.root.parent = null;
        return Pair.of(left, right);
    }

    private Pair<Node<V>, Node<V>> split(Node<V> node, V element) {
        Node<V> cur = node, left = null, right = null;
        Comparable<V> c = (Comparable<V>) element;
        while (cur != null) {
            int cmp = c.compareTo(cur.value);
            if (cmp > 0) {
                if (left == null) {
                    left = cur;
                    left.parent = null;
                } else {
                    left.right = cur;
                    cur.parent = left;
                    left = left.right;
                }
                cur = cur.right;
                left.right = null;
            } else if (cmp < 0) {
                if (right == null) {
                    right = cur;
                    right.parent = null;
                } else {
                    right.left = cur;
                    cur.parent = right;
                    right = right.left;
                }
                cur = cur.left;
                right.left = null;
            } else {
                if (left == null) {
                    left = cur.left;
                    if (left != null)
                        left.parent = null;
                } else {
                    left.right = cur.left;
                    if (left.right != null)
                        left.right.parent = left;
                }

                if (right == null) {
                    right = cur.right;
                    if (right != null)
                        right.parent = null;
                } else {
                    right.left = cur.right;
                    if (right.left != null)
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
     * Merge two cartesian trees into one.
     * Priority functions must be the same for {@code left} and {@code right} cartesian tree.
     * If both of {@code left} and {@code right} are null - returns null
     * Otherwise if {@code left} is null then return {@code right}.
     * Otherwise If {@code right} is null then return {@code left}.
     * Invariants:
     * 1. maximum value in left cartesian tree is lower (equal?) than the minimum value i right cartesian tree
     *
     * @param left  - left cartesian tree
     * @param right - right cartesian tree
     * @param <V>   - type value of cartesian trees
     * @return - merged cartesian tree
     */
    static <V> CartesianTree<V> merge(CartesianTree<V> left, CartesianTree<V> right) {
        if (left == null && right == null)
            return null;
        if (left == null) return right;
        if (right == null) return left;

        if (left.priorityConsumer != right.priorityConsumer)
            throw new IllegalStateException("priority functions are not equal");

        CartesianTree<V> tree = new CartesianTree<>(left.priorityConsumer);

        tree.root = mergeNodes((CTNode<V>) left.root, (CTNode<V>) right.root, (CTNode<V>) tree.root);
        return tree;
    }

    private static <V> CTNode<V> mergeNodes(CTNode<V> left, CTNode<V> right, CTNode<V> parent) {

        if (left == null && right == null)
            return null;

        if (left == null) {
            right.parent = parent;
            return right;
        }
        if (right == null) {
            left.parent = parent;
            return left;
        }

        if (left.priority > right.priority) {
            CTNode<V> newR = mergeNodes((CTNode<V>) left.right, right, left);
            CTNode<V> node = new CTNode<>(left.value, parent, left.priority);
            node.left = left.left;
            if (left.left != null)
                left.left.parent = node;
            node.right = newR;
            if (newR != null)
                newR.parent = node;
            return node;
        } else {
            CTNode<V> newL = mergeNodes(left, (CTNode<V>) right.left, right);
            CTNode<V> node = new CTNode<>(right.value, parent, right.priority);
            node.left = newL;
            if (newL != null)
                newL.parent = node;
            node.right = right.right;
            if (right.right != null)
                right.right.parent = node;
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
     * <p>
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

        for (int i = 0; i < size; i++) {
            priorities[i] = priority.apply(values[i]);
        }

        tree.root = buildTree(values, priorities, 0, size, null);

        return tree;
    }

    private static <V> Node<V> buildTree(V[] values, int[] priorities, int from, int to, CTNode<V> parent) {
        if (to > from) {
            int pivot = getMaxPriorityPos(priorities, from, to);
            CTNode<V> node = new CTNode<>(values[pivot], parent, priorities[pivot]);
            Node<V> left = buildTree(values, priorities, from, pivot, node);
            Node<V> right = buildTree(values, priorities, pivot + 1, to, node);
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

    private static int getMaxPriorityPos(int[] values, int from, int to) {
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