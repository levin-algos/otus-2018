package ru.otus.algo;

import java.util.Comparator;
import java.util.function.Consumer;

/**
 * Implementation of common methods of binary search tree (BST).
 * <p>
 * 1. Add element to tree
 * 2. Remove element from the tree
 * 3. Find element in the tree
 * 4. Traverse
 * </p>
 * This implementation cannot keep null values.
 *
 * @param <T> - type of elements in BST
 */
public class BinarySearchTree<T> implements TreeNode<T> {


    private int size;

    private Comparator<? super T> comparator;
    private Node<T> root;


    public BinarySearchTree() {
        comparator = null;
    }

    public BinarySearchTree(Comparator<? super T> comparator) {
        this.comparator = comparator;
    }


    public static <T> BinarySearchTree<T> of(T[] arr) {
        BinarySearchTree<T> tree = new BinarySearchTree<>();
        for (T i : arr) {
            tree.add(i);
        }
        return tree;
    }

    /**
     * Searches element in BST.
     * If {@code element} is {@code null} - throws IllegalArgumentException
     *
     * @param element - element to contains
     * @return - true if element exists in BST, else false
     */
    public boolean contains(T element) {
        return getElement(element) != null;
    }

    private Node<T> getElement(T element) {
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


    /**
     * Adds element to the BST
     *
     * @param element - element to add
     */
    public void add(T element) {
        if (element == null)
            throw new IllegalArgumentException();

        if (root == null) {
            root = new Node<>(element, null, null, null);
            size++;
        }


        int cmp;
        if (comparator != null) {
            cmp = comparator.compare(this.root.value, element);
        } else {
            @SuppressWarnings("unchecked")
            Comparable<? super T> cpr = (Comparable<? super T>) this.root.value;
            cmp = cpr.compareTo(element);
        }

        Node<T> p = this.root;
        while (p != null) {
            if (cmp == 0) {
                return;
            } else if (cmp > 0) {
                if (p.left == null) {
                    p.left = new Node<>(element, p, null, null);
                    size++;
                    return;
                } else
                    p = p.left;
            } else {
                if (p.right == null) {
                    p.right = new Node<>(element, p, null, null);
                    size++;
                    return;
                } else
                    p = p.right;
            }
        }
    }

    /**
     * Removes the {@code element} from BST.
     *
     * @param element - element to remove
     */
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
        }
    }

    private void removeLeaf(Node<T> node) {
        if (node == node.parent.left)
            node.parent.left = null;
        if (node == node.parent.right) node.parent.right = null;
        else throw new IllegalStateException();
    }

    @Override
    public T getValue() {
        return root == null ? null : root.value;
    }

    @Override
    public TreeNode<T> getLeft() {
        return root == null ? null : root.left;
    }

    @Override
    public TreeNode<T> getRight() {
        return root == null ? null : root.right;
    }

    @Override
    public TreeNode<T> getParent() {
        return root == null ? null : root.parent;
    }

    public int size() {
        return size;
    }

    private class Node<V> implements TreeNode<V> {
        private V value;
        private Node<V> parent;
        private Node<V> left;
        private Node<V> right;

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    '}';
        }

        Node(V value, Node<V> parent, Node<V> left, Node<V> right) {
            this.value = value;
            this.parent = parent;
            this.left = left;
            this.right = right;

            if (left != null)
                left.parent = this;
            if (right != null)
                right.parent = this;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public TreeNode<V> getLeft() {
            return left;
        }

        @Override
        public TreeNode<V> getRight() {
            return right;
        }

        @Override
        public TreeNode<V> getParent() {
            return parent;
        }
    }
}