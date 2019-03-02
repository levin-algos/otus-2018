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

    private int getBalance(AVLNode<T> node) {
        return node == null ? 0 : getHeight(node.right) - getHeight(node.left);
    }

    private void balanceAfterInsertion(Node<T> node) {
        while (node != null) {

            if (getBalance((AVLNode<T>) node) == 2) {
                Node<T> right = rightOf(node);
                if (node.right != null && getBalance((AVLNode<T>) node.right) < 0) {
                    rotateRight(right);
                    rotateLeft(node);
                    ((AVLNode<T>) right).height = calculateHeight(right);
                } else if (right != null) {
                    rotateLeft(node);
                }
            } else if (getBalance((AVLNode<T>) node) == -2) {
                Node<T> left = leftOf(node);
                if (node.left != null && getBalance((AVLNode<T>) node.left) > 0) {
                    rotateLeft(left);
                    rotateRight(node);
                    ((AVLNode<T>) left).height = calculateHeight(left);
                } else if (left != null) {
                    rotateRight(node);
                }
            }

            int newH = calculateHeight(node);
            ((AVLNode<T>) node).height = newH;

            node = parentOf(node);
        }

    }

    private int calculateHeight(Node<T> node) {
        return Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }


    private void balanceAfterDeletion(Node<T> node) {
        while (node != null && node.parent != null) {
            Node<T> parent = parentOf(node);

            if (node == leftOf(parent)) {
                if (getBalance((AVLNode<T>) parent) > 0) {
                    Node<T> sibling = rightOf(parent);
                    if (getBalance((AVLNode<T>) sibling) < 0) {
                        rotateRight(sibling);
                        rotateLeft(parent);
                    } else
                        rotateLeft(parent);
                }
            } else {
                if (getBalance((AVLNode<T>) node) < 0) {
                    Node<T> sibling = leftOf(parent);
                    if (getBalance((AVLNode<T>) sibling) > 0) {
                        rotateLeft(sibling);
                        rotateRight(parent);
                    } else
                        rotateRight(parent);
                }
            }
            ((AVLNode<T>) node).height = Math.max(getHeight(node.left), getHeight(node.right) + 1);
        }
    }

    /**
     * Adds element to the BST
     *
     * @param element - element to add
     */
    public void add(T element) {
        insert(element, this::balanceAfterInsertion);
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

            balanceAfterInsertion(replacement);
        } else if (node.parent == null) {
            root = null;
        } else {
            Node<T> parent = parentOf(node);

            if (node == node.parent.left)
                node.parent.left = null;
            else if (node == node.parent.right)
                node.parent.right = null;
            node.parent = null;

            balanceAfterInsertion(parent);
        }
    }
}