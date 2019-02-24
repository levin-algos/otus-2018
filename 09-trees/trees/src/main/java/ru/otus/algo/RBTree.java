package ru.otus.algo;

import java.util.Comparator;

public class RBTree<T> {

    private int size;
    private RBTreeNode<T> root;
    private final Comparator<T> comparator;

    public RBTree() {
        comparator = null;
    }

    public static <T> RBTree<T> of(T[] arr) {
        RBTree<T> tree = new RBTree<>();

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

    private RBTreeNode<T> getElement(T element) {
        if (element == null)
            throw new IllegalArgumentException();

        RBTreeNode<T> p = this.root;

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
            root = new RBTreeNode<>(element, null);
            insertRoot(root);
            size++;
            return;
        }

        int cmp;
        RBTreeNode<T> p = this.root;
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
                    p.left = new RBTreeNode<>(element, p);
                    insertRoot(p.left);
                    size++;
                    return;
                } else
                    p = p.left;
            } else {
                if (p.right == null) {
                    p.right = new RBTreeNode<>(element, p);
                    insertRoot(p.right);
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

        RBTreeNode<T> node = getElement(element);
        if (node != null) {
            remove(node);
            size--;
        }
    }

    private void remove(RBTreeNode<T> node) {
        if (node.left == null && node.right == null) {
            removeLeaf(node);
        } else if (node.left == null || node.right == null) {
            removeWithOneChild(node);
        } else {
            removeWithTwoChildren(node);
        }
    }

    private void removeWithTwoChildren(RBTreeNode<T> node) {
        RBTreeNode<T> leftTop = getMax(node.left);

        swapValues(node, leftTop);
        remove(leftTop);
    }

    private RBTreeNode<T> getMax(RBTreeNode<T> node) {
        RBTreeNode<T> res = node;
        while (res.right != null) {
            res = res.right;
        }
        return res;
    }


    private void swapValues(RBTreeNode<T> node, RBTreeNode<T> node1) {
        T tmp = node.value;
        node.value = node1.value;
        node1.value = tmp;
        boolean t = node.color;
        node.color = node1.color;
        node1.color = t;
    }

    private boolean isLeaf(RBTreeNode<T> node) {
        return node.right == null && node.left == null;
    }

    private void replaceNode(RBTreeNode<T> oldNode, RBTreeNode<T> newNode) {
        if (oldNode != null && newNode != null) {
            oldNode.color = newNode.color;
            oldNode.right = newNode.right;
            oldNode.left = newNode.left;
        }
    }


    private void removeWithOneChild(RBTreeNode<T> node) {
        RBTreeNode<T> child = isLeaf(node.left) ? node.right : node.left;

        replaceNode(node, child);
        if (node.color == BLACK) {
            if (child.color == RED)
                child.color = BLACK;
            else
                deleteCase1(child);
        }
    }

    private void deleteCase1(RBTreeNode<T> node) {
        if (node.parent != null)
            deleteCase2(node);
    }

    private void deleteCase2(RBTreeNode<T> node) {
        RBTreeNode<T> sibling = node.sibling();

        if (sibling != null && sibling.color == RED) {
            node.parent.color = RED;
            sibling.color = BLACK;
            if (node == node.parent.left)
                rotateLeft(node.parent);
            else
                rotateRight(node.parent);
        }
        deleteCase3(node);
    }

    private void deleteCase3(RBTreeNode<T> node) {
        RBTreeNode<T> sibling = node.sibling();

        if (node.parent != null && sibling != null && sibling.left != null && sibling.right != null &&
                node.parent.color == BLACK &&
                sibling.color == BLACK &&
                sibling.left.color == BLACK &&
                sibling.right.color == BLACK) {

            sibling.color = RED;
            deleteCase1(node.parent);
        } else
            deleteCase4(node);
    }

    private void deleteCase4(RBTreeNode<T> node) {
        RBTreeNode<T> sibling = node.sibling();

        if (node.parent != null && sibling != null && sibling.left != null && sibling.right != null &&
                node.parent.color == RED &&
                sibling.color == BLACK &&
                sibling.left.color == BLACK &&
                sibling.right.color == BLACK) {
            sibling.color = RED;
            node.parent.color = BLACK;
        } else
            deleteCase5(node);
    }

    private void deleteCase5(RBTreeNode<T> node) {
        RBTreeNode<T> sibling = node.sibling();

        if (sibling != null && sibling.color == BLACK) {
            if (node == node.parent.left && sibling.right.color == BLACK && sibling.left.color == RED) {
                sibling.color = RED;
                sibling.left.color = BLACK;
                rotateRight(sibling);
            } else if (node == node.parent.right && sibling.left.color == BLACK && sibling.right.color == RED) {
                sibling.color = RED;
                sibling.right.color = BLACK;
                rotateLeft(sibling);
            }
        }
        deleteCase6(node);
    }

    private void deleteCase6(RBTreeNode<T> node) {
        RBTreeNode<T> sibling = node.sibling();

        if (sibling != null) {
            sibling.color = node.parent.color;
            node.parent.color = BLACK;

            if (node == node.parent.left) {
                sibling.right.color = BLACK;
                rotateLeft(node.parent);
            } else {
                sibling.left.color = BLACK;
                rotateRight(node.parent);
            }
        }
    }

    private void removeLeaf(RBTreeNode<T> node) {
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


    private void insertRoot(RBTreeNode<T> node) {
        if (node.parent == null) {
            node.color = BLACK;
        } else
            insertCase2(node);
    }

    private void insertCase2(RBTreeNode<T> node) {
        if (node.parent.color == RED) {
            insertCase3(node);
        }
    }

    private void insertCase3(RBTreeNode<T> node) {
        RBTreeNode<T> uncle = node.getUncle();

        if (uncle != null && uncle.color == RED) {
            node.parent.color = BLACK;
            uncle.color = BLACK;
            RBTreeNode<T> grandParent = node.getGrandParent();
            grandParent.color = RED;
            insertRoot(grandParent);
        } else
            insertCase4(node);


    }

    private void insertCase4(RBTreeNode<T> node) {
        RBTreeNode<T> grandParent = node.getGrandParent();

        if (grandParent != null) {
            if (node == node.parent.right && node.parent == grandParent.left) {
                rotateLeft(node.parent);
            } else if (node == node.parent.left && node.parent == grandParent.right) {
                rotateRight(node.parent);
            }
        }
        insertCase5(node);
    }

    private void insertCase5(RBTreeNode<T> node) {
        RBTreeNode<T> grandParent = node.getGrandParent();

        if (node == node.parent.left && node.parent == grandParent.left) {
            rotateRight(grandParent);
            grandParent.left.color = RED;
        } else {
            rotateLeft(grandParent);
            grandParent.right.color = RED;
        }
    }

    private void rotateRight(RBTreeNode<T> node) {
        RBTreeNode<T> left = node.left;
        RBTreeNode<T> left1 = left.left;

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
    }

    private void rotateLeft(RBTreeNode<T> node) {
        RBTreeNode<T> right = node.right;

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
    }

    public BinaryTreeNode<T> getTree() {
        return root;
    }

    public int size() {
        return size;
    }

    private final static boolean RED = true;
    private final static boolean BLACK = false;

    private class RBTreeNode<V> implements BinaryTreeNode<V> {

        private V value;
        private RBTreeNode<V> parent;
        private RBTreeNode<V> left;
        private RBTreeNode<V> right;
        private boolean color = RED;

        public RBTreeNode(V value, RBTreeNode<V> parent) {
            this.value = value;
            this.parent = parent;
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

        RBTreeNode<V> getGrandParent() {
            if (parent != null && parent.parent != null)
                return parent.parent;
            return null;
        }

        RBTreeNode<V> getUncle() {
            RBTreeNode<V> grandParent = getGrandParent();
            if (grandParent == null)
                return null;
            if (parent == grandParent.left)
                return grandParent.right;
            else
                return grandParent.left;
        }

        RBTreeNode<V> sibling() {
            if (parent == null)
                return null;

            if (this == parent.left)
                return parent.right;
            else
                return parent.left;
        }
    }
}
