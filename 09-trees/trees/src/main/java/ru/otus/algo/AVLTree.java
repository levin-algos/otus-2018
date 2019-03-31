package ru.otus.algo;

import java.util.Comparator;

public class AVLTree<T> extends AbstractBinarySearchTree<T> {

    private static class AVLNode<V> extends Node<V> {
        private int height;

        AVLNode(V value, AVLNode<V> parent) {
            super(value, parent);
        }

        @Override
        public String toString() {
            return "{" + "" + value + ", " + height + '}';
        }
    }

    @Override
    protected Node<T> createNode(T value, Node<T> parent) {
        return new AVLNode<>(value, (AVLNode<T>) parent);
    }

    private AVLTree() {
        super();
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

    public static <T> AVLTree<T> of() {
        return new AVLTree<>();
    }

    public static <T> AVLTree<T> of(Comparator<? super T> cmp) {
        return new AVLTree<>(cmp);
    }

    public static <T> AVLTree<T> of(T[] arr, Comparator<? super T> cmp) {
        AVLTree<T> tree = new AVLTree<>(cmp);
        for (T i : arr) {
            tree.add(i);
        }
        return tree;
    }

    @Override
    public void remove(T element) {
        if (element == null)
            throw new IllegalArgumentException();

        Node<T> node = getElement(element);
        if (node != null) {
            delete(node);
            size--;
        }

        assert root == null || checkInvariants(root, getComparator());
    }

    private void delete(Node<T> z) {

        if (z.right != null && z.left != null) {
            Node<T> min = successor(z);
            z.value = min.value;
            z = min;
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

            removeFixup(replacement);
        } else if (z.parent == null) {
            root = null;
        } else {

            Node<T> parent = parentOf(z);
            if (z == z.parent.left)
                z.parent.left = null;
            else if (z == z.parent.right)
                z.parent.right = null;
            z.parent = null;

            removeFixup(parent);
        }
    }

    private int getHeight(Node<T> node) {
        return node == null ? 0 : ((AVLNode<T>) node).height;
    }

    private int getBalance(AVLNode<T> node) {
        return node == null ? 0 : getHeight(node.right) - getHeight(node.left);
    }

    private void balanceAfterInsertion(Node<T> node) {
        boolean rotated = false;
        while (node != null) {

            if (!rotated) {
                int balance = getBalance((AVLNode<T>) node);
                if (balance == 2) {
                    Node<T> right = rightOf(node);
                    if (right != null && getBalance((AVLNode<T>) right) < 0) {
                        rotateRight(right);
                        rotateLeft(node);
                        ((AVLNode<T>) right).height = calculateHeight(right);
                        rotated = true;
                    } else if (right != null) {
                        rotateLeft(node);
                        rotated = true;
                    }
                } else if (balance == -2) {
                    Node<T> left = leftOf(node);
                    if (left != null && getBalance((AVLNode<T>) left) > 0) {
                        rotateLeft(left);
                        rotateRight(node);
                        rotated = true;
                        ((AVLNode<T>) left).height = calculateHeight(left);
                    } else if (left != null) {
                        rotateRight(node);
                        rotated = true;
                    }
                }
            }

            ((AVLNode<T>) node).height = calculateHeight(node);


            node = parentOf(node);
        }
    }

    private void balanceAfterDeletion(Node<T> node) {
        while (node != null) {
            int balance = getBalance((AVLNode<T>) node);
            if (balance == 2) {
                Node<T> right = rightOf(node);
                if (right != null && getBalance((AVLNode<T>) right) < 0) {
                    rotateRight(right);
                    rotateLeft(node);
                    ((AVLNode<T>) right).height = calculateHeight(right);
                } else if (right != null) {
                    rotateLeft(node);
                }
            } else if (balance == -2) {
                Node<T> left = leftOf(node);
                if (left != null && getBalance((AVLNode<T>) left) > 0) {
                    rotateLeft(left);
                    rotateRight(node);
                    ((AVLNode<T>) left).height = calculateHeight(left);
                } else if (left != null) {
                    rotateRight(node);
                }
            }

            ((AVLNode<T>) node).height = calculateHeight(node);

            node = parentOf(node);
        }
    }

    private int calculateHeight(Node<T> node) {
        return Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }

    @Override
    void insertionFixup(Node<T> root) {
        balanceAfterInsertion(root);
    }

    @Override
    void removeFixup(Node<T> root) {
        balanceAfterDeletion(root);
    }

    @Override
    boolean checkInvariants(Node<T> node, Comparator<? super T> cmp) {
        return node == null || isAVL(node, getComparator(), null, null);
    }

    private static <V> boolean isAVL(Node<V> node, Comparator<? super V> cmp, V from, V to) {
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

        int nodeHeight = ((AVLNode<V>)node).height;
        int leftHeight = node.left != null ? ((AVLNode<V>)node.left).height : 0;
        int rightHeight = node.right != null ? ((AVLNode<V>) node.right).height : 0;

        if (nodeHeight != (Math.max(leftHeight, rightHeight)+1)) {
            System.out.println(String.format("%s != max(%s, %s)+1", nodeHeight, leftHeight, rightHeight));
            return false;
        }

        if (Math.abs(leftHeight - rightHeight) > 1) {
            System.out.println(String.format("|%s - %s| > 1", leftHeight, rightHeight));
            return false;
        }


        if (node.left != null && !isAVL(node.left, cmp, from, value))
            return false;

        if (node.right != null && !isAVL(node.right, cmp, value, to))
            return false;

        return true;
    }
}