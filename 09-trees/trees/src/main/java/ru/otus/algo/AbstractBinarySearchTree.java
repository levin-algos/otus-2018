package ru.otus.algo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

abstract class AbstractBinarySearchTree<T> implements BinaryTree<T> {
    int size;

    private final Comparator<? super T> comparator;
    Node<T> root;

    AbstractBinarySearchTree() {
        comparator = null;
    }

    AbstractBinarySearchTree(Comparator<? super T> comparator) {
        this.comparator = comparator;
    }

    protected abstract Node<T> createNode(T value, Node<T> parent);

    /**
     * Searches element in BST.
     * If {@code element} is {@code null} - throws IllegalArgumentException
     *
     * @param element - element to contains
     * @return - true if element exists in BST, else false
     */
    @Override
    public boolean contains(T element) {
        return getElement(element) != null;
    }

    Node<T> getElement(T element) {
        if (element == null)
            throw new IllegalArgumentException();

        Node<T> p = this.root;

        while (p != null) {
            int cmp;
            if (comparator != null) {
                cmp = comparator.compare(p.value, element);
            } else {
                @SuppressWarnings("unchecked")
                Comparable<? super T> cpr = (Comparable<? super T>) p.value;
                cmp = cpr.compareTo(element);
            }
            if (cmp == 0) {
                return p;
            } else if (cmp > 0) {
                p = p.left;
            } else {
                p = p.right;
            }
        }
        return null;
    }

    @Override
    public void traverse(Traversal order, Consumer<T> action) {
        if (Traversal.IN_ORDER == order) {
            traverseInOrder(action);
        } else
            throw new IllegalStateException();
    }

    private void traverseInOrder(Consumer<T> action) {
        for (Node<T> cur = getMin(root); cur != null; cur = successor(cur)) {
            action.accept(cur.value);
        }
    }

    /**
     * Adds element to the BST
     *
     * @param element - element to add
     */
    @Override
    public void add(T element) {
        insert(element, tNode -> {
        });
    }

    void insert(T element, Consumer<Node<T>> consumer) {
        if (element == null)
            throw new IllegalArgumentException();

        if (root == null) {
            root = createNode(element, null);
            consumer.accept(root);
            size++;
            return;
        }

        int cmp;
        Node<T> p = root;
        Node<T> parent;

        if (comparator != null) {
            do {
                cmp = comparator.compare(element, p.value);
                parent = p;
                if (cmp < 0) {
                    p = p.left;
                } else if (cmp > 0) {
                    p = p.right;
                } else return;
            } while (p != null);

        } else {
            @SuppressWarnings("unchecked")
            Comparable<? super T> cpr = (Comparable<? super T>) element;
            do {
                cmp = cpr.compareTo(p.value);
                parent = p;
                if (cmp > 0) {
                    p = p.right;
                } else if (cmp < 0) {
                    p = p.left;
                } else return;
            } while (p != null);
        }

        Node<T> node = createNode(element, parent);
        if (cmp < 0)
            parent.left = node;
        else
            parent.right = node;

        consumer.accept(node);

        size++;
    }

    /**
     * Removes the {@code element} from BST.
     *
     * @param element - element to remove
     */
    @Override
    public void remove(T element) {
        if (element == null)
            throw new IllegalArgumentException();

        Node<T> node = getElement(element);
        if (node != null) {
            remove(node);
            size--;
        }
    }

    private void remove(Node<T> node) {
        if (node.left == null && node.right == null) {
            removeLeaf(node);
        } else if (node.left == null || node.right == null) {
            removeWithOneChild(node);
        } else {
            removeWithTwoChildren(node);
        }
    }

    private void removeWithTwoChildren(Node<T> node) {
        Node<T> leftTop = getMax(node.left);

        swapValues(node, leftTop);
        remove(leftTop);
    }

    private Node<T> getMax(Node<T> node) {
        if (node == null)
            return null;

        Node<T> res = node;
        while (res.right != null) {
            res = res.right;
        }
        return res;
    }

    private Node<T> getMin(Node<T> node) {
        if (node == null)
            return null;

        Node<T> res = node;
        while (res.left != null) {
            res = res.left;
        }
        return res;
    }


    private void swapValues(Node<T> node, Node<T> node1) {
        T tmp = node.value;
        node.value = node1.value;
        node1.value = tmp;
    }

    private void removeWithOneChild(Node<T> node) {
        if (node == root) {
            if (root.left != null) {
                root = root.left;
            } else {
                root = root.right;
            }

            return;
        }

        if (node.left == null) {
            if (node.parent.left == node)
                node.parent.left = node.right;
            else
                node.parent.right = node.right;
        } else {
            if (node.parent.left == node)
                node.parent.left = node.left;
            else
                node.parent.right = node.left;
        }
    }

    private void removeLeaf(Node<T> node) {
        if (node == root) {
            root = null;
            return;
        }

        if (node == node.parent.left)
            node.parent.left = null;
        else if (node == node.parent.right)
            node.parent.right = null;
        else throw new IllegalStateException();
    }


    static <T> Node<T> parentOf(Node<T> node) {
        return node == null ? null : node.parent;
    }

    static <T> Node<T> leftOf(Node<T> node) {
        return node == null ? null : node.left;
    }

    static <T> Node<T> rightOf(Node<T> node) {
        return node == null ? null : node.right;
    }

    void rotateRight(Node<T> node) {
        if (node != null) {
            Node<T> l = node.left;
            node.left = rightOf(l);
            if (l.right != null) l.right.parent = node;
            l.parent = parentOf(node);
            if (node.parent == null)
                root = l;
            else if (node.parent.right == node)
                node.parent.right = l;
            else node.parent.left = l;
            l.right = node;
            node.parent = l;
        }
    }

    void rotateLeft(Node<T> node) {
        if (node != null) {
            Node<T> r = node.right;
            node.right = r.left;
            if (r.left != null)
                r.left.parent = node;
            r.parent = node.parent;
            if (node.parent == null)
                root = r;
            else if (node == node.parent.left)
                node.parent.left = r;
            else node.parent.right = r;
            r.left = node;
            node.parent = r;
        }
    }

    Node<T> successor(Node<T> it) {
        if (it == null)
            return null;

        else if (it.right != null) {
            Node<T> p = it.right;
            while (p.left != null)
                p = p.left;
            return p;
        } else {
            Node<T> p = it.parent;
            Node<T> ch = it;

            while (p != null && p.right == ch) {
                ch = p;
                p = p.parent;
            }

            return p;
        }
    }

    /**
     * Get maximum height of binary tree
     * Returns {@code 0} for empty tree
     *
     * @return - maximum height
     */
    int getMaxHeight() {
        if (root == null)
            return 0;

        int left = getMaxHeightNode(root.left);
        int right = getMaxHeightNode(root.right);

        return Math.max(left, right) + 1;
    }

    private int getMaxHeightNode(Node<T> node) {
        if (node == null)
            return 0;

        int left = getMaxHeightNode(node.left);
        int right = getMaxHeightNode(node.right);

        return Math.max(left, right) + 1;
    }


    public void saveToFile(Path path) {
        TreeVisualizer treeVisualizer = new TreeVisualizer(getMaxHeight());
        treeVisualizer.save(path, root);
    }

    @Override
    public int size() {
        return size;
    }

    protected static class Node<V> {
        V value;
        Node<V> parent;
        Node<V> left;
        Node<V> right;

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    '}';
        }

        Node(V value, Node<V> parent) {
            this.value = value;
            this.parent = parent;
        }
    }
}
