package ru.otus.algo;

import java.util.Comparator;
import java.util.function.Function;

/**
 * Cartesian tree implementation. Priority generates with {@code Function<V, Integer>} functional interface.
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
                    value + ", " +
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
    public void add(V element) {
        if (contains(element)) return;

        Node<V> cur = root;
        int priority = priorityConsumer.apply(element);
        Comparator<? super V> cmp = getComparator();
        Node<V> parent = null;
        if (cmp == null) {
            Comparable<V> c = (Comparable<V>) element;
            while (cur != null && ((CTNode<V>) cur).priority > priority) {
                parent = cur;
                int cmpRes = c.compareTo(cur.value);
                if (cmpRes == 0) {
                    return;
                } else if (cmpRes > 0) {
                    cur = cur.right;
                } else
                    cur = cur.left;
            }

            CTNode<V> node = new CTNode<>(element, parent, priority);
            if (cur == null) {
                if (parent == null) {
                    root = node;
                    return;
                }
                int i = c.compareTo(parent.value);
                if (i > 0) {
                    parent.right = node;
                } else if (i < 0) {
                    parent.left = node;
                }
            } else {
                Pair<Node<V>, Node<V>> split = split(cur, element);
                node.left = split.getLeft();
                if (node.left != null)
                    node.left.parent = node;
                node.right = split.getRight();
                if (node.right != null)
                    node.right.parent = node;

                if (parent == null) {
                    root = node;
                } else {
                    if (c.compareTo(parent.value) < 0) {
                        parent.left = node;
                    } else if (c.compareTo(parent.value) > 0) {
                        parent.right = node;
                    }
                }
            }
        }
        //TODO: comparator loop
    }

    @Override
    public void remove(V element) {
        Node<V> node = getElement(element);
        if (node == null)
            return;

        Node<V> merged = mergeNodes(node.left, node.right, node.parent);
        if (node.parent == null) {
            root = merged;
        } else {
            if (node.parent.left == node) {
                node.parent.left = merged;
            } else {
                node.parent.right = merged;
            }
        }
    }

    @Override
    protected Node<V> createNode(V value, Node<V> parent) {
        throw new UnsupportedOperationException();
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
            } else {
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
            }

        }

        if (left != null) {
            while (left.parent != null) {
                left = left.parent;
            }
        }
        if (right != null) {
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
    private static <V> Node<V> mergeNodes(Node<V> left, Node<V> right, Node<V> parent) {

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

        int leftPriority = ((CTNode<V>)left).priority;
        int rightPriority = ((CTNode<V>)right).priority;
        if (leftPriority > rightPriority) {
            Node<V> newR = mergeNodes(left.right, right, left);
            CTNode<V> node = new CTNode<>(left.value, parent, leftPriority);
            node.left = left.left;
            if (left.left != null)
                left.left.parent = node;
            node.right = newR;
            if (newR != null)
                newR.parent = node;
            return node;
        } else {
            Node<V> newL = mergeNodes(left, right.left, right);
            Node<V> node = new CTNode<>(right.value, parent, rightPriority);
            node.left = newL;
            if (newL != null)
                newL.parent = node;
            node.right = right.right;
            if (right.right != null)
                right.right.parent = node;
            return node;
        }
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