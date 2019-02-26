package ru.otus.algo;

import java.util.Comparator;

public class AVLTree<T> extends AbstractBinarySearchTree<T> {

    public boolean contains(T i) {
        return getElement(i) != null;
    }

    private class AVLNode<V> extends Node<V> {
        private int height;

        AVLNode(V value, AVLNode<V> parent) {
            super(value, parent);
        }

    }

    @Override
    protected Node<T> createNode(T value, Node<T> parent) {
        return new AVLNode<>(value, (AVLNode<T>) parent);
    }

    private AVLTree() {
        super(null);
    }

    private AVLTree(Comparator<? super T> cmp) {
        super(cmp);
    }

    static <T> AVLTree<T> of(T[] arr) {
        AVLTree<T> tree = new AVLTree<>();
        for (T i : arr) {
            tree.add(i);
        }
        return tree;
    }

    public static <T> AVLTree<T> of(T[] arr, Comparator<? super T> cmp) {
        AVLTree<T> tree = new AVLTree<>(cmp);
        for (T i : arr) {
            tree.add(i);
        }
        return tree;
    }

    private int getHeight(Node<T> node) {
        return node == null ? 0 : ((AVLNode<T>) node).height;
    }

    private void getBalance(AVLNode<T> node) {
        if (node != null)
            node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }

    private void balance(AVLNode<T> node) {
        if (node == null)
            return;

        Node<T> left = node.left;
        Node<T> right = node.right;

        getBalance(node);
        getBalance((AVLNode<T>) left);
        getBalance((AVLNode<T>) right);

        if (Math.abs(getHeight(left) - getHeight(right)) == 2) {
            if (right != null && getHeight(right) >= 0) {
                rotateLeft(node);
            } else if (left != null && getHeight(left.left) - getHeight(left.right) >= 0) {
                rotateRight(node);
            } else if (node.left != null && getHeight(left != null ? left.left : null) - getHeight(left != null ? left.right : null) == 2) {
                rotateLeft(left);
                rotateRight(node);
            } else if (node.right != null && getHeight(node.right.left) - getHeight(node.right.right) == -2) {
                rotateRight(right);
                rotateLeft(node);
            }
        }

        if (node.parent != null) {
            getBalance((AVLNode<T>) node.parent);
            balance((AVLNode<T>) node.parent);
        }
    }


    /**
     * Adds element to the BST
     *
     * @param element - element to add
     */
    public void add(T element) {
        insert(element, node -> {
            getBalance((AVLNode<T>) node);
            balance((AVLNode<T>) node);
        });
    }

    /**
     * Removes the {@code element} from BST.
     *
     * @param element - element to remove
     */
    public void remove(T element) {
        if (element == null)
            throw new IllegalArgumentException();

        AVLNode<T> node = (AVLNode<T>) getElement(element);
        if (node != null) {
            remove(node);
            size--;
        }
    }

    private void remove(AVLNode<T> node) {
        if (node.right != null && node.left != null) {
            Node<T> suc = successor(node);
            node.value = suc.value;
            node = (AVLNode<T>) suc;
        }

        Node<T> replacement = node.left != null ? node.left : node.right;

        if (replacement != null) {
            replacement.parent = node.parent;
            if (node.parent == null)
                root = replacement;
            else if (node == node.parent.left)
                node.parent.left = replacement;
            else
                node.parent.right = replacement;

            node.left = node.right = node.parent = null;

            getBalance((AVLNode<T>) replacement);
            balance((AVLNode<T>) replacement);
        } else if (node.parent == null) {
            root = null;
        } else {
            getBalance(node);
            balance(node);

            if (node.parent != null) {
                if (node == node.parent.left)
                    node.parent.left = null;
                else if (node == node.parent.right)
                    node.parent.right = null;
                node.parent = null;
            }
        }
    }
}