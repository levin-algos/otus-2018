package ru.otus.algo;

import java.awt.*;
import java.util.*;
import java.util.function.Consumer;

abstract class AbstractBinarySearchTree<T> implements BinaryTree<T> {

    private int leftRotations;
    private int rightRotations;


    @Override
    public int getLeftRotationCount() {
        return leftRotations;
    }

    @Override
    public int getRightRotationCount() {
        return rightRotations;
    }

    int size;
    private final Comparator<? super T> comparator;
    Node<T> root;

    AbstractBinarySearchTree(AbstractBinarySearchTree<T> tree) {
        this.size = tree.size;
        this.comparator = tree.comparator;
        this.root = tree.root;
    }

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
        if (element == null)
            throw new IllegalArgumentException();

        if (root == null) {
            root = createNode(element, null);
            insertionFixup(root);
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

        insertionFixup(node);
        size++;

        assert checkInvariants(root, comparator);
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
            delete(node);
            size--;
        }

        if (root != null)
            assert checkInvariants(root, comparator);
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

            removeFixup(z);
            if (z == z.parent.left)
                z.parent.left = null;
            else if (z == z.parent.right)
                z.parent.right = null;
            z.parent = null;

        }
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
            rightRotations++;
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
            leftRotations++;
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
    @Override
    public int getHeight() {
        if (root == null)
            return 0;

        int left = root.left == null ? 0 : getMaxHeightNode(root.left);
        int right = root.right == null ? 0 : getMaxHeightNode(root.right);

        return Math.max(left, right) + 1;
    }

    private int getMaxHeightNode(Node<T> node) {
        if (node == null)
            return 0;

        int left = node.left == null ? 0 : getMaxHeightNode(node.left);
        int right = node.right == null ? 0 : getMaxHeightNode(node.right);

        return Math.max(left, right) + 1;
    }

    Image asImage() {
        return drawTree(root, getHeight());
    }

    private void drawNode(Image img, int x, int y, AbstractBinarySearchTree.Node<?> node) {
        String str = node.toString();
        img.drawString(str, x, y, TextAlign.CENTER);
    }


    private static final int HEIGHT_COEFF = 35;

    private Image drawTree(AbstractBinarySearchTree.Node<?> root, int treeHeight) {
        if (treeHeight <= 0)
            throw new IllegalArgumentException();

        int h = treeHeight + 1;
        int width = (1 << (h - 1)) * 30;
        int height = h * HEIGHT_COEFF;

        Image image = Image.of(width, height);
        drawTree(image, image.getWidth() / 2, 0, root);
        return image;
    }

    private void drawTree(Image img, int x, int lvl, AbstractBinarySearchTree.Node<?> root) {
        int y0 = lvl * HEIGHT_COEFF;
        drawNode(img, x, y0 + 16, root);
        int pad = img.getWidth() / (1 << (lvl + 1));
        int y1 = (lvl + 1) * HEIGHT_COEFF;
        if (root.left != null) {
            img.drawLine(x, y0, x - pad / 2, y1, Color.BLACK);
            drawTree(img, x - pad / 2, lvl + 1, root.left);
        }

        if (root.right != null) {
            img.drawLine(x, y0, x + pad / 2, y1, Color.BLACK);
            drawTree(img, x + pad / 2, lvl + 1, root.right);
        }
    }

    public Comparator<? super T> getComparator() {
        return comparator;
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
            return "{" +
                    value +
                    '}';
        }

        Node(V value, Node<V> parent) {
            this.value = value;
            this.parent = parent;
        }
    }

    /**
     * Recursively checks binary search tree invariants
     *
     * @param node - root of the tree to check
     * @return - true if tree is binary search tree
     */
    abstract boolean checkInvariants(Node<T> node, Comparator<? super T> cmp);

    abstract void insertionFixup(Node<T> root);
    abstract void removeFixup(Node<T> root);
}
