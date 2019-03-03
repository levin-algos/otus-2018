package ru.otus.algo;

import java.util.*;

public class RedBlackTree<T> extends AbstractBinarySearchTree<T> {

    private RedBlackTree() {
        super();
    }

    private RedBlackTree(Comparator<? super T> cmp) {
        super(cmp);
    }

    @Override
    protected Node<T> createNode(T value, Node<T> parent) {
        return new RedBlackTreeNode<>(value, (RedBlackTreeNode<T>)parent);
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

    /**
     * Adds element to the BST
     *
     * @param element - element to add
     */
    public void add(T element) {
        insert(element, node -> insertFixUp((RedBlackTreeNode<T>) node));
    }

    /**
     * Removes the {@code element} from red black tree.
     *
     * @param element - element to remove
     */
    public void remove(T element) {
        if (element == null)
            throw new IllegalArgumentException();

        RedBlackTreeNode<T> node = (RedBlackTreeNode<T>) getElement(element);

        if (node != null) {
            delete(node);
            size--;
        }
    }

    private void insertFixUp(RedBlackTreeNode<T> node) {
        while (node != null && root != node && colorOf(parentOf(node)) == RED) {
            Node<T> grand = parentOf(parentOf(node));
            if (parentOf(node) == leftOf(grand)) {
                Node<T> r = rightOf(grand);
                if (colorOf(r) == RED) {
                    setColor(parentOf(node), BLACK);
                    setColor(r, BLACK);
                    setColor(grand, RED);
                    node = (RedBlackTreeNode<T>) grand;
                } else {
                    if (node == rightOf(parentOf(node))) {
                        node = (RedBlackTreeNode<T>) parentOf(node);
                        rotateLeft(node);
                    }
                    setColor(parentOf(node), BLACK);
                    setColor(grand, RED);
                    rotateRight(grand);
                }
            } else {
                RedBlackTreeNode<T> l = (RedBlackTreeNode<T>) leftOf(grand);
                if (colorOf(l) == RED) {
                    setColor(parentOf(node), BLACK);
                    setColor(l, BLACK);
                    setColor(grand, RED);
                    node = (RedBlackTreeNode<T>) grand;
                } else {
                    if (node == leftOf(parentOf(node))) {
                        node = (RedBlackTreeNode<T>) parentOf(node);
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

    private static <T> boolean colorOf(Node<T> node) {
        return node == null ? BLACK : ((RedBlackTreeNode<T>)node).color;
    }

    private static <T> void setColor(Node<T> node, boolean color) {
        if (node != null) {
            ((RedBlackTreeNode<T>)node).color = color;
        }
    }

    private void delete(RedBlackTreeNode<T> z) {

        if (z.right != null && z.left != null) {
            Node<T> min = successor(z);
            z.value = min.value;
            z = (RedBlackTreeNode<T>) min;
        }

        Node<T> replacement = z.left != null ? z.left : z.right;

        if (replacement != null) {
            replacement.parent = z.parent;
            if (z.parent == null)
                root = replacement;
            else if (z == z.parent.left)
                z.parent.left = replacement;
            else
                z.parent.right = replacement;

            z.left = z.right = z.parent = null;

            if (z.color == BLACK)
                deleteFixup((RedBlackTreeNode<T>) replacement);
        } else if (z.parent == null) {
            root = null;
        } else {
            if (z.color == BLACK)
                deleteFixup(z);

            if (z.parent != null) {
                if (z == z.parent.left)
                    z.parent.left = null;
                else if (z == z.parent.right)
                    z.parent.right = null;
                z.parent = null;
            }
        }
    }

    private void deleteFixup(RedBlackTreeNode<T> x) {

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
                    x = (RedBlackTreeNode<T>) parentOf(x);
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
                    x = (RedBlackTreeNode<T>) root;
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
                    x = (RedBlackTreeNode<T>) parentOf(x);
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
                    x = (RedBlackTreeNode<T>) root;
                }
            }
        }
        setColor(x, BLACK);
    }

    final static boolean RED = true;
    final static boolean BLACK = false;

    private static class RedBlackTreeNode<V> extends Node<V> {
        boolean color = RED;

        RedBlackTreeNode(V value, RedBlackTreeNode<V> parent) {
            super(value, parent);
        }
    }
}