package ru.otus.algo;

import java.util.*;

public class RedBlackTree<T> extends AbstractBinarySearchTree<T> {

    private RedBlackTree() {
        super();
    }

    private RedBlackTree(Comparator<? super T> cmp) {
        super(cmp);
    }

    static <T> RedBlackTree<T> of(T[] arr) {
        RedBlackTree<T> tree = new RedBlackTree<>();

        for (T i : arr) {
            tree.add(i);
        }
        return tree;
    }

    static <T> RedBlackTree<T> of(T[] arr, Comparator<? super T> cmp) {
        RedBlackTree<T> tree = new RedBlackTree<>(cmp);

        for (T i : arr) {
            tree.add(i);
        }
        return tree;
    }

    @Override
    protected Node<T> createNode(T value, Node<T> parent) {
        return new RedBlackTreeNode<>(value, (RedBlackTreeNode<T>)parent);
    }

    @Override
    void insertionFixup(Node<T> node) {
        if (colorOf(node) == BLACK) {
            while (node != null && root != node && colorOf(parentOf(node)) == RED) {
                Node<T> grand = parentOf(parentOf(node));
                if (parentOf(node) == leftOf(grand)) {
                    Node<T> r = rightOf(grand);
                    if (colorOf(r) == RED) {
                        setColor(parentOf(node), BLACK);
                        setColor(r, BLACK);
                        setColor(grand, RED);
                        node = grand;
                    } else {
                        if (node == rightOf(parentOf(node))) {
                            node = parentOf(node);
                            rotateLeft(node);
                        }
                        setColor(parentOf(node), BLACK);
                        setColor(grand, RED);
                        rotateRight(grand);
                    }
                } else {
                    Node<T> l = leftOf(grand);
                    if (colorOf(l) == RED) {
                        setColor(parentOf(node), BLACK);
                        setColor(l, BLACK);
                        setColor(grand, RED);
                        node = grand;
                    } else {
                        if (node == leftOf(parentOf(node))) {
                            node = parentOf(node);
                            rotateRight(node);
                        }
                        setColor(parentOf(node), BLACK);
                        setColor(grand, RED);
                        rotateLeft(grand);
                    }
                }
            }
            setColor(root, BLACK);
        }
    }

    @Override
    void removeFixup(Node<T> x) {
        while (x != root && colorOf(x) == BLACK) {
            if (x == leftOf(parentOf(x))) {
                Node<T> sib = rightOf(parentOf(x));
                if (colorOf(sib) == RED) {
                    setColor(sib, BLACK);
                    setColor(parentOf(x), RED);
                    rotateLeft(parentOf(x));
                    sib = rightOf(parentOf(x));
                }
                if (colorOf(leftOf(sib)) == BLACK && colorOf(rightOf(sib)) == BLACK) {
                    setColor(sib, RED);
                    x = parentOf(x);
                } else {
                    if (colorOf(rightOf(sib)) == BLACK) {
                        setColor(leftOf(sib), BLACK);
                        setColor(sib, RED);
                        rotateRight(sib);
                        sib = rightOf(parentOf(x));
                    }
                    setColor(sib, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(rightOf(sib), BLACK);
                    rotateLeft(parentOf(x));
                    x = root;
                }
            } else {
                Node<T> sib = leftOf(parentOf(x));
                if (colorOf(sib) == RED) {
                    setColor(sib, BLACK);
                    setColor(parentOf(x), RED);
                    rotateRight(parentOf(x));
                    sib = leftOf(parentOf(x));
                }
                if (colorOf(rightOf(sib)) == BLACK && colorOf(leftOf(sib)) == BLACK) {
                    setColor(sib, RED);
                    x = parentOf(x);
                } else {
                    if (colorOf(leftOf(sib)) == BLACK) {
                        setColor(rightOf(sib), BLACK);
                        setColor(sib, RED);
                        rotateLeft(sib);
                        sib = leftOf(parentOf(x));
                    }
                    setColor(sib, colorOf(parentOf(x)));
                    setColor(parentOf(x), BLACK);
                    setColor(leftOf(sib), BLACK);
                    rotateRight(parentOf(x));
                    x = root;
                }
            }
        }
        setColor(x, BLACK);
    }

    private static <T> boolean colorOf(Node<T> node) {
        return node == null ? BLACK : ((RedBlackTreeNode<T>)node).color;
    }

    private static <T> void setColor(Node<T> node, boolean color) {
        if (node != null) {
            ((RedBlackTreeNode<T>)node).color = color;
        }
    }

    private final static boolean RED = true;
    private final static boolean BLACK = false;

    private static class RedBlackTreeNode<V> extends Node<V> {

        boolean color = RED;
        RedBlackTreeNode(V value, RedBlackTreeNode<V> parent) {
            super(value, parent);
        }

    }

    @Override
    boolean checkInvariants(Node<T> node, Comparator<? super T> cmp) {
        if (node == null) return true;

        if (!isRBT(node, getComparator(), null, null))
            return false;

        return countBlackNodes(node).getLeft();
    }

    private static <V> boolean isRBT(Node<V> node, Comparator<? super V> cmp, V from, V to) {
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
        if (node.left != null && node.right != null) {
            boolean leftColor = ((RedBlackTreeNode<V>)node.left).color;
            boolean rightColor = ((RedBlackTreeNode<V>)node.right).color;
            boolean color = ((RedBlackTreeNode<V>) node).color;
            return color != RedBlackTree.RED || (leftColor == RedBlackTree.BLACK && rightColor == RedBlackTree.BLACK);
        }

        if (node.left != null && !isRBT(node.left, cmp, from, value))
            return false;

        if (node.right != null && !isRBT(node.right, cmp, value, to))
            return false;

        return true;
    }

    private Pair<Boolean, Integer> countBlackNodes(Node<T> entry) {
        if (entry == null)
            return Pair.of(true, 0);

        Node<T> left = entry.left;
        Node<T> right = entry.right;

        Pair<Boolean, Integer> leftRes = countBlackNodes(left);
        Pair<Boolean, Integer> rightRes = countBlackNodes(right);

        if (leftRes.getLeft() && rightRes.getLeft()) {
            if (leftRes.getRight().equals(rightRes.getRight())) {
                int inc = ((RedBlackTreeNode<T>)entry).color == RedBlackTree.BLACK ? 1 : 0;
                return Pair.of(true, leftRes.getRight() + inc);
            } else {
                return Pair.of(false, 0);
            }
        } else {
            return Pair.of(false, 0);
        }
    }
}