package ru.otus.algo;

import java.util.Comparator;

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
public class BinarySearchTree<T> {

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
        Node<T> p = this.root;
        while (p != null) {
            if (comparator != null) {
                cmp = comparator.compare(p.value, element);
            } else {
                @SuppressWarnings("unchecked")
                Comparable<? super T> cpr = (Comparable<? super T>) p.value;
                cmp = cpr.compareTo(element);
            }
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
        Node<T> res = node;
        while (res.right != null) {
            res = res.right;
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

    public BinaryTreeNode<T> getTree() {
        return root;
    }

    public int size() {
        return size;
    }

    private class Node<V> implements BinaryTreeNode<V> {
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
        public BinaryTreeNode<V> getLeft() {
            return left;
        }

        @Override
        public BinaryTreeNode<V> getRight() {
            return right;
        }

        @Override
        public BinaryTreeNode<V> getParent() {
            return parent;
        }
    }
}