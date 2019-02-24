package ru.otus.algo;

import java.util.Comparator;

public class AVLTree<T> implements BinaryTreeNode<T> {

    private AVLNode<T> root;
    private Comparator<T> comparator;
    private int size;

    @Override
    public T getValue() {
        return root.value;
    }

    @Override
    public BinaryTreeNode<T> getLeft() {
        return root==null? null : root.left;
    }

    @Override
    public BinaryTreeNode<T> getRight() {
        return root==null? null : root.right;
    }

    @Override
    public BinaryTreeNode<T> getParent() {
        return root==null? null : root.parent;
    }

    public boolean contains(T i) {
        return getElement(i) != null;
    }

    private class AVLNode<V> implements BinaryTreeNode<V> {
        private V value;
        private int height;
        private AVLNode<V> left;
        private AVLNode<V> right;
        private AVLNode<V> parent;

        public AVLNode(V value, AVLNode<V> parent, AVLNode<V> left, AVLNode<V> right) {
            this.value = value;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public BinaryTreeNode<V> getLeft() {
            return root==null? null : left;
        }

        @Override
        public BinaryTreeNode<V> getRight() {
            return root==null? null : right;
        }

        @Override
        public BinaryTreeNode<V> getParent() {
            return root==null? null : parent;
        }
    }

    private AVLTree() {
    }

    public AVLTree(Comparator<T> cmp) {
        this.comparator = cmp;
    }

    public static <T> AVLTree<T> of(T[] arr) {
        AVLTree<T> tree = new AVLTree<>();
        for (T i: arr) {
            tree.add(i);
        }
        return tree;
    }

    private int getHeight(AVLNode<T> node) {
        return node == null ? 0 : node.height;
    }

    private void getBalance(AVLNode<T> node) {
        if (node != null)
            node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }

    private void balance(AVLNode<T> node) {
        if (node == null)
            return;

        AVLNode<T> left = node.left;
        AVLNode<T> right = node.right;

        getBalance(node);
        getBalance(left);
        getBalance(right);

        if (Math.abs(getHeight(left) - getHeight(right)) == 2) {
            if (right != null && getHeight(right) >= 0) {
                rotateLeft(node);
            } else if (left != null && getHeight(left.left) - getHeight(left.right) >= 0) {
                rotateRight(node);
            } else if (node.left != null && getHeight(left.left) - getHeight(left.right) == 2) {
                rotateLeft(left);
                rotateRight(node);
            } else if (node.right != null && getHeight(node.right.left) - getHeight(node.right.right) == -2) {
                rotateRight(right);
                rotateLeft(node);
            }
    }

        if (node.parent != null) {
            getBalance(node.parent);
            balance(node.parent);
        }
    }

    private void rotateRight(AVLNode<T> node) {
        AVLNode<T> left = node.left;
        AVLNode<T> left1 = left.left;

        node.left = left1;
        if (left1 != null)
            left1.parent = node;

        left.left = left.right;
        left.right = node.right;
        left.parent = node;

        node.right = left;
        T tmp = node.value;
        node.value = left.value;
        left.value = tmp;

        balance(left);
        balance(left1);
    }

    private void rotateLeft(AVLNode<T> node) {
        AVLNode<T> right = node.right;

        node.right = right.right;
        if (right.right != null)
            right.right.parent = node;

        right.right = right.left;
        right.left = node.left;
        if (right.left != null)
            right.left.parent = right;

        node.left = right;
        right.parent = node;

        T tmp = node.value;
        node.value = right.value;
        right.value = tmp;

        balance(node);
        balance(right);
    }

    private AVLNode<T> getElement(T element) {
        if (element == null)
            throw new IllegalArgumentException();

        AVLNode<T> p = this.root;

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
            root = new AVLNode<>(element, null, null, null);
            size++;
            return;
        }

        int cmp;
        AVLNode<T> p = this.root;
        while (p != null) {
            if (comparator != null) {
                cmp = comparator.compare(this.root.value, element);
            } else {
                @SuppressWarnings("unchecked")
                Comparable<? super T> cpr = (Comparable<? super T>)p.value;
                cmp = cpr.compareTo(element);
            }
            if (cmp == 0) {
                return;
            } else if (cmp > 0) {
                if (p.left == null) {
                    p.left = new AVLNode<>(element, p, null, null);
                    getBalance(p.left);
                    balance(p.left);
                    size++;
                    return;
                } else
                    p = p.left;
            } else {
                if (p.right == null) {
                    p.right = new AVLNode<>(element, p, null, null);
                    getBalance(p.right);
                    balance(p.right);
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

        AVLNode<T> node = getElement(element);
        if (node != null) {
            remove(node);
            size--;
        }
    }

    private void remove(AVLNode<T> node) {
        if (node.left == null && node.right == null) {
            removeLeaf(node);
        } else if (node.left == null || node.right == null) {
            removeWithOneChild(node);
        } else {
            removeWithTwoChildren(node);
        }
    }

    private void removeWithTwoChildren(AVLNode<T> node) {
        AVLNode<T> leftTop = getMax(node.left);

        swapValues(node, leftTop);
        remove(leftTop);
    }

    private AVLNode<T> getMax(AVLNode<T> node) {
        AVLNode<T> res = node;
        while (res.right != null) {
            res = res.right;
        }
        return res;
    }


    private void swapValues(AVLNode<T> node, AVLNode<T> node1) {
        T tmp = node.value;
        node.value = node1.value;
        node1.value = tmp;
    }

    private void removeWithOneChild(AVLNode<T> node) {
        if (node == root) {
            if (root.left != null) {
                root = root.left;
            } else {
                root = root.right;
            }

            return;
        }

        if (node.left == null) {
            if (node.parent.left == node) {
                node.parent.left = node.right;
                node.right.parent = node.parent;
            }
            else {
                node.parent.right = node.right;
                node.right.parent = node.parent;
            }

        } else {
            if (node.parent.left == node) {
                node.parent.left = node.left;
                node.left.parent = node.parent;
            }
            else {
                node.parent.right = node.left;
                node.left = node.parent;
            }
        }
    }

    private void removeLeaf(AVLNode<T> node) {
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

    public int size() {
        return size;
    }
}